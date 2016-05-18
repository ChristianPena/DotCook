package com.dotcook.application;

import com.dotcook.user.User;

import javafx.collections.ObservableList;

public interface ApplicationController{
	
	
	public abstract void setUser(User user);
	
	public abstract User getUser();
	
	public abstract void setApp(Application app);
	
	public abstract Application getApp();
	
	public abstract void setApps(ObservableList<Application> apps);
	
	public abstract ObservableList<Application> getApps();

}
