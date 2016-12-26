package com.ted.shapeless.playground.csv

import shapeless.{Generic, ::, HList, HNil}

trait CsvEncoder[T] {
  def encode(value: T): List[String]
}

object CsvEncoder {
  def createEncoder[T](func: T => List[String]): CsvEncoder[T] =
      new CsvEncoder[T] {
        override def encode(value: T): List[String] = func(value)
      }

  implicit val stringCsvEncoder: CsvEncoder[String] = createEncoder(str => List(str))
  implicit val intCsvEncoder: CsvEncoder[Int] = createEncoder(num => List(num.toString))
  implicit val booleanCsvEncoder: CsvEncoder[Boolean] = createEncoder(bool => List(if (bool) "yes" else "no"))

  implicit val hNilCsvEncoder: CsvEncoder[HNil] = createEncoder(_ => Nil)
  implicit def hListCsvEncoder[H, T <: HList](
                                               implicit
                                               headCsvEncoder: CsvEncoder[H],
                                               tailCsvEncoder: CsvEncoder[T]
                                             ): CsvEncoder[H :: T] = createEncoder {
    case head :: tail => headCsvEncoder.encode(head) ++ tailCsvEncoder.encode(tail)
  }

  implicit def genericCsvEncoder[T, R](
                                     implicit
                                     gen: Generic.Aux[T, R] /*Generic[T] {type Repr = R}*/ ,
                                     reprCsvEncoder: CsvEncoder[R]
                                   ): CsvEncoder[T] =
  createEncoder(value => reprCsvEncoder.encode(gen.to(value)))
}
