package com.ted.shapeless.playground.migration

import shapeless.ops.hlist.{Align, Intersection}
import shapeless.{HList, LabelledGeneric}

trait Migration[A, B] {
  def apply(value: A): B
}

object Migration {
  implicit class MigrationOps[A](value: A) {
    def migrateTo[B](implicit migration: Migration[A, B]): B = migration.apply(value)
  }

  implicit def genericMigration[A, B, ARepr <: HList, BRepr <: HList, Unaligned <: HList](
                                                                 implicit
                                                                 aGen: LabelledGeneric.Aux[A, ARepr],
                                                                 bGen: LabelledGeneric.Aux[B, BRepr],
                                                                 intersection: Intersection.Aux[ARepr, BRepr, Unaligned],
                                                                 align: Align[Unaligned, BRepr]
                                                                 ): Migration[A, B] =
  new Migration[A, B] {
    override def apply(value: A): B = {
      bGen.from(align.apply(intersection.apply(aGen.to(value))))
    }
  }
}
