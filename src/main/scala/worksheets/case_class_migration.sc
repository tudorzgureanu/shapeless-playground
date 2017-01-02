import com.ted.shapeless.playground.migration.Migration._


case class PersonV1(firstName: String, lastName: String, age: Int)
case class PersonV2(age: Int, firstName: String)
case class PersonV3(firstName: String)
case class PersonV4(name: String, age: Int)

val personV1 = PersonV1("John", "Doe", 42)

personV1.migrateTo[PersonV2]
personV1.migrateTo[PersonV3]

// not supported yet personV1.migrateTo[PersonV4]