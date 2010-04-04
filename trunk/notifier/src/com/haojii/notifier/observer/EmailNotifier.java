package com.haojii.notifier.observer;

import java.util.Observable;
import java.util.Observer;

import com.haojii.notifier.Entity;
import com.haojii.notifier.Notifiable;
import com.haojii.notifier.User;
import com.haojii.notifier.helper.SMTPSender;

/**
 * 邮件通知器结合手机邮箱可达到手机提醒的效果
 * 
 * 若使用GoogleAppEngine发邮件的代码可以简化，不需要多少配置
 * 
 * @author hao
 *
 */
public class EmailNotifier extends Notifiable implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		for (User user : this.getNotifierList()) {
			System.out.println("Email to "+user.email +" : \n\n"+ ((Entity)arg).plainMessage());
			SMTPSender.send(user.email, ((Entity)arg).emailSubject(), ((Entity)arg).emailBodyText());
		} 

	}

}
