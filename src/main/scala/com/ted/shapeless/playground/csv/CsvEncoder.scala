package com.ted.shapeless.playground.csv

import shapeless._

trait CsvEncoder[T] {
  def encode(value: T): List[String]
}

object CsvEncoder {
  def apply[T](implicit csvEncoder: CsvEncoder[T]): CsvEncoder[T] = csvEncoder

  def createEncoder[T](func: T => List[String]): CsvEncoder[T] =
      new CsvEncoder[T] {
        override def encode(value: T): List[String] = func(value)
      }

  implicit val stringCsvEncoder: CsvEncoder[String] = createEncoder(str => List(str))
  implicit val intCsvEncoder: CsvEncoder[Int] = createEncoder(num => List(num.toString))
  implicit val doubleCsvEncoder: CsvEncoder[Double] = createEncoder(num => List(num.toString))
  implicit val booleanCsvEncoder: CsvEncoder[Boolean] = createEncoder(bool => List(if (bool) "yes" else "no"))

  implicit val hNilCsvEncoder: CsvEncoder[HNil] = createEncoder(_ => Nil)
  implicit def hListCsvEncoder[H, T <: HList](
                                               implicit
                                               hCsvEncoder: Lazy[CsvEncoder[H]],
                                               tCsvEncoder: CsvEncoder[T]
                                             ): CsvEncoder[H :: T] = createEncoder {
    case head :: tail => hCsvEncoder.value.encode(head) ++ tCsvEncoder.encode(tail)
  }


  implicit val cNilCsvEncoder: CsvEncoder[CNil] = createEncoder(cnil => throw new Exception("Impossible"))
  implicit def coproductCsvEncoder[H, T <: Coproduct](
                                      implicit
                                      hCsvEncoder: Lazy[CsvEncoder[H]],
                                      tCsvEncoder: CsvEncoder[T]
                                    ): CsvEncoder[H :+: T] = createEncoder {
    case Inl(h) => hCsvEncoder.value.encode(h)
    case Inr(t) => tCsvEncoder.encode(t)
  }


    implicit def genericCsvEncoder[T, R](
                                     implicit
                                     gen: Generic.Aux[T, R] /*Generic[T] {type Repr = R}*/ ,
                                     reprCsvEncoder: Lazy[CsvEncoder[R]]
                                   ): CsvEncoder[T] =
  createEncoder(value => reprCsvEncoder.value.encode(gen.to(value)))
}
