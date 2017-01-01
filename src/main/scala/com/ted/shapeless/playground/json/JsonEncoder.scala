package com.ted.shapeless.playground.json

import shapeless._
import shapeless.labelled.FieldType

trait JsonEncoder[T] {
  def encode(value: T): JsonValue
}

trait JsonObjectEncoder[T] extends JsonEncoder[T]{
  def encode(value: T): JsonObject
}

object JsonEncoder {
  def apply[T](implicit enc: JsonEncoder[T]): JsonEncoder[T] = enc

  def createEncoder[T](func: T => JsonValue): JsonEncoder[T] =
    new JsonEncoder[T] {
      override def encode(value: T): JsonValue = func(value)
    }

  def createObjectEncoder[T](func: T => JsonObject): JsonObjectEncoder[T] =
    new JsonObjectEncoder[T] {
      override def encode(value: T): JsonObject = func(value)
    }

  implicit val stringEncoder: JsonEncoder[String] = createEncoder(JsonString)
  implicit val doubleEncoder: JsonEncoder[Double] = createEncoder(JsonNumber)
  implicit val intEncoder: JsonEncoder[Int] = createEncoder(JsonNumber(_))
  implicit val booleanEncoder: JsonEncoder[Boolean] = createEncoder(JsonBoolean)

  implicit def arrayEncoder[T](implicit enc: JsonEncoder[T]): JsonEncoder[List[T]] = createEncoder(list => JsonArray(list.map(enc.encode)))
  implicit def optionEncoder[T](implicit enc: JsonEncoder[T]): JsonEncoder[Option[T]] = createEncoder(option => option.map(enc.encode).getOrElse(JsonNull))

  implicit val hNilEncoder: JsonObjectEncoder[HNil] = createObjectEncoder(_ => JsonObject(Nil))
  implicit def hListEncoder[K <: Symbol, H, T <: HList](
                                                         implicit
                                                         witness: Witness.Aux[K],
                                                         hEnc: Lazy[JsonEncoder[H]],
                                                         tEnc: JsonObjectEncoder[T]
                                                       ): JsonObjectEncoder[FieldType[K, H] :: T] = {
    createObjectEncoder {
      case h :: t =>
      val hField = hEnc.value.encode(h)
      val tFields = tEnc.encode(t).fields
      JsonObject((witness.value.name, hField) :: tFields)
    }
  }

  implicit val cNilEncoder: JsonObjectEncoder[CNil] = createObjectEncoder(_ => throw new Exception("Never in this life!"))
  implicit def coproductEncoder[K <: Symbol, H, T <: Coproduct](
                                                                implicit
                                                                witness: Witness.Aux[K],
                                                                hEnc: Lazy[JsonEncoder[H]],
                                                                tEnc: JsonObjectEncoder[T]
                                                           ): JsonObjectEncoder[FieldType[K, H] :+: T] = {
    createObjectEncoder {
      case Inl(h) => JsonObject(List(witness.value.name -> hEnc.value.encode(h)))
      case Inr(t) => tEnc.encode(t)
    }
  }

  implicit def genericObjectEncoder[T, Repr](
                                           implicit
                                           labelledGeneric: LabelledGeneric.Aux[T, Repr],
                                           enc: Lazy[JsonObjectEncoder[Repr]]
                                         ): JsonEncoder[T] =
    createEncoder { obj =>
      enc.value.encode(labelledGeneric.to(obj))
    }
}
