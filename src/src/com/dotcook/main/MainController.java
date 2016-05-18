package com.dotcook.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.application.Application;
import com.dotcook.resources.Properties;
import com.dotcook.user.User;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class MainController implements Initializable {
	
	@FXML TreeView<String> leftMenu;
	@FXML WebView centralView;
	@FXML BorderPane centerPane;
	@FXML ToolBar toolBar;
	
	private Properties prop;
	private User user;
	private Application app;
	private ObservableList<Application> apps;
	
	FXMLLoader loader = null;

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		try {
			
			loader = new FXMLLoader(location);
			
			Parent parent = (Parent) loader.load();
			
			setProp();		
			fillLeftMenu();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void fillLeftMenu(){
		
		final TreeView <String> leftMenu = new TreeView<String>();
		
		TreeItem<String> rootItem = new TreeItem<String>(getProp().getPropertyValue("main.menutitle"));
		rootItem.setExpanded(true);
		
		int idx = 1;
		
		for(Application cat : getApps()){

			if((cat.getIdCategory() != 1)&&(cat.getIdCategory() == idx)){
			
				TreeItem<String> category = new TreeItem<String>(cat.getDescriptionCategory());
				category.setExpanded(true);
				
				for(Application app : getApps()){
					
					if(app.getIdCategory() == cat.getIdCategory()){
						
						TreeItem<String> item = new TreeItem<String>(app.getDescription());
						category.getChildren().add(item);
						
					}
					
				}
				
				rootItem.getChildren().add(category);
			
			}
			
		}
		
		leftMenu.setRoot(rootItem);
		
		setLeftMenu(leftMenu);
	}
	
	public void setLeftMenu(TreeView<String> leftMenu){
		this.leftMenu = leftMenu;
	}
	
	public TreeView<String> getLeftMenu(){
		return leftMenu;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp() {
		
		Properties prop = new Properties();
		prop.setProp();
		this.prop = prop;		
	}

	public void setUser(User user) {
		this.user = user;		
	}

	public User getUser() {		
		return user;
	}

	public void setApp(Application app) {
		this.app = app;		
	}

	public Application getApp() {
		return app;
	}

	public void setApps(ObservableList<Application> apps) {
		this.apps = apps;
	}

	public ObservableList<Application> getApps() {
		return apps;
	}
	

}
