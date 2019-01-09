package com.nd.resultsmockservice

import cats.effect.IO
import com.typesafe.config.ConfigFactory
import doobie._
import doobie.implicits._

import scala.concurrent.ExecutionContext

class DbService(configBase: String) {

  implicit val cs = IO.contextShift(ExecutionContext.global)

  val xa = Transactor.fromDriverManager[IO](
    ConfigFactory.load().getString(s"$configBase.db.driver"),
    ConfigFactory.load().getString(s"$configBase.db.url"),
    ConfigFactory.load().getString(s"$configBase.db.user"),
    ConfigFactory.load().getString(s"$configBase.db.password")
  )

  def getReportByJobId(jobId: String): Option[ReportResults] =
    sql"select * from lci_report where job_id = $jobId"
      .query[ReportResults]
      .option
      .transact(xa)
      .unsafeRunSync

}