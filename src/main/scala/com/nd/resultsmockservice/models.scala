package com.nd.resultsmockservice


case class ReportResults(
  jobId: String,
  campaignId: Int,
  reportDate: String,
  numberOfVisitors: Option[Int],
  numberOfImpressions: Option[Int],
  status: String
)
