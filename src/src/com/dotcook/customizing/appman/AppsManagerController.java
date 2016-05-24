package com.dotcook.customizing.appman;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.dotcook.application.Application;
import com.dotcook.application.ApplicationController;
import com.dotcook.application.Category;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

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
	
	private boolean editMode;
		
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		fillToolbar();
		
		fillChoiceCategory();
		
		setDisabledForm(true);
		
		setEditMode(false);

	}
	
	public void fillToolbar(){
		
		ToolBar toolbar = getToolbar();
		
		createButtons();
		
		setOnActionToolbarButtons();
		
		toolbar.getItems().addAll(getAddApplication(),
								  getEditApplication(),
								  getRemoveApplication(),
								  new Separator(),
								  getCategories());
		
		setToolbar(toolbar);
		
	}
	
	public void fillChoiceCategory(){
		AppsManager appMan = new AppsManager();
		ObservableList<String> choiceItems = FXCollections.observableArrayList();
		
		for(Category cat : appMan.getCategories()) {			
			choiceItems.add(cat.getDescriptionCategory());			
		}
		
		choiceCategory.setItems(choiceItems);
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
		clearInputs();
		setDisabledForm(false);
		setEditableForm(true);
		getRoot(e).setStatusMessage("Ingrese los datos de la nueva aplicación", 'S');
	}
	
	public void editApplication(Event e){
		
		if(isEditMode()==false){
			setEditMode(true);
			getRoot(e).setStatusMessage("Se ha habilitado la edición de la aplicación", 'S');
		} else {
			setEditMode(false);
			getRoot(e).setStatusMessage("Se ha deshabilitado la edición de la aplicación", 'S');
		}
		
		setEditableForm(isEditMode());
		
	}
	
	public void removeApplication(Event e){
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar aplicación");
		alert.setHeaderText("Esta a punto de eliminar una aplicación");
		alert.setContentText("¿Está seguro que desea eliminar la aplicación?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			
			getRoot(e).setStatusMessage("Aplicación eliminada satisfactoriamente", 'S');		    
		} else {
			getRoot(e).setStatusMessage("Acción cancelada por el usuario", 'S');
		}	
		
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
	
	public void setEditableForm(boolean option){
		
		inputIdApplication.setEditable(false);
		inputNameApplication.setEditable(option);
		inputDescription.setEditable(option);
		inputSource.setEditable(option);
		inputController.setEditable(option);
		inputPosition.setEditable(option);
		//choiceCategory.setEditable(option);		
		
	}
	
	public void clearInputs(){
		
		inputIdApplication.clear();
		inputNameApplication.clear();
		inputDescription.clear();
		inputSource.clear();
		inputController.clear();
		inputPosition.clear();
		//choiceCategory.clear();
		
	}
	
	
	@Override
	public void actionSave(ActionEvent e){
		
	}
	
	@Override
	public void actionCancel(ActionEvent e){
		
	}
	
	@Override
	public void actionSearch(ActionEvent e){
		Dialog<Pair<String,String>> dialog = new Dialog<>();
		dialog.setTitle("Buscar Aplicación...");
		dialog.setHeaderText("Ingresa los parametros de búsqueda");
		
		dialog.setGraphic(new ImageView(getClass().getResource("/com/dotcook/resources/icons/search_32x32.png").toString()));
		
		ButtonType searchButtonType = new ButtonType("Buscar", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(searchButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20,150,10,10));
		
		TextField appName = new TextField();
		appName.setPromptText("Nombre de la aplicación");
		
		TextField description = new TextField();
		description.setPromptText("Descripción");
		
		grid.add(new Label("Nombre:"), 0, 0);
		grid.add(appName, 1, 0);
		grid.add(new Label("Descripción:"), 0, 1);
		grid.add(description, 1, 1);
		
		dialog.getDialogPane().setContent(grid);
		
		Platform.runLater(() -> appName.requestFocus());
		
		dialog.setResultConverter(dialogButton ->{
			if(dialogButton == searchButtonType){
				return new Pair<>(appName.getText(),description.getText());
			}
			return null;
		});
		
		Optional<Pair<String,String>> result = dialog.showAndWait();
		
		result.ifPresent(getApplication -> {
			Application app = new Application();
			AppsManager appMan = new AppsManager();
			app = appMan.searchApplication(appName.getText(), description.getText());
			if(app != null){
				fillScreenAppData(app);
				getRoot(e).setStatusMessage("Búsqueda exitosa", 'S');				
			} else{
				getRoot(e).setStatusMessage("No se encontró ninguna aplicación con los parámetros ingresados", 'E');
			}
		});
		
	}
	
	public void fillScreenAppData(Application app){
		
		inputIdApplication.setText(app.getIdApplication()+"");
		inputNameApplication.setText(app.getNameApplication());
		inputDescription.setText(app.getDescription());
		inputSource.setText(app.getSource());
		inputController.setText(app.getController());
		inputPosition.setText(app.getPosApp()+"");
		setDisabledForm(false);
		setEditableForm(false);
		//((Labeled) choiceCategory).setText(app.getNameCategory());
		
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

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	
}
