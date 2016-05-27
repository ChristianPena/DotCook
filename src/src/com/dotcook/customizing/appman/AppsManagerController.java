package com.dotcook.customizing.appman;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.dotcook.application.Application;
import com.dotcook.application.ApplicationController;
import com.dotcook.application.Category;
import com.dotcook.application.ToolbarApplication;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;

public class AppsManagerController extends ApplicationController {
	
	@FXML private ToolBar toolbar;
	
	private Button addApplication;
	private Button editApplication;
	private Button removeApplication;
	private Button categories;
	private Button refresh;
	
	@FXML TextField inputIdApplication;
	@FXML TextField inputNameApplication;
	@FXML TextField inputDescription;
	@FXML TextField inputSource;
	@FXML TextField inputController;
	@FXML TextField inputPosition;
	@FXML ChoiceBox<String> choiceCategory;
	
	@FXML CheckBox chkSave;
	@FXML CheckBox chkCancel;
	@FXML CheckBox chkPrint;
	@FXML CheckBox chkSearch;
	
	@FXML TableView<Application> viewApplications;
	
	private boolean editMode;
	private ObservableList<String> choiceItems;
	private ObservableList<ToolbarApplication> toolbarApps;
	private ObservableList<Category> obsCategories;
	
	private AppsManager appMan = new AppsManager();
		
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		fillToolbar();
		
		fillChoiceCategory();
		
		setDisabledForm(true);
		
		setEditMode(false);
		
