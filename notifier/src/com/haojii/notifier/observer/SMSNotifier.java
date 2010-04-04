package com.haojii.notifier.observer;

import java.util.Observable;
import java.util.Observer;

import com.haojii.notifier.Entity;
import com.haojii.notifier.Notifiable;
import com.haojii.notifier.User;
import com.haojii.notifier.helper.SMSSender;

/**
 * 
 * 
 * @author hao
 *
 */
public class SMSNotifier extends Notifiable implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		for (User user : this.getNotifierList()) {
			System.out.println("SMS "+user.nickName +" : "+ ((Entity)arg).smsMessage());
			SMSSender.send(user.cellphone, ((Entity)arg).smsMessage());
		} 
	}

}
