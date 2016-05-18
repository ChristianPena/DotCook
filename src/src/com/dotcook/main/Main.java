package com.dotcook.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.dotcook.application.Application;
import com.dotcook.user.User;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Main extends BorderPane{
	
	private HBox topPane = null;
	private Node centerPane = null;
	private VBox bottomPane = null;
	
//  Top Pane objects
	private VBox leftTopPane;
	private ToolBar toolbar;
	private ToolBar appTitleBar;
	private ImageView viewLogo;
	private Image logo;
	private String appTitle;
	
//  Other objects
	private Double sizeHeight;
	private Double sizeWidth;
	
	private User user;
	private Application app;
	private ObservableList<Application> apps;
	
	public Main(){
		
		getScreenSize();
		
		setTopPane();
		setCenterPane(getApp());
		setBottomPane();	
		
		setTop(getTopPane());
		setCenter(getCenterPane());
		setBottom(getBottomPane());
	}
	
	public HBox getTopPane() {
		return topPane;
	}

	public void setTopPane() {
		
		HBox topPane = new HBox(2);
		
		setViewLogo();
		setLeftTopPane();
		
		topPane.getChildren().addAll(getLeftTopPane(),getViewLogo());
		
		this.topPane = topPane;
	}
		
	public VBox getBottomPane() {
		return bottomPane;
	}

	public void setBottomPane() {
		
		VBox bottomPane = new VBox();
		
		bottomPane.setMinWidth(getSizeWidth());
		
		
		this.bottomPane = bottomPane;
	}

	public Node getCenterPane() {
		return centerPane;
	}

	public void setCenterPane(Application app) {
		Node centerPane = null;
		
		try{
			
			this.setCenter(null);
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(app.getSource()));
			centerPane = (Node) fxmlLoader.load();
			setAppTitle(app.getDescription());
			this.setCenter(centerPane);
			
		}catch(Exception e){
			
		}
		
		this.centerPane = centerPane;
	}

	public ToolBar getToolbar() {
		return toolbar;
	}

	public void setToolbar() {
		
		ToolBar toolbar = new ToolBar(
			new Button("New"),
			new Button("Open"),
			new Button("Save")
		);	
		
		this.toolbar = toolbar;
	}

	public ImageView getViewLogo() {
		return viewLogo;
	}

	public void setViewLogo() {
		
		ImageView viewLogo = new ImageView();
		
		setLogo();
		viewLogo.setImage(getLogo());
		viewLogo.setFitWidth(159.38);
		viewLogo.setFitHeight(65);
		
		this.viewLogo = viewLogo;
		
	}

	public Image getLogo() {
		return logo;
	}

	public void setLogo() {
		Image logo = new Image(Main.class.getResourceAsStream("/com/dotcook/resources/logo/logo.png"));
		this.logo = logo;
	}
	
	public void getScreenSize(){
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setSizeHeight(screenSize.getHeight());
		setSizeWidth(screenSize.getWidth());
		
	}

	public Double getSizeHeight() {
		return sizeHeight;
	}

	public void setSizeHeight(Double sizeHeight) {
		this.sizeHeight = sizeHeight;
	}

	public Double getSizeWidth() {
		return sizeWidth;
	}

	public void setSizeWidth(Double sizeWidth) {
		this.sizeWidth = sizeWidth;
	}

	public VBox getLeftTopPane() {
		return leftTopPane;
	}

	public void setLeftTopPane() {
		VBox leftTopPane = new VBox();
		
		leftTopPane.setMinWidth(getSizeWidth() - getViewLogo().getFitWidth());
		
		setToolbar();
		setAppTitleBar();
		
		leftTopPane.getChildren().addAll(getToolbar(),getAppTitleBar());
		
		this.leftTopPane = leftTopPane;
	}

	public ToolBar getAppTitleBar() {
		return appTitleBar;
	}

	public void setAppTitleBar() {
		
		ToolBar appTitleBar = new ToolBar();
		
		setAppTitle("App.Title");
		
		Label labelTitle = new Label(getAppTitle());
		
		labelTitle.setFont(new Font("Arial",20));
		
		appTitleBar.getItems().add(labelTitle);
		
		this.appTitleBar = appTitleBar;
	}

	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUser(String username){
		
		User user = new User();
		user.getUser(username);
		
		this.user = user;
		
	}
	
	public Application getApp(){
		return app;
	}

	public ObservableList<Application> getApps() {
		return apps;
	}

	public void setApps(ObservableList<Application> apps) {
		this.apps = apps;
	}
	
	

}
