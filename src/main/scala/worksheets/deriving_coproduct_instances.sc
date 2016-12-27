import com.ted.shapeless.playground.csv.CsvWriter._


sealed trait Shape
case class Rectangle(width: Double, height: Double) extends Shape
case class Circle(radius: Double) extends Shape


writeCsv(List[Shape](Circle(3), Rectangle(4, 5)))