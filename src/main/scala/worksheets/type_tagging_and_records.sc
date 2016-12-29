import shapeless._
import shapeless.labelled.field
import shapeless.syntax.singleton._

trait Important

val a = 42

val keyTagA = "sampleTag" ->> a
val fieldTypeA = field[Important](a)

val person = ("name" ->> "John Doe") :: ("age" ->> 42 ) :: HNil