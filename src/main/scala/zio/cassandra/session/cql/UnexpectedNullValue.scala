package zio.cassandra.session.cql

import com.datastax.oss.driver.api.core.cql.{ ColumnDefinition, Row }
import com.datastax.oss.driver.api.core.data.UdtValue

import scala.util.control.NoStackTrace

sealed trait UnexpectedNullValue extends Throwable

case class UnexpectedNullValueInColumn(row: Row, cl: ColumnDefinition)
    extends RuntimeException()
    with UnexpectedNullValue {
  override def getMessage: String = {
    val table    = cl.getTable.toString
    val column   = cl.getName.toString
    val keyspace = cl.getKeyspace.toString
    val tpe      = cl.getType.asCql(true, true)

    s"Read NULL value from $keyspace.$table column $column expected $tpe. Row ${row.getFormattedContents}"
  }
}

object UnexpectedNullValueInColumn {

  def apply(row: Row, fieldName: String): UnexpectedNullValueInColumn =
    UnexpectedNullValueInColumn(row, row.getColumnDefinitions.get(fieldName))

  def apply(row: Row, index: Int): UnexpectedNullValueInColumn =
    UnexpectedNullValueInColumn(row, row.getColumnDefinitions.get(index))

}

case class UnexpectedNullValueInUdt(row: Row, udt: UdtValue, fieldName: String)
    extends RuntimeException()
    with UnexpectedNullValue {
  override def getMessage: String = {
    val cl       = row.getColumnDefinitions.get(fieldName)
    val table    = cl.getTable.toString
    val column   = cl.getName.toString
    val keyspace = cl.getKeyspace.toString
    val tpe      = cl.getType.asCql(true, true)

    val udtTpe = udt.getType(fieldName)

    s"Read NULL value from $keyspace.$table inside UDT column $column with type $tpe. NULL value in $fieldName, expected type $udtTpe. Row ${row.getFormattedContents}"
  }

}

object UnexpectedNullValueInUdt {
  private[cql] case class NullValueInUdt(udtValue: UdtValue, fieldName: String) extends NoStackTrace
}
