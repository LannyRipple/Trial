package me.remi.cascal

import com.shorrockin.cascal.model._
import com.shorrockin.cascal.session._

object Cass {

    val hosts = Host("localhost", 9160, 30 * 1000) :: Nil
    val params = new PoolParams(200, ExhaustionPolicy.Fail, 500L, 6, 2)
    lazy val pool = new SessionPool(hosts, params, Consistency.One)
}

object Example {

    def main(av: Array[String]) {
        val cols = Cass.pool.borrow {_.list( Keyspace("Trail") \ "People" \ "1" )}
        println(cols.mkString("\n"))
    }
}
