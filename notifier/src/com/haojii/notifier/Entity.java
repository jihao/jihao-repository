package com.haojii.notifier;

public interface Entity {
	String message();
	
	String plainMessage();
	String emailSubject();
	String emailBodyText();
	String smsMessage();
}
