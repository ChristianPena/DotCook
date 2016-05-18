package com.dotcook.config;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.application.Application;
import com.dotcook.application.ApplicationController;
import com.dotcook.user.User;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

public class ConfigController implements Initializable, ApplicationController{
	
	private User user;
	private Application app;
	private ObservableList<Application> apps;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
				
	}
	
	@Override
	public void setUser(User user) {
		this.user = user;		
	}

	@Override
	public User getUser() {		
		return user;
	}

	@Override
	public void setApp(Application app) {
		this.app = app;		
	}

	@Override
	public Application getApp() {
		return app;
	}

	@Override
	public void setApps(ObservableList<Application> apps) {
		this.apps = apps;
	}

	@Override
	public ObservableList<Application> getApps() {
		return apps;
	}

}
