import com.ted.shapeless.playground.csvencoder.CsvEncoder
import shapeless.{HNil, ::}

val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly

reprEncoder.encode("abc" :: 123 :: true :: HNil)
