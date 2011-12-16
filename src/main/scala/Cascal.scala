package me.remi.cascal

import com.shorrockin.cascal.model._
import com.shorrockin.cascal.session._
import com.shorrockin.cascal.utils.Conversions._


object Cass {

  val hosts = Host("localhost", 9160, 30 * 1000) :: Nil
  val params = new PoolParams(200, ExhaustionPolicy.Fail, 500L, 6, 2)
  lazy val pool = new SessionPool(hosts, params, Consistency.One)
}


object Example {

  private val rgen = scala.util.Random
  private val alpha = ('a' to 'z').toSeq

  def main(args: Array[String]) {
    val cfPeople = Keyspace("Trial") \ "People"

    val cols = Cass.pool.borrow {_.list( cfPeople \ "1" )}
    println(cols.mkString("\n"))

    //Cass.pool.borrow{session => session.insert(Keyspace("Trial") \ "People" \ "1" \ ("firstName".getBytes,"Alan".getBytes))}
    var userid = 0
    val familys = List.fill(10 * 1000){nextLastName()}

    for {
      surname <- familys
      familySize = (rgen.nextGaussian() + 3).toInt.abs max 1 min 8
      givenNames <- List.fill(familySize){nextFirstName()}
      given <- givenNames
    } {
      val ins = List(
        Insert(cfPeople \ userid \ ("firstName", given)),
        Insert(cfPeople \ userid \ ("lastName", surname))
      )

      Cass.pool.borrow{_.batch(ins)}
      userid += 1
    }

    Cass.pool.borrow{
      session =>
        val uid = rgen.nextInt(userid)
        println("userid " + uid + " = ")
        println(session.list(cfPeople \ uid).mkString("\n"))
      }
  }

    def nextAlpha(): Char = alpha(rgen.nextInt(alpha.size))

    /** @return String of length ~ Normal(avgLen, 1.0) bounded by [2,10] */
    def nextName(avgLen: Int): String = {
        val buf = new StringBuilder()

        // `len` taken from [0..avgLen) so final length will be avgLen
        val len = (rgen.nextGaussian() + avgLen).toInt.abs max 1 min 9

        buf += nextAlpha.toUpper
        buf ++= List.fill(len){nextAlpha}
        buf.result
    }

    def nextFirstName(): String = nextName(4)
    def nextLastName(): String = nextName(6)
}
