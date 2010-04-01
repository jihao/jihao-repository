package com.haojii.notifier;

import org.junit.Test;

public class EasytvParserTest {

	@Test
	public void testParsePage() {
		EasytvParser ep = new EasytvParser(new Item("迷失.Lost[第6季]", "http://easytv.echinatv.com.cn/ItemDet.aspx?IID=11768"));
		ep.checkForUpdate();

		
		
	}

}
