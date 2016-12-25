import shapeless._

val product: String :: Int :: Boolean :: HNil =
  "Sunday" :: 1 :: false :: HNil

case class Person(name: String, age: Int, /*it's important*/ likesMetal: Boolean)

val genericPerson = Generic[Person]

val person1 = Person("John Doe", 42, true)
val repr1 = genericPerson.to(person1)
val restoredPerson1 = genericPerson.from(repr1)
