import com.ted.shapeless.playground.json.JsonEncoder

case class FootballTeam(name: String, locality: String, players: List[String], points: Int)

val team1 = FootballTeam("FC Some Name", "London", Nil, 0)

JsonEncoder[FootballTeam].encode(team1)


sealed trait Animal
case class Cat(name: String, age: Int) extends Animal
case class Dog(name: String, age: Int) extends Animal

val animalEncoder = JsonEncoder[Animal]

List(Cat("Tom", 4), Dog("Rex", 10)).map(animalEncoder.encode)

