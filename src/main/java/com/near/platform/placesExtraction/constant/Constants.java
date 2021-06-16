package com.near.platform.placesExtraction.constant;

public interface Constants {
  String MAILER_FROM_ADDRESS = "no-reply@adnear.net";
  String SUCCESS_SUBJECT = "Request for data upload/update to database was successful";
  String FAILURE_SUBJECT = "Request for data upload/update failed";
  String REDIS_KEY= "livyJobQueue";
  String LIVY_SUCCESS_SUBJECT = "Livy request execution was successful";
  String LIVY_FAILURE_SUBJECT = "Livy request execution failed";
}
