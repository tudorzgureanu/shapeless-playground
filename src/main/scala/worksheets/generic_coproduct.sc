import shapeless._

case class Red()
case class Yellow()
case class Green()

type Light = Red :+: Yellow :+: Green :+: CNil

sealed trait Shape
case class Rectangle(width: Double, height: Double) extends Shape
case class Circle(radius: Double) extends Shape
case class Circle2(radius: Double) extends Shape

val genericShape = Generic[Shape]

genericShape.from(genericShape.to(Circle(3)))
genericShape.from(genericShape.to(Circle2(3)))
