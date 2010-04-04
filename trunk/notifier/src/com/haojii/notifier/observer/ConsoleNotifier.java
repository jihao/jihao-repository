package com.haojii.notifier.observer;

import java.util.Observable;
import java.util.Observer;

import com.haojii.notifier.Entity;
import com.haojii.notifier.Notifiable;
import com.haojii.notifier.User;

/**
 * 命令行打印更新信息
 * 
 * @author hao
 *
 */
public class ConsoleNotifier extends Notifiable implements Observer {

	
	
	@Override
	public void update(Observable o, Object arg) {
		for (User user : this.getNotifierList()) {
			System.out.println("通知 "+user.nickName +" : "+ ((Entity)arg).plainMessage());
		} 
	}

}
