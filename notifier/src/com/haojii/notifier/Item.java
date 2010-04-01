package com.haojii.notifier;

import java.util.HashMap;
import java.util.Map;

public class Item {
	
	String title;
	String url;
	Map<String,String> linksMap = new HashMap<String, String>();
	Map<String,String> newLinksMap = new HashMap<String, String>();
	
	public Item(String title, String url) {
		this.title = title;
		this.url = url;
	}
}
