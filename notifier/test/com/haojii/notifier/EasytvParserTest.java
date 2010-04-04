package com.haojii.notifier;

import org.junit.Test;

import com.haojii.notifier.easytv.EasytvEntity;
import com.haojii.notifier.easytv.EasytvParserTask;
import com.haojii.notifier.observer.ConsoleNotifier;
import com.haojii.notifier.observer.EmailNotifier;

public class EasytvParserTest {

	@Test
	public void testParsePage() {
		EasytvParserTask ep = new EasytvParserTask(new EasytvEntity("迷失.Lost[第6季]", "http://easytv.echinatv.com.cn/ItemDet.aspx?IID=11768"));
		ConsoleNotifier cn = new ConsoleNotifier();
		cn.addUserToNotifierList(new User("13712612612", "126@126.com", "test"));
		
		EmailNotifier en = new EmailNotifier();
		en.addUserToNotifierList(new User("1376412XXXX", "coolnotifier@gmail.com", "Hao"));
		
		//ep.addObserver(cn);
		ep.addObserver(en);
		
		ep.doTask();
	
	}

}
