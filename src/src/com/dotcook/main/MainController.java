package com.dotcook.main;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.application.Application;
import com.dotcook.application.ApplicationController;
import com.dotcook.resources.Properties;
import com.dotcook.user.User;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class MainController implements ApplicationController {
	
	@FXML TreeView<String> leftMenu = null;
	@FXML WebView centralView;
	@FXML BorderPane centerPane;
	@FXML ToolBar toolBar;
	
	private Properties prop;
	private User user;
	private Application app;
	private ObservableList<Application> apps;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {

		fillLeftMenu();
		
		getCenterPane().setLeft(getLeftMenu());

	}
	
	public void fillLeftMenu(){
		
		final TreeView <String> leftMenu = new TreeView<String>();
		
		TreeItem<String> rootItem = new TreeItem<String>(getProp().getPropertyValue("main.menutitle"));
		rootItem.setExpanded(true);
		
		int idx = 0;
		
		for(Application cat : getApps()){

			if((cat.getIdCategory() != 1) && (cat.getIdCategory() > idx)){
				
				idx = cat.getIdCategory();
			
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
		
		leftMenu.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent){
				if(mouseEvent.getClickCount() == 2){
					TreeItem<String> item = leftMenu.getSelectionModel().getSelectedItem();
					for(Application app : getApps()){
						if(item.getValue().equals(app.getDescription())){
							Scene scene = ((Node) mouseEvent.getTarget()).getScene();
							Main main = (Main) scene.getRoot();
							main.setApp(app.getIdApplication());	
							main.setCenterPane(app);
						}
					}
				}
			}
		});
		
		setLeftMenu(leftMenu);
	}
	
	public void setLeftMenu(TreeView<String> leftMenu){
		this.leftMenu = leftMenu;
	}
	
	public TreeView<String> getLeftMenu(){
		return leftMenu;
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
	
	public void setCenterPane(BorderPane centerPane){
		this.centerPane = centerPane;
	}
	
	public BorderPane getCenterPane(){
		return centerPane;
	}
	
	
	@Override
	public void actionBack(ActionEvent e){
	}
	
	@Override	
	public void actionFinish(ActionEvent e){
		System.out.println("MainController: Finish");
		System.exit(0);
	}
	
	@Override
	public void actionCancel(ActionEvent e){
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
