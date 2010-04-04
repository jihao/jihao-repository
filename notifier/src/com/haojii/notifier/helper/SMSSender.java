package com.haojii.notifier.helper;

import java.io.IOException;
import java.util.Properties;

class SMSConfig {

	String fetionNumber;
	String fetionPassword;  
}

public class SMSSender {

	private static SMSConfig smsConfig = new SMSConfig();
	static {
		Properties p = new Properties();
		try {
			p.load(SMTPSender.class.getResourceAsStream("sms.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		smsConfig.fetionNumber = p.getProperty("fetionNumber", "smtps");
		smsConfig.fetionPassword = p.getProperty("fetionPassword");
	}
	
	public static void send(String to, String msg) {
		// TODO: 找个现成的用飞信发短信的API，用户需加配置的飞信号为好友
		
	}
}
