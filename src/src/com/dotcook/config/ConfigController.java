package com.dotcook.config;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.application.Application;
import com.dotcook.application.ApplicationController;
import com.dotcook.main.Main;
import com.dotcook.resources.Properties;
import com.dotcook.user.User;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;

public class ConfigController implements ApplicationController{
	
	private User user;
	private Application app;
	private ObservableList<Application> apps;
	private Properties prop;
	
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
	
	@Override
	public Properties getProp() {
		return prop;
	}

	@Override
	public void setProp(Properties prop) {		
		this.prop = prop;		
	}
	
	@Override
	public void actionBack(ActionEvent e){
		System.out.println("ConfigController: Back");
		Scene scene = ((Node) e.getTarget()).getScene();
		Main main = (Main) scene.getRoot();
		main.removeStep();
		main.setCenterPane(main.getLastStep());
	}
	
	@Override	
	public void actionFinish(ActionEvent e){
		System.out.println("ConfigController: Finish");
		Scene scene = ((Node) e.getTarget()).getScene();
		Main main = (Main) scene.getRoot();
		main.removeStep();
		main.setCenterPane(main.getLastStep());
	}
	
	@Override
	public void actionCancel(ActionEvent e){
		System.out.println("ConfigController: Cancel");
		Scene scene = ((Node) e.getTarget()).getScene();
		Main main = (Main) scene.getRoot();
		main.removeStep();
		main.setCenterPane(main.getLastStep());
	}
	
	@Override
	public void actionPrint(ActionEvent e){
	}
	
	@Override
	public void actionSearch(ActionEvent e){
	}
	
	@Override	
	public void actionHelp(ActionEvent e){
	}
	
	@Override
	public void actionSys(ActionEvent e){
	}

}