		fillViewApplications();

	}
	
	public AppsManager getAppMan() {
		return appMan;
	}

	public void setAppMan(AppsManager appMan) {
		this.appMan = appMan;
	}

	public void fillToolbar(){
		
		ToolBar toolbar = getToolbar();
		
		createButtons();
		
		setOnActionToolbarButtons();
		
		toolbar.getItems().addAll(getAddApplication(),
								  getEditApplication(),
								  getRemoveApplication(),
								  new Separator(),
								  getCategories(),
								  getRefresh());
		
		setToolbar(toolbar);
		
	}
	
	public void fillChoiceCategory(){
		AppsManager appMan = new AppsManager();
		ObservableList<String> choiceItems = FXCollections.observableArrayList();
		
		setObsCategories(appMan.getCategories());
		
		for(Category cat : getObsCategories()) {			
			choiceItems.add(cat.getDescriptionCategory());			
		}
		
		setChoiceItems(choiceItems);
		
		choiceCategory.setItems(getChoiceItems());
	}
	
	public void createButtons(){
		
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
		
		Button categories        = new Button("Categorias");
		categories.setGraphic(
				new ImageView(
						new Image(getClass()
								.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/category.png"))));
		categories.setMinWidth(buttonWidth);
		setCategories(categories);
		
		Button refresh           = new Button("Actualizar");
		refresh.setGraphic(
				new ImageView(
						new Image(getClass()
								.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/arrow_refresh.png"))));
		refresh.setMinWidth(buttonWidth);
		setRefresh(refresh);
				
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
					
					if(btnPressed.getText().equals(getRefresh().getText()))
						refresh(e);
				
				}catch(Exception ex){
					ex.printStackTrace();
					launchExceptionDialog(ex);
				}
				
			}
		};
		
		getAddApplication().setOnAction(eHandler);
		getEditApplication().setOnAction(eHandler);
		getRemoveApplication().setOnAction(eHandler);
		getCategories().setOnAction(eHandler);
		getRefresh().setOnAction(eHandler);
		
	}
	
	public void addApplication(Event e){
		clearInputs();
		setDisabledForm(false);
		setEditableForm(true);
		getRoot(e).setStatusMessage("Ingrese los datos de la nueva aplicacion", 'S');
	}
	
	public void editApplication(Event e){
		
		if(isEditMode()==false){
			setEditMode(true);
			getRoot(e).setStatusMessage("Se ha habilitado la edicion de la aplicacion", 'S');
		} else {
			setEditMode(false);
			getRoot(e).setStatusMessage("Se ha deshabilitado la edicion de la aplicacion", 'S');
		}
		
		setEditableForm(isEditMode());
		
	}
	
	public void removeApplication(Event e){
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar aplicacion");
		alert.setHeaderText("Esta a punto de eliminar una aplicacion");
		alert.setContentText("¿Está seguro que desea eliminar la aplicacion?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			
			if(!(inputIdApplication.getText()).equals("")){
				try{
					AppsManager appMan = new AppsManager();
					if(appMan.deleteApplication(Integer.parseInt(inputIdApplication.getText()))==true){
						getRoot(e).setStatusMessage("Aplicacion eliminada satisfactoriamente", 'S');
						clearInputs();
					} else {
						getRoot(e).setStatusMessage("Ha ocurrido un error", 'E');
					}
				}catch(Exception ex){
					ex.printStackTrace();
					getRoot(e).showDump(ex);
				}
			}else{
				getRoot(e).setStatusMessage("Debe seleccionar una aplicacion primero", 'E');
			}
			getRoot(e).setStatusMessage("Aplicacion eliminada satisfactoriamente", 'S');
		} else {
			getRoot(e).setStatusMessage("Accion cancelada por el usuario", 'S');
		}	
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void showCategories(Event e){
		
		try{
			
			ObservableList<Category> obsCat = getObsCategories();
			
			Dialog<Pair<String, String>> dialog = new Dialog<>();
			dialog.setTitle("Gestion de categorias");
			dialog.setHeaderText("Listado de categorias");
	
			dialog.setGraphic(
					new ImageView(getClass()
							.getResource("/com/dotcook/resources/icons/FatCow_Icons32x32/category.png").toString()));
	
			ButtonType saveButtonType = new ButtonType("Aceptar", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
			
			TableView<Category> viewCats = new TableView<Category>();
			
			viewCats.setEditable(true);
			
			TableColumn colIdCategory = new TableColumn<Category, String>("ID Cat.");
			colIdCategory.setMinWidth(50);
			colIdCategory.setCellValueFactory(
					new PropertyValueFactory<Category,String>("idCategory"));
			
			TableColumn colNameCategory = new TableColumn<Category, String>("Nombre Categoria");
			colNameCategory.setEditable(true);
			colNameCategory.setMinWidth(150);
			colNameCategory.setCellValueFactory(
					new PropertyValueFactory<Category,String>("nameCategory"));
			colNameCategory.setCellFactory(TextFieldTableCell.forTableColumn());
			colNameCategory.setOnEditCommit(
		            new EventHandler<CellEditEvent<Category, String>>() {
		                @Override
		                public void handle(CellEditEvent<Category, String> t) {
		                    ((Category) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setNameCategory(t.getNewValue());
		                }
		            }
		    );
			
			TableColumn colDescription = new TableColumn<Category, String>("Descripcion");
			colDescription.setMinWidth(50);
			colDescription.setCellValueFactory(
					new PropertyValueFactory<Category,String>("descriptionCategory"));
			colDescription.setCellFactory(TextFieldTableCell.forTableColumn());
			colDescription.setOnEditCommit(
		            new EventHandler<CellEditEvent<Category, String>>() {
		                @Override
		                public void handle(CellEditEvent<Category, String> t) {
		                    ((Category) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setDescriptionCategory(t.getNewValue());
		                }
		            }
		    );
			
			TableColumn<Category,Integer> colPosition = new TableColumn<Category,Integer>("Posicion");
			colPosition.setMinWidth(50);
			colPosition.setCellValueFactory(new PropertyValueFactory<Category,Integer>("positionCategory"));
			colPosition.setCellFactory(TextFieldTableCell.<Category, Integer>forTableColumn(new IntegerStringConverter()));
			colPosition.setOnEditCommit(
		            new EventHandler<CellEditEvent<Category, Integer>>() {
		                @Override
		                public void handle(CellEditEvent<Category, Integer> t) {
		                    ((Category) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setPositionCategory(t.getNewValue());
		                }
		            }
		    );
			
			viewCats.setItems(obsCat);
			viewCats.getColumns().addAll(colIdCategory,colNameCategory,colDescription,colPosition);
			
			BorderPane borderPane = new BorderPane();
			
			Button save = new Button("Grabar",
					new ImageView(new Image(getClass()
							.getResourceAsStream("/com/dotcook/resources/icons/save_16x16.png"))));
			save.setMinWidth(100);
			save.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					getAppMan().saveCategories(obsCat);
					getRoot(e).setStatusMessage("Accion ejecutada sin errores", 'S');
					getRoot(e).setApps();
					refresh(e);
				}				
			});
			
			Button newRow = new Button("Nueva",
					new ImageView(new Image(getClass()
							.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/table_row_insert.png"))));
			newRow.setMinWidth(100);
			newRow.setOnAction(new EventHandler<ActionEvent>(){
				int idNewApp = 9000;
				@Override
				public void handle(ActionEvent event){
					Category newCat = new Category();
					idNewApp++;
					newCat.setIdCategory(idNewApp);
					obsCat.add(newCat);
				}
			});
			
			Button delRow = new Button("Eliminar",
					new ImageView(new Image(getClass()
							.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/table_row_delete.png"))));
			delRow.setMinWidth(100);
			delRow.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Eliminar categoria");
					alert.setHeaderText("Si elimina una categoria, eliminara tambien las aplicaciones asociadas");
					alert.setContentText("�Estas seguro que deseas continuar?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
					    getAppMan().deleteCategory(viewCats.getSelectionModel().getSelectedItem());
					    getRoot(e).setStatusMessage("Accion ejecutada sin errores", 'S');
					    obsCat.remove(viewCats.getSelectionModel().getSelectedItem());
					    refresh(e);
					}else{
						getRoot(e).setStatusMessage("Accion cancelada por el usuario", 'S');
					}
				}
			});
			
			borderPane.setTop(new ToolBar(save,newRow,delRow));
			borderPane.setCenter(viewCats);
			
			dialog.getDialogPane().setContent(borderPane);
			
			Platform.runLater(() -> viewCats.requestFocus());
			
			dialog.show();
		
		} catch(Exception ex){
			
			ex.printStackTrace();
			getRoot(e).showDump(ex);
			
		}
		
	}
	
	public void refresh(Event e){
		
		fillChoiceCategory();
		fillViewApplications();
		getRoot(e).setStatusMessage("Se han actualizado los datos", 'S');
		
	}
	
	public void setDisabledForm(boolean option){
		
		inputIdApplication.setDisable(option);
		inputNameApplication.setDisable(option);
		inputDescription.setDisable(option);
		inputSource.setDisable(option);
		inputController.setDisable(option);
		inputPosition.setDisable(option);
		choiceCategory.setDisable(option);
		
		chkSave.setDisable(option);
		chkCancel.setDisable(option);
		chkPrint.setDisable(option);
		chkSearch.setDisable(option);
		
	}
	
	public void setEditableForm(boolean option){
		
		boolean disable = false;
		
		inputIdApplication.setEditable(false);
		inputNameApplication.setEditable(option);
		inputDescription.setEditable(option);
		inputSource.setEditable(option);
		inputController.setEditable(option);
		inputPosition.setEditable(option);		
		
		if(option==true)
			disable = false;
		else
			disable = true;
		
		choiceCategory.setDisable(disable);
		
		chkSave.setDisable(disable);
		chkCancel.setDisable(disable);
		chkPrint.setDisable(disable);
		chkSearch.setDisable(disable);
		
	}
	
	public void clearInputs(){
		
		inputIdApplication.clear();
		inputNameApplication.clear();
		inputDescription.clear();
		inputSource.clear();
		inputController.clear();
		inputPosition.clear();
		choiceCategory.setValue(null);
		
		chkSave.setSelected(false);
		chkCancel.setSelected(false);
		chkPrint.setSelected(false);
		chkSearch.setSelected(false);
		
	}
	
	
	@Override
	public void actionSave(ActionEvent e){
		
		AppsManager appMan = new AppsManager();		
		Application saveApp = new Application();
		
		boolean hasSave   = false;
		boolean hasCancel = false;
		boolean hasPrint  = false;
		boolean hasSearch = false;
		
		boolean result = false;
		
		char mode;
		
		try{
			
			int idApp = 0;
		
			if(!(inputIdApplication.getText()).equals("")){
				mode = 'U';
				idApp = Integer.parseInt(inputIdApplication.getText());
			} else {
				mode = 'I';
			}	
			
			saveApp.setIdApplication(idApp);
			saveApp.setNameApplication(inputNameApplication.getText());
			saveApp.setDescription(inputDescription.getText());
			saveApp.setSource(inputSource.getText());
			saveApp.setController(inputController.getText());
			saveApp.setPosApp(Integer.parseInt(inputPosition.getText()));
			
			for(Category cat : getObsCategories()){			
				if((choiceCategory.getValue()).equals(cat.getDescriptionCategory()))
					saveApp.setIdCategory(cat.getIdCategory());						
			}
			
			if(chkSave.isSelected())
				hasSave = true;
			
			if(chkCancel.isSelected())
				hasCancel = true;
			
			if(chkPrint.isSelected())
				hasPrint = true;
			
			if(chkSearch.isSelected())
				hasSearch = true;
			
			result = appMan.saveChanges(mode, saveApp, hasSave, hasCancel, hasPrint, hasSearch);
			
			if(result==true){
				if(mode=='I')				
					getRoot(e).setStatusMessage("Se ha agregado una nueva aplicacion satisfactoriamente", 'S');
				if(mode=='U')
					getRoot(e).setStatusMessage("Cambios grabados satisfactoriamente", 'S');
				    getRoot(e).setApps();
			} else {
				getRoot(e).setStatusMessage("Ha ocurrido un error", 'E');			
			}
		
		} catch(Exception ex){
			
			ex.printStackTrace();
			getRoot(e).showDump(ex);
			
		}
		
	}
	
	@Override
	public void actionCancel(ActionEvent e){
		
	}
	
	@Override
	public void actionSearch(ActionEvent e){
		Dialog<Pair<String,String>> dialog = new Dialog<>();
		dialog.setTitle("Buscar Aplicación...");
		dialog.setHeaderText("Ingresa los parametros de b�squeda");
		
		dialog.setGraphic(new ImageView(getClass().getResource("/com/dotcook/resources/icons/search_32x32.png").toString()));
		
		ButtonType searchButtonType = new ButtonType("Buscar", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(searchButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20,150,10,10));
		
		TextField appName = new TextField();
		appName.setPromptText("Nombre de la aplicaci�n");
		
		TextField description = new TextField();
		description.setPromptText("Descripci�n");
		
		grid.add(new Label("Nombre:"), 0, 0);
		grid.add(appName, 1, 0);
		grid.add(new Label("Descripci�n:"), 0, 1);
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
				getRoot(e).setStatusMessage("B�squeda exitosa", 'S');				
			} else{
				getRoot(e).setStatusMessage("No se encontr� ninguna aplicaci�n con los par�metros ingresados", 'E');
			}
		});
		
	}
	
	public void fillScreenAppData(Application app){
		
		inputIdApplication.setText(String.valueOf(app.getIdApplication()));
		inputNameApplication.setText(app.getNameApplication());
		inputDescription.setText(app.getDescription());
		inputSource.setText(app.getSource());
		inputController.setText(app.getController());
		inputPosition.setText(app.getPosApp()+"");
		choiceCategory.setValue(app.getDescriptionCategory());
		
		fillCheckBox(app.getIdApplication());
		
		setDisabledForm(false);
		setEditableForm(false);
		
	}
	
	public void fillCheckBox(int idApp){
		AppsManager appMan = new AppsManager();
		ObservableList<ToolbarApplication> toolbarApps = appMan.getToolbarApplication(idApp);
		setToolbarApps(toolbarApps);
		
		chkSave.setSelected(false);
		chkCancel.setSelected(false);
		chkPrint.setSelected(false);
		chkSearch.setSelected(false);
		
		for(ToolbarApplication tb : toolbarApps){

			switch(tb.getIdButton()){
			
				case "SAVE":
					if(tb.isEnabled()==true)
						chkSave.setSelected(true);
					break;
				
				case "CANCEL":
					if(tb.isEnabled()==true)
						chkCancel.setSelected(true);
					break;
					
				case "PRINT":
					if(tb.isEnabled()==true)
						chkPrint.setSelected(true);
					break;
				
				case "SEARCH":
					if(tb.isEnabled()==true)
						chkSearch.setSelected(true);
					break;
			
			}
			
		};
	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void fillViewApplications(){
		
		TableView<Application> viewApplications = getViewApplications();
		AppsManager appMan = new AppsManager();
		
		try{
			
			ObservableList<Application> allApps = appMan.getAllApplications();
			
			viewApplications.setEditable(false);
			
			TableColumn colIdApplication = new TableColumn<Application, String>("ID App");
			colIdApplication.setMinWidth(50);
			colIdApplication.setCellValueFactory(
	                new PropertyValueFactory<Application, String>("idApplication"));
			
			TableColumn colNameApplication = new TableColumn<Application, String>("Nombre Aplicacion");
			colNameApplication.setMinWidth(150);
			colNameApplication.setCellValueFactory(
	                new PropertyValueFactory<Application, String>("nameApplication"));
			
			TableColumn colDescApplication = new TableColumn<Application, String>("Descripcion Aplicacion");
			colDescApplication.setMinWidth(200);
			colDescApplication.setCellValueFactory(
	                new PropertyValueFactory<Application, String>("description"));
			
			TableColumn colCategory = new TableColumn<Application, String>("Categoria");
			colCategory.setMinWidth(200);
			colCategory.setCellValueFactory(
	                new PropertyValueFactory<Application, String>("descriptionCategory"));
			
			TableColumn colPosApp = new TableColumn<Application, String>("Posicion");
			colPosApp.setMinWidth(50);
			colPosApp.setCellValueFactory(
	                new PropertyValueFactory<Application, String>("posApp"));
			
			TableColumn colSource = new TableColumn<Application, String>("Vista");
			colSource.setMinWidth(400);
			colSource.setCellValueFactory(
	                new PropertyValueFactory<Application, String>("source"));
			
			TableColumn colController = new TableColumn<Application, String>("Controlador");
			colController.setMinWidth(400);
			colController.setCellValueFactory(
	                new PropertyValueFactory<Application, String>("controller"));
			
			viewApplications.setItems(allApps);
			viewApplications.getColumns().addAll(colIdApplication,colNameApplication,colDescApplication,
													colCategory,colPosApp,colSource,colController);
			
			viewApplications.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent mouseEvent){
					if(mouseEvent.getClickCount()==2){
						fillScreenAppData(viewApplications.getSelectionModel().getSelectedItem());
					}
				}
			});
			
		} catch(Exception ex){			
			ex.printStackTrace();
			launchExceptionDialog(ex);
		}		
		
		setViewApplications(viewApplications);
		
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

	public Button getRefresh() {
		return refresh;
	}

	public void setRefresh(Button refresh) {
		this.refresh = refresh;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public ObservableList<String> getChoiceItems() {
		return choiceItems;
	}

	public void setChoiceItems(ObservableList<String> choiceItems) {
		this.choiceItems = choiceItems;
	}

	public ObservableList<ToolbarApplication> getToolbarApps() {
		return toolbarApps;
	}

	public void setToolbarApps(ObservableList<ToolbarApplication> toolbarApps) {
		this.toolbarApps = toolbarApps;
	}

	public ObservableList<Category> getObsCategories() {
		return obsCategories;
	}

	public void setObsCategories(ObservableList<Category> obsCategories) {
		this.obsCategories = obsCategories;
	}
	
	public TableView<Application> getViewApplications(){
		return viewApplications;
	}
	
	public void setViewApplications(TableView<Application> viewApplications){
		this.viewApplications = viewApplications;
	}
	
}
