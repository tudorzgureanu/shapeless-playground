import shapeless._
import shapeless.record._


case class Person(name: String, age: Int)

val pGen = LabelledGeneric[Person]
val person = Person("John Doe", 42)
val personRepr: pGen.Repr = pGen.to(person)

personRepr.get('name)


pGen.from(personRepr.updated('name, "Not John Doe"))

personRepr.toMap.map{ case (key, value) => key.name -> value }

personRepr.renameField('age, 'level)