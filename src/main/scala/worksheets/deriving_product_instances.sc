import com.ted.shapeless.playground.csv.CsvEncoder
import com.ted.shapeless.playground.csv.CsvWriter._
import shapeless.{HNil, ::}


val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly
reprEncoder.encode("abc" :: 123 :: true :: HNil)


case class User(nickname: String, email: String, age: Int)
val user1 = User("johndoe", "johndoe@example.com", 42)

writeCsv(List(user1, user1))
