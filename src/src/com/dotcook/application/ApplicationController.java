package com.dotcook.application;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.resources.Properties;
import com.dotcook.user.User;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

public interface ApplicationController extends Initializable{
	
	public abstract void initialize(URL location, ResourceBundle resource);	
	
	public abstract void setUser(User user);
	
	public abstract User getUser();
	
	public abstract void setApp(Application app);
	
	public abstract Application getApp();
	
	public abstract void setApps(ObservableList<Application> apps);
	
	public abstract ObservableList<Application> getApps();
	
	public abstract void setProp(Properties prop);
	
	public abstract Properties getProp();
	
	public abstract void actionBack(ActionEvent e);
	
	public abstract void actionFinish(ActionEvent e);
	
	public abstract void actionCancel(ActionEvent e);
	
	public abstract void actionPrint(ActionEvent e);
	
	public abstract void actionSearch(ActionEvent e);
	
	public abstract void actionHelp(ActionEvent e);
	
	public abstract void actionSys(ActionEvent e);

}
