package com.dotcook.application;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.main.Main;
import com.dotcook.resources.Properties;
import com.dotcook.user.User;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;

public class ApplicationController implements ApplicationInterface{
	
	private User user;
	private Application app;
	private ObservableList<Application> apps;
	private Properties prop;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {		
		
	}
	
	public Main getRoot(Event e){
		Scene scene = ((Node) e.getTarget()).getScene();
		Main root = (Main) scene.getRoot();		
		return root;		
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
	
	@Override
	public Properties getProp() {
		return prop;
	}

	@Override
	public void setProp(Properties prop) {		
		this.prop = prop;		
	}
	
	@Override
	public void actionSave(ActionEvent e) {
		
	}
	
	@Override
	public void actionBack(ActionEvent e) {
		
	}

	@Override
	public void actionFinish(ActionEvent e) {
		
	}

	@Override
	public void actionCancel(ActionEvent e) {
		
	}

	@Override
	public void actionPrint(ActionEvent e) {
		
	}

	@Override
	public void actionSearch(ActionEvent e) {
		
	}

	@Override
	public void actionHelp(ActionEvent e) {
		
	}

	@Override
	public void actionSys(ActionEvent e) {
		
	}

}
