package com.haojii.notifier.easytv;

import java.util.ArrayList;
import java.util.List;

import com.haojii.notifier.Entity;

public class EasytvEntity implements Entity{
	
	String title;
	String url;
	//Map<String,String> linksMap = new HashMap<String, String>();
	//Map<String,String> newLinksMap = new HashMap<String, String>();
	
	List<EasytvItem> items = new ArrayList<EasytvItem>();
	List<EasytvItem> newItems = new ArrayList<EasytvItem>();
	
	public EasytvEntity(String title, String url) {
		this.title = title;
		this.url = url;
	}

	@Override
	public String toString() {
		return "Item [linksMap=" + items + ", newLinksMap=" + newItems
				+ ", title=" + title + ", url=" + url + "]";
	}

	@Override
	public String message() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("New!!!");
		sb.append("\n");
		sb.append(title);
		sb.append("\n");
		for (EasytvItem e : newItems)
		{
			sb.append(e.itemTitle);
			sb.append("\n");
		}
		
		return sb.toString();
	}

	@Override
	public String emailBodyText() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("New!!!");
		sb.append("<br/>\n");
		sb.append(title);
		sb.append("<br/>\n");
		sb.append("<a href=\""+url+"\">"+title+"</a>");
		sb.append("<br/>\n");
		for (EasytvItem e : newItems)
		{
			sb.append("<a href=\""+e.itemLink+"\">"+e.itemTitle+"</a>");
			sb.append("<br/>\n");
		}
		
		return sb.toString();
	}

	@Override
	public String plainMessage() {
		return message();
	}

	@Override
	public String smsMessage() {
		return message();
	}

	@Override
	public String emailSubject() {
		return "New!!!"+title;
	}
	
}
