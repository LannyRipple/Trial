package me.remi.Trial

import org.specs2.mutable._

class TestTree extends SpecificationWithJUnit{

  "fromList" should{

    "return this tree" in{
      val liste = Tree fromList List(3,2,5,7,1)
      liste mustEqual Node(3,Node(2,Node(1,End,End),End),Node(5,End,Node(7,End,End)))
    }
  }

  "from" should {
  val xs = List(3,2,5,7,1)
  val expected = Node(3, Node(2, Node(1, End, End), End), Node(5, End, Node(7, End, End)))

  "work for an iterator" in {
    val got = Tree from xs.iterator
    got mustEqual expected
  }

  "work for a traversable" in {
    val got = Tree from xs.toTraversable
    got mustEqual expected
  }

  // These below are covered by the above.

  "work for a list" in {
    val got = Tree from xs
    got mustEqual expected
  }

  "work for an array" in {
    val got = Tree from (xs.toArray: Array[Int]) // Type hint to break implicit from Array[Double] and Array[Int]
    got mustEqual expected
  }

  "work for a steam" in {
    val got = Tree from xs.toStream
    got mustEqual expected
  }
}

}