package com.near.platform.placesExtraction;

import mcm.MessageCodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.near.platform.placesExtraction", "mcm", "utility", "mongodb"})
public class NearServiceCoreApplication {

	private static Logger logger = LoggerFactory.getLogger(NearServiceCoreApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(NearServiceCoreApplication.class, args);
		try{
			MessageCodeConfig messageCodeConfig = (MessageCodeConfig) ctx.getBean("messageCodeConfig");
			if (messageCodeConfig == null || messageCodeConfig.getPlatform() == null) {
				logger.error("Unable to find messageCodes in yml file, please check download messageCodes api during build, Shutting down..");
				ctx.close();
			}
		}catch(NoSuchBeanDefinitionException e){
			logger.error("Unable to load messageCodesConfig bean from context, Shutting down..");
			ctx.close();
		}

	}
}