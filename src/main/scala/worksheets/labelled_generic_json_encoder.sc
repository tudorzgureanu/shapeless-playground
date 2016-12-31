import com.ted.shapeless.playground.json.JsonEncoder

case class FootballTeam(name: String, locality: String, players: List[String], points: Int)

val team1 = FootballTeam("FC Some Name", "London", Nil, 0)

JsonEncoder[FootballTeam].encode(team1)

