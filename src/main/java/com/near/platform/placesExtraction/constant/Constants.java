package com.near.platform.placesExtraction.constant;

public interface Constants {
  String MAILER_FROM_ADDRESS = "no-reply@adnear.net";
  String SUCCESS_SUBJECT = "Request for data upload/update to database was successful";
  String FAILURE_SUBJECT = "Request for data upload/update failed";
  String REDIS_KEY= "livyJobQueue";
  String LIVY_SUCCESS_SUBJECT = "Livy request execution was successful";
  String LIVY_FAILURE_SUBJECT = "Livy request execution failed";
  String LIVY_SUCCESS_BODY = "Dear user,<br><br>Livy request from queue executed successfully.<br>";
  String LIVY_FAILURE_BODY = "Dear user,<br><br>Previous livy request failed. Now executing next request from queue<br>";
  String PLACES_TEAM_EMAIL = "ajay@near.co";
}
