package me.remi.cascal


import com.shorrockin.cascal.model._
import com.shorrockin.cascal.session._


object Cass {

    val hosts = Host("localhost", 9160, 30 * 1000) :: Nil
    val params = new PoolParams(200, ExhaustionPolicy.Fail, 500L, 6, 2)
    lazy val pool = new SessionPool(hosts, params, Consistency.One)
}


object Example {

    def main(args: Array[String]) {

      val cols = Cass.pool.borrow {_.list( Keyspace("Trial") \ "People" \ "1" )}
        println(cols.mkString("\n"))
      //Cass.pool.borrow{session => session.insert(Keyspace("Trial") \ "People" \ "1" \ ("firstName".getBytes,"Alan".getBytes))}
      for (i <- 0 to 575){
        val (first, last) = doubleCorres(i)
        Cass.pool.borrow(session => session.insert((Keyspace("Trial") \ "People" \ i.toString \ ("firstName".getBytes, first.getBytes))))
        Cass.pool.borrow(session => session.insert((Keyspace("Trial") \ "People" \ i.toString \ ("lastName".getBytes, last.getBytes))))
      }
      println(Cass.pool.borrow{session => session.count(Keyspace("Trial") \ "People" \ "1")})
    }

  def doubleCorres(x: Int):(String, String) = {
    (corres(x/26),corres(x%26))
  }

    def corres(i: Int): String = i match{

        case 0 => "A"
        case 1 => "B"
        case 2 => "C"
        case 3 => "D"
        case 4 => "E"
        case 5 => "F"
        case 6 => "G"
        case 7 => "H"
        case 8 => "I"
        case 9 => "J"
        case 10 => "K"
        case 11=> "L"
        case 12=> "M"
        case 13=> "N"
        case 14=> "O"
        case 15=> "P"
        case 16=> "Q"
        case 17=> "R"
        case 18=> "S"
        case 19=> "T"
        case 20=> "U"
        case 21=> "V"
        case 22=> "W"
        case 23=> "X"
        case 24=> "Y"
        case 25=> "Z"

    }
}
