package com.dotcook.customizing.appman;

import com.dotcook.application.Application;
import com.dotcook.connection.Connection;

import javafx.collections.ObservableList;

public class AppsManager extends Connection{
	
	private Application app;
	private ObservableList<Application> appsLists;
	
	public AppsManager(){
		
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public ObservableList<Application> getAppsLists() {
		return appsLists;
	}

	public void setAppsLists(ObservableList<Application> appsLists) {
		this.appsLists = appsLists;
	}

}
