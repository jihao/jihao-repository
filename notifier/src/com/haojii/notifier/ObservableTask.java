package com.haojii.notifier;

import java.util.Observable;

public abstract class ObservableTask extends Observable{
	
	public abstract void doTask() throws Exception;
}
