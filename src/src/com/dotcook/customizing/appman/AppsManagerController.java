package com.dotcook.customizing.appman;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.application.ApplicationController;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AppsManagerController extends ApplicationController {
	
	@FXML private ToolBar toolbar;
	
	private Button addApplication;
	private Button editApplication;
	private Button removeApplication;
	private Button categories;
	
	@FXML TextField inputIdApplication;
	@FXML TextField inputNameApplication;
	@FXML TextField inputDescription;
	@FXML TextField inputSource;
	@FXML TextField inputController;
	@FXML TextField inputPosition;
	@FXML ChoiceBox<String> choiceCategory;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		fillToolbar();
		
		setDisabledForm(true);

	}
	
	public void fillToolbar(){
		
		ToolBar toolbar = getToolbar();
		
		createButtons();
		
		setOnActionToolbarButtons();
		
		toolbar.getItems().addAll(getAddApplication(), getEditApplication(), getRemoveApplication(), 
									new Separator(), getCategories());
		
		setToolbar(toolbar);
		
	}
	
	private void createButtons(){
		
		Double buttonWidth = 120.0;
		
		Button addApplication    = new Button("Nueva");
		addApplication.setGraphic(
				new ImageView(
						new Image(getClass()
								.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/application_add.png"))));
		addApplication.setMinWidth(buttonWidth);
		setAddApplication(addApplication);
		
		Button editApplication   = new Button("Editar");
		editApplication.setGraphic(
				new ImageView(
						new Image(getClass()
								.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/application_edit.png"))));
		editApplication.setMinWidth(buttonWidth);
		setEditApplication(editApplication);
		
		Button removeApplication = new Button("Eliminar");
		removeApplication.setGraphic(
				new ImageView(
						new Image(getClass()
								.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/application_delete.png"))));
		removeApplication.setMinWidth(buttonWidth);
		setRemoveApplication(removeApplication);
		
		Button categories        = new Button("Categorías");
		categories.setGraphic(
				new ImageView(
						new Image(getClass()
								.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/category.png"))));
		categories.setMinWidth(buttonWidth);
		setCategories(categories);
				
	}
	
	public void setOnActionToolbarButtons(){
		
		EventHandler<ActionEvent> eHandler = new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				
				try{
										
					Button btnPressed = (Button) e.getSource();
					
					if(btnPressed.getText().equals(getAddApplication().getText()))
						addApplication(e);
					
					if(btnPressed.getText().equals(getEditApplication().getText()))
						editApplication(e);
					
					if(btnPressed.getText().equals(getRemoveApplication().getText()))
						removeApplication(e);
					
					if(btnPressed.getText().equals(getCategories().getText()))
						showCategories(e);
				
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		};
		
		getAddApplication().setOnAction(eHandler);
		getEditApplication().setOnAction(eHandler);
		getRemoveApplication().setOnAction(eHandler);
		getCategories().setOnAction(eHandler);
		
	}
	
	public void addApplication(Event e){
		getRoot(e).setStatusMessage("Add Application Pressed!", 'S');
	}
	
	public void editApplication(Event e){
		getRoot(e).setStatusMessage("Edit Application Pressed!", 'S');
	}
	
	public void removeApplication(Event e){
		getRoot(e).setStatusMessage("Remove Application Pressed!", 'S');
	}
	
	public void showCategories(Event e){
		getRoot(e).setStatusMessage("Categories Pressed!", 'S');
	}
	
	public void setDisabledForm(boolean option){
		
		inputIdApplication.setDisable(option);
		inputNameApplication.setDisable(option);
		inputDescription.setDisable(option);
		inputSource.setDisable(option);
		inputController.setDisable(option);
		inputPosition.setDisable(option);
		choiceCategory.setDisable(option);
		
	}
	
	public ToolBar getToolbar(){
		return toolbar;
	}
	
	public void setToolbar(ToolBar toolbar){
		this.toolbar = toolbar;
	}

	public Button getAddApplication() {
		return addApplication;
	}

	public void setAddApplication(Button addApplication) {
		this.addApplication = addApplication;
	}

	public Button getEditApplication() {
		return editApplication;
	}

	public void setEditApplication(Button editApplication) {
		this.editApplication = editApplication;
	}

	public Button getRemoveApplication() {
		return removeApplication;
	}

	public void setRemoveApplication(Button removeApplication) {
		this.removeApplication = removeApplication;
	}

	public Button getCategories() {
		return categories;
	}

	public void setCategories(Button categories) {
		this.categories = categories;
	}
	
}
