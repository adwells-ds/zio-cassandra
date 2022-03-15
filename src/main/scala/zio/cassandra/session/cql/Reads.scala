package zio.cassandra.session.cql

import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.data.UdtValue
import com.datastax.oss.driver.internal.core.`type`.{ DefaultListType, DefaultMapType, DefaultSetType }
import shapeless.{ ::, Generic, HList, HNil }
import zio.{ Task, ZIO }

import java.nio.ByteBuffer
import java.time.{ Instant, LocalDate }
import java.util.UUID
import scala.jdk.CollectionConverters._

trait Reads[T] { self =>
  def readUnsafe(row: Row, index: Int): T

  def read(row: Row, index: Int): Task[T] =
    if (row.isNull(index)) {
      Task.fail(UnexpectedNullValue(row, index))
    } else {
      Task(readUnsafe(row, index))
    }

  def nextIndex(index: Int): Int = index + 1

  def map[U](f: T => U): Reads[U] =
    (row: Row, index: Int) => f(self.readUnsafe(row, index))
}

case class UnexpectedNullValue(row: Row, index: Int) extends RuntimeException() {
  override def getMessage: String = {
    val cl       = row.getColumnDefinitions.get(index)
    val table    = cl.getTable.toString
    val column   = cl.getName.toString
    val keyspace = cl.getKeyspace.toString
    val tpe      = cl.getType.asCql(true, true)

    s"Read NULL value from column $column with type $tpe at $keyspace.$table. Row ${row.getFormattedContents}"
  }
}

object Reads extends ReadsLowerPriority with ReadsLowestPriority {
  def apply[T](implicit r: Reads[T]): Reads[T] = r

  implicit val rowReads: Reads[Row] = new Reads[Row] {
    override def readUnsafe(row: Row, index: Int): Row = row
    override def nextIndex(index: Int): Int            = index
  }

  implicit val stringReads: Reads[String]         = (row: Row, index: Int) => row.getString(index)
  implicit val doubleReads: Reads[Double]         = (row: Row, index: Int) => row.getDouble(index)
  implicit val intReads: Reads[Int]               = (row: Row, index: Int) => row.getInt(index)
  implicit val longReads: Reads[Long]             = (row: Row, index: Int) => row.getLong(index)
  implicit val byteBufferReads: Reads[ByteBuffer] = (row: Row, index: Int) => row.getByteBuffer(index)
  implicit val localDateReads: Reads[LocalDate]   = (row: Row, index: Int) => row.getLocalDate(index)
  implicit val instantReads: Reads[Instant]       = (row: Row, index: Int) => row.getInstant(index)
  implicit val booleanReads: Reads[Boolean]       = (row: Row, index: Int) => row.getBoolean(index)
  implicit val uuidReads: Reads[UUID]             = (row: Row, index: Int) => row.getUuid(index)
  implicit val bigIntReads: Reads[BigInt]         = (row: Row, index: Int) => row.getBigInteger(index)
  implicit val bigDecimalReads: Reads[BigDecimal] = (row: Row, index: Int) => row.getBigDecimal(index)
  implicit val shortReads: Reads[Short]           = (row: Row, index: Int) => row.getShort(index)
  implicit val udtReads: Reads[UdtValue]          = (row: Row, index: Int) => row.getUdtValue(index)

  implicit def optionReads[T: Reads]: Reads[Option[T]] = new Reads[Option[T]] {
    override def readUnsafe(row: Row, index: Int): Option[T] =
      if (row.isNull(index)) None
      else {
        Some(Reads[T].readUnsafe(row, index))
      }

    override def read(row: Row, index: Int): Task[Option[T]] =
      if (row.isNull(index)) ZIO.none
      else {
        Task(Reads[T].readUnsafe(row, index)).map(Some(_))
      }
  }

}

/** Note: We define instances for collections rather than A where A has evidence of a CassandraTypeMapper instance to
  * prevent an implicit resolution clash with the case class parser
  */
trait ReadsLowerPriority {
  implicit def deriveSetFromCassandraTypeMapper[A](implicit ev: CassandraTypeMapper[A]): Reads[Set[A]] = {
    (row: Row, index: Int) =>
      val datatype     = row.getType(index).asInstanceOf[DefaultSetType].getElementType
      val cassandraSet = row.getSet(index, ev.classType)
      cassandraSet.asScala.map(cas => ev.fromCassandra(cas, datatype)).toSet
  }

  implicit def deriveListFromCassandraTypeMapper[A](implicit ev: CassandraTypeMapper[A]): Reads[List[A]] = {
    (row: Row, index: Int) =>
      val datatype     = row.getType(index).asInstanceOf[DefaultListType].getElementType
      val cassandraSet = row.getList(index, ev.classType)
      cassandraSet.asScala.map(cas => ev.fromCassandra(cas, datatype)).toList
  }

  implicit def deriveMapFromCassandraTypeMapper[K, V](implicit
    evK: CassandraTypeMapper[K],
    evV: CassandraTypeMapper[V]
  ): Reads[Map[K, V]] = { (row: Row, index: Int) =>
    val top          = row.getType(index).asInstanceOf[DefaultMapType]
    val keyType      = top.getKeyType
    val valueType    = top.getValueType
    val cassandraMap = row.getMap(index, evK.classType, evV.classType)
    cassandraMap.asScala.map { case (k, v) =>
      (evK.fromCassandra(k, keyType), evV.fromCassandra(v, valueType))
    }.toMap
  }
}

trait ReadsLowestPriority {
  implicit val hNilParser: Reads[HNil] = new Reads[HNil] {
    override def readUnsafe(row: Row, index: Int): HNil = HNil
    override def read(row: Row, index: Int): Task[HNil] = Task.succeed(HNil)
    override def nextIndex(index: Int): Int = index
  }

  implicit def hConsParser[H: Reads, T <: HList: Reads]: Reads[H :: T] = new Reads[H :: T] {
    override def read(row: Row, index: Int): Task[H :: T] =
      for {
        h        <- Reads[H].read(row, index)
        nextIndex = Reads[H].nextIndex(index)
        t        <- Reads[T].read(row, nextIndex)
      } yield h :: t

    override def readUnsafe(row: Row, index: Int): H :: T = {
      val h         = Reads[H].readUnsafe(row, index)
      val nextIndex = Reads[H].nextIndex(index)
      val t         = Reads[T].readUnsafe(row, nextIndex)
      h :: t
    }
  }

  implicit def caseClassParser[A, R <: HList](implicit
    gen: Generic[A] { type Repr = R },
    reprParser: Reads[R]
  ): Reads[A] = new Reads[A] {
    override def readUnsafe(row: Row, index: Int): A = {
      val rep = reprParser.readUnsafe(row, index)
      gen.from(rep)
    }

    override def read(row: Row, index: Int): Task[A] =
      reprParser.read(row, index).map(gen.from)
  }
}
