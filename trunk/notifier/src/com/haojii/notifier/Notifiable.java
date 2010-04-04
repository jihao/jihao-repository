package com.haojii.notifier;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要通知的对象列表
 * 
 * @author hao
 *
 */
public abstract class Notifiable {

	List<User> notifierList = new ArrayList<User>();
	
	public List<User> getNotifierList() {
		return notifierList;
	}

	public void addUserToNotifierList(User user)
	{
		if (!notifierList.contains(user))
		{
			notifierList.add(user);
		}
	}
	
	public void removeUserFromNotifierList(User user)
	{
		notifierList.remove(user);
	}
}
