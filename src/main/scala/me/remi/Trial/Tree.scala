package me.remi.Trial

sealed abstract class Tree[+T]{

  def addValue[U >: T <% Ordered[U]](x: U): Tree[U]
}

  case class Node[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T] {
    override def toString = "T(" + value.toString + " " + left.toString + " " + right.toString + ")"

    //def isMirrorOf[V](Tre: Tree[V]): Boolean = Tre match{
      //case t: Node[V] => left.isMirrorOf(t.right) && right.isMirrorOf(t.left)
      //case _ => false
    //}

    //def isSymmetric : Boolean = {
      //left.isMirrorOf(right)
    //}

    def addValue[U >: T <% Ordered[U]](entier: U): Tree[U] = {
      if (entier<value) Node(value, left.addValue(entier), right)
      else Node(value, left, right.addValue(entier))


    }


  }

  case object End extends Tree[Nothing] {
    override def toString = "."
    def addValue[U <% Ordered[U]](entier: U): Tree[U] = Node(entier)
    //def isSymmetric: Boolean = true
    //def isMirrorOf[V](t: Tree[V]): Boolean = t==End
  }

  object Node {
    def apply[T](value: T): Node[T] = Node(value, End, End)

    //def main(args: Array[String]){
      //val arbre = Node('a', Node('b', Node('d'), Node('e')), Node('c', End, Node('f', Node('g'), End)))
      //println(arbre)
    //}
  }

object Tree {

  def main(args: Array[String]){
    println(fromList(List(3,2,5,7,1)))
  }

  def fromList[U<% Ordered[U]](l:List[U]): Tree[U] = {
    var result: Tree[U] = End
    for (arg<-l) result = result.addValue(arg)
    result
  }

  def from[U <% Ordered[U]](xs: TraversableOnce[U]): Tree[U] = {
  @annotation.tailrec
  def step(iter: Iterator[U], z: Tree[U]): Tree[U] = {
    if (!iter.hasNext) z
    else {
      val u = iter.next()
      step(iter, z.addValue(u))
    }
  }
  step(xs.toIterator, End)
}

  def cBalanced[T](n:Int, x: T): List[Tree[T]] = n match {
    case 0 => List(End)
    case 1 => List(Node.apply(x))
    case m if m%2!=0 => {
      val arb = cBalanced((m-1)/2,x)
      val arbu = arb
      var result = List[Tree[T]]()
      for (ar <- arb) {
        for (a <- arbu)
          result=(new Node(x,ar,a))::result
      }
      result
    }
    case m => {
      val ar1 = cBalanced(m/2,x)
      val ar2 = cBalanced(m/2-1,x)
      var result1 = List[Tree[T]]()
      var result2 = List[Tree[T]]()
      for (arb1 <- ar1){
        for (arb2 <- ar2){
          result1 = (new Node(x,arb1,arb2))::result1
          result2 = (new Node(x,arb2,arb1))::result2
        }
      }
      result1:::result2
    }
  }

  def cBalancedBis[T](nodes: Int, value: T): List[Tree[T]] = nodes match{
    case n if n<1 => List(End)
    case n if n%2==1 => {
      val inter = cBalancedBis((n-1)/2, value)
      inter.flatMap((element: Tree[T]) => inter.map((ar: Tree[T]) => new Node(value, ar, element)))
    }
    case n => {
      val result1 = cBalancedBis(nodes/2,value)
      val result2 = cBalancedBis(nodes/2-1,value)
      result1.flatMap((element1: Tree[T]) => result2.flatMap((element2: Tree[T]) => List(new Node(value,element1,element2), new Node(value, element2,element1))))
    }
  }
}