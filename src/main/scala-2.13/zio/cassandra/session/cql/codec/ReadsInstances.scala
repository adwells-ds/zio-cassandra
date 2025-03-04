package zio.cassandra.session.cql.codec

import com.datastax.oss.driver.api.core.cql.Row
import shapeless.{ ::, HList, HNil, LabelledGeneric, Witness }
import shapeless.labelled.{ field, FieldType }
import zio.cassandra.session.cql.codec.Reads.instance

trait ReadsInstances0 extends ReadsInstances1 {

  /** Useful when you want to "cache" [[zio.cassandra.session.cql.codec.Reads]] instance (e.g. to decrease compilation
    * time or make sure it captures the correct [[zio.cassandra.session.cql.codec.Configuration]])
    *
    * Example:
    * {{{
    * final case class Foo(a: Int, b: String)
    *
    * // somewhere else
    * implicit val configuration: Configuration = {
    *   val renameFields: String => String = {
    *     case "a"   => "some_other_name"
    *     case other => Configuration.snakeCaseTransformation(other)
    *     }
    *   Configuration(renameFields)
    * }
    *
    * implicit val reads: Reads[Foo] = Reads.derive
    * }}}
    */
  def derive[T, Repr](implicit
    configuration: Configuration,
    gen: LabelledGeneric.Aux[T, Repr],
    reads: Reads[Repr]
  ): Reads[T] = genericReads

}

trait ReadsInstances1 extends ReadsInstances2 {

  implicit val rowReads: Reads[Row] = instance(identity)

  implicit def tuple1Reads[
    T1: CellReads
  ]: Reads[Tuple1[T1]] =
    instance { row =>
      val t1 = readByIndex[T1](row, 0)
      Tuple1(t1)
    }

  implicit def tuple2Reads[
    T1: CellReads,
    T2: CellReads
  ]: Reads[(T1, T2)] =
    instance { row =>
      val t1 = readByIndex[T1](row, 0)
      val t2 = readByIndex[T2](row, 1)
      (t1, t2)
    }

  implicit def tuple3Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads
  ]: Reads[(T1, T2, T3)] =
    instance { row =>
      val t1 = readByIndex[T1](row, 0)
      val t2 = readByIndex[T2](row, 1)
      val t3 = readByIndex[T3](row, 2)
      (t1, t2, t3)
    }

  implicit def tuple4Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads
  ]: Reads[(T1, T2, T3, T4)] =
    instance { row =>
      val t1 = readByIndex[T1](row, 0)
      val t2 = readByIndex[T2](row, 1)
      val t3 = readByIndex[T3](row, 2)
      val t4 = readByIndex[T4](row, 3)
      (t1, t2, t3, t4)
    }

  implicit def tuple5Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads
  ]: Reads[(T1, T2, T3, T4, T5)] =
    instance { row =>
      val t1 = readByIndex[T1](row, 0)
      val t2 = readByIndex[T2](row, 1)
      val t3 = readByIndex[T3](row, 2)
      val t4 = readByIndex[T4](row, 3)
      val t5 = readByIndex[T5](row, 4)
      (t1, t2, t3, t4, t5)
    }

  implicit def tuple6Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6)] =
    instance { row =>
      val t1 = readByIndex[T1](row, 0)
      val t2 = readByIndex[T2](row, 1)
      val t3 = readByIndex[T3](row, 2)
      val t4 = readByIndex[T4](row, 3)
      val t5 = readByIndex[T5](row, 4)
      val t6 = readByIndex[T6](row, 5)
      (t1, t2, t3, t4, t5, t6)
    }

  implicit def tuple7Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7)] =
    instance { row =>
      val t1 = readByIndex[T1](row, 0)
      val t2 = readByIndex[T2](row, 1)
      val t3 = readByIndex[T3](row, 2)
      val t4 = readByIndex[T4](row, 3)
      val t5 = readByIndex[T5](row, 4)
      val t6 = readByIndex[T6](row, 5)
      val t7 = readByIndex[T7](row, 6)
      (t1, t2, t3, t4, t5, t6, t7)
    }

  implicit def tuple8Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8)] =
    instance { row =>
      val t1 = readByIndex[T1](row, 0)
      val t2 = readByIndex[T2](row, 1)
      val t3 = readByIndex[T3](row, 2)
      val t4 = readByIndex[T4](row, 3)
      val t5 = readByIndex[T5](row, 4)
      val t6 = readByIndex[T6](row, 5)
      val t7 = readByIndex[T7](row, 6)
      val t8 = readByIndex[T8](row, 7)
      (t1, t2, t3, t4, t5, t6, t7, t8)
    }

  implicit def tuple9Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] =
    instance { row =>
      val t1 = readByIndex[T1](row, 0)
      val t2 = readByIndex[T2](row, 1)
      val t3 = readByIndex[T3](row, 2)
      val t4 = readByIndex[T4](row, 3)
      val t5 = readByIndex[T5](row, 4)
      val t6 = readByIndex[T6](row, 5)
      val t7 = readByIndex[T7](row, 6)
      val t8 = readByIndex[T8](row, 7)
      val t9 = readByIndex[T9](row, 8)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9)
    }

  implicit def tuple10Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10)
    }

  implicit def tuple11Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11)
    }

  implicit def tuple12Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12)
    }

  implicit def tuple13Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13)
    }

  implicit def tuple14Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads,
    T14: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      val t14 = readByIndex[T14](row, 13)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14)
    }

  implicit def tuple15Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads,
    T14: CellReads,
    T15: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      val t14 = readByIndex[T14](row, 13)
      val t15 = readByIndex[T15](row, 14)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15)
    }

  implicit def tuple16Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads,
    T14: CellReads,
    T15: CellReads,
    T16: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      val t14 = readByIndex[T14](row, 13)
      val t15 = readByIndex[T15](row, 14)
      val t16 = readByIndex[T16](row, 15)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16)
    }

  implicit def tuple17Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads,
    T14: CellReads,
    T15: CellReads,
    T16: CellReads,
    T17: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      val t14 = readByIndex[T14](row, 13)
      val t15 = readByIndex[T15](row, 14)
      val t16 = readByIndex[T16](row, 15)
      val t17 = readByIndex[T17](row, 16)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17)
    }

  implicit def tuple18Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads,
    T14: CellReads,
    T15: CellReads,
    T16: CellReads,
    T17: CellReads,
    T18: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      val t14 = readByIndex[T14](row, 13)
      val t15 = readByIndex[T15](row, 14)
      val t16 = readByIndex[T16](row, 15)
      val t17 = readByIndex[T17](row, 16)
      val t18 = readByIndex[T18](row, 17)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18)
    }

  implicit def tuple19Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads,
    T14: CellReads,
    T15: CellReads,
    T16: CellReads,
    T17: CellReads,
    T18: CellReads,
    T19: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      val t14 = readByIndex[T14](row, 13)
      val t15 = readByIndex[T15](row, 14)
      val t16 = readByIndex[T16](row, 15)
      val t17 = readByIndex[T17](row, 16)
      val t18 = readByIndex[T18](row, 17)
      val t19 = readByIndex[T19](row, 18)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19)
    }

  implicit def tuple20Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads,
    T14: CellReads,
    T15: CellReads,
    T16: CellReads,
    T17: CellReads,
    T18: CellReads,
    T19: CellReads,
    T20: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      val t14 = readByIndex[T14](row, 13)
      val t15 = readByIndex[T15](row, 14)
      val t16 = readByIndex[T16](row, 15)
      val t17 = readByIndex[T17](row, 16)
      val t18 = readByIndex[T18](row, 17)
      val t19 = readByIndex[T19](row, 18)
      val t20 = readByIndex[T20](row, 19)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20)
    }

  implicit def tuple21Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads,
    T14: CellReads,
    T15: CellReads,
    T16: CellReads,
    T17: CellReads,
    T18: CellReads,
    T19: CellReads,
    T20: CellReads,
    T21: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      val t14 = readByIndex[T14](row, 13)
      val t15 = readByIndex[T15](row, 14)
      val t16 = readByIndex[T16](row, 15)
      val t17 = readByIndex[T17](row, 16)
      val t18 = readByIndex[T18](row, 17)
      val t19 = readByIndex[T19](row, 18)
      val t20 = readByIndex[T20](row, 19)
      val t21 = readByIndex[T21](row, 20)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21)
    }

  implicit def tuple22Reads[
    T1: CellReads,
    T2: CellReads,
    T3: CellReads,
    T4: CellReads,
    T5: CellReads,
    T6: CellReads,
    T7: CellReads,
    T8: CellReads,
    T9: CellReads,
    T10: CellReads,
    T11: CellReads,
    T12: CellReads,
    T13: CellReads,
    T14: CellReads,
    T15: CellReads,
    T16: CellReads,
    T17: CellReads,
    T18: CellReads,
    T19: CellReads,
    T20: CellReads,
    T21: CellReads,
    T22: CellReads
  ]: Reads[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] =
    instance { row =>
      val t1  = readByIndex[T1](row, 0)
      val t2  = readByIndex[T2](row, 1)
      val t3  = readByIndex[T3](row, 2)
      val t4  = readByIndex[T4](row, 3)
      val t5  = readByIndex[T5](row, 4)
      val t6  = readByIndex[T6](row, 5)
      val t7  = readByIndex[T7](row, 6)
      val t8  = readByIndex[T8](row, 7)
      val t9  = readByIndex[T9](row, 8)
      val t10 = readByIndex[T10](row, 9)
      val t11 = readByIndex[T11](row, 10)
      val t12 = readByIndex[T12](row, 11)
      val t13 = readByIndex[T13](row, 12)
      val t14 = readByIndex[T14](row, 13)
      val t15 = readByIndex[T15](row, 14)
      val t16 = readByIndex[T16](row, 15)
      val t17 = readByIndex[T17](row, 16)
      val t18 = readByIndex[T18](row, 17)
      val t19 = readByIndex[T19](row, 18)
      val t20 = readByIndex[T20](row, 19)
      val t21 = readByIndex[T21](row, 20)
      val t22 = readByIndex[T22](row, 21)
      (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22)
    }

}

trait ReadsInstances2 extends ReadsInstances3 {

  implicit val hNilReads: Reads[HNil] = instance(_ => HNil)

  implicit def hConsReads[K <: Symbol, H, T <: HList](implicit
    configuration: Configuration,
    hReads: CellReads[H],
    tReads: Reads[T],
    fieldNameW: Witness.Aux[K]
  ): Reads[FieldType[K, H] :: T] =
    instance { row =>
      val fieldName = configuration.transformFieldNames(fieldNameW.value.name)
      val bytes     = row.getBytesUnsafe(fieldName)
      val fieldType = row.getType(fieldName)
      val head      = withRefinedError(hReads.read(bytes, row.protocolVersion(), fieldType))(row, fieldName)
      val tail      = tReads.read(row)
      field[K](head) :: tail
    }

  implicit def genericReads[T, Repr](implicit
    configuration: Configuration,
    gen: LabelledGeneric.Aux[T, Repr],
    reads: Reads[Repr]
  ): Reads[T] =
    instance(row => gen.from(reads.read(row)))

  private def withRefinedError[T](expr: => T)(row: Row, fieldName: String): T =
    try expr
    catch refineError(row, row.getColumnDefinitions.get(fieldName))

}
