package com.nd.resultsmockservice

import cats.effect.IO
import io.finch._
import io.finch.catsEffect._
import decorators.withInternalServerError
import scala.language.postfixOps


class Endpoints(configBase: String) {

  val rootUrl = "api" :: "v1"

  val dbService = new DbService(configBase)

  def getReportResults: Endpoint[IO, ReportResults] = get(rootUrl :: "job" :: path[String]) {
    jobId: String =>
      withInternalServerError[ReportResults] {
        val report = dbService.getReportByJobId(jobId)
        report match {
          case None => NoContent
          case Some(report: ReportResults) => Ok(report)
        }
      }
  }
}
