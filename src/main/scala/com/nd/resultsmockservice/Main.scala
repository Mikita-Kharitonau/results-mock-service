package com.nd.resultsmockservice

import cats.effect.IO
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._

object Main extends App {

  val endpoints = new Endpoints("results-mock-service")

  val api = endpoints.getReportResults

  def service: Service[Request, Response] = Bootstrap
    .serve[Application.Json](api)
    .toService

  Await.ready(Http.server.serve(":8082", service))
}