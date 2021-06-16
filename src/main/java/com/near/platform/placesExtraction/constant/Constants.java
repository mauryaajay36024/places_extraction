package com.near.platform.placesExtraction.constant;

public interface Constants {
  String MAILER_FROM_ADDRESS = "no-reply@adnear.net";
  String SUCCESS_SUBJECT = "Request for data upload/update to database was successful";
  String FAILURE_SUBJECT = "Request for data upload/update failed";
  String UPDATE_FAILURE_BODY = "Dear user,<br><br>Your request for data update is failed.<br>";
  String UPDATE_SUCCESS_BODY = "Dear user,<br><br>Your request for data update is complete.<br>"+ "<br>";
  String REDIS_KEY= "livyJobQueue";
  String LIVY_SUCCESS_SUBJECT = "Livy request execution was successful";
  String LIVY_FAILURE_SUBJECT = "Livy request execution failed";
  String LIVY_SUCCESS_BODY = "Dear user,<br><br>Livy request from queue executed successfully.<br>";
  String LIVY_FAILURE_BODY = "Dear user,<br><br>Previous livy request failed. Now executing next request from queue<br>";
  String PLACES_TEAM_EMAIL = "ajay@near.co";
}
