package com.near.platform.placesExtraction.service;

import mailer.NearMailerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailerService implements Runnable{

  private NearMailerService nearMailerService;
  private String from;
  private String to;
  private String subject;
  private String body;

  private static final Logger LOGGER = LoggerFactory.getLogger(MailerService.class);

  public MailerService(NearMailerService nearMailerService, String from, String to, String subject, String body) {
    this.nearMailerService = nearMailerService;
    this.from = from;
    this.to = to;
    this.subject = subject;
    this.body = body;
  }

  public void run() {
    try {
      nearMailerService.sendMail(from, to, subject, body);
    } catch (Exception e){
      LOGGER.error("Mailer error {}", e);
    }
  }
}