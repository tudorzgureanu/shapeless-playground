import com.ted.shapeless.playground.csv.CsvWriter._

sealed trait Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
case class Leaf[A](value: A) extends Tree[A]

writeCsv(List[Tree[Int]](Branch(Leaf(2), Branch(Leaf(3), Leaf(4)))))