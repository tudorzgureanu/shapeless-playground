package com.ted.shapeless.playground.csv

object CsvWriter {
  def writeCsv[T](values: List[T])(implicit encoder: CsvEncoder[T]): String =
    values.map(value => encoder.encode(value).mkString(",")).mkString("\n")
}
