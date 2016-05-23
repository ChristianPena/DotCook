package com.dotcook.main;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.dotcook.application.Application;
import com.dotcook.application.ApplicationController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainController extends ApplicationController {
	
	@FXML TreeView<String> leftMenu = null;
	@FXML WebView centralView;
	@FXML BorderPane centerPane;
	@FXML ToolBar toolBar;
	
//	ToolBar Buttons
	private Button btnChgPass;
	private Button btnMyUser;
		
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		fillToolBar();
		
		setActionToolbar();

		fillLeftMenu();
		
		getCenterPane().setLeft(getLeftMenu());

	}
	
	private void setActionToolbar() {
		
		EventHandler<ActionEvent> eHandler = new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){				
				try {
					
					Button btnPressed = (Button) e.getSource();
					
//					Change Password
					if((btnPressed.getText()).equals(getBtnChgPass().getText()))
						changePassword(e);
					
//					Show My User
					if((btnPressed.getText()).equals(getBtnMyUser().getText()))
						showMyUser(e);
					
				} catch(Exception ex){
					ex.printStackTrace();			
				}				
				
			}
		};
		
		getBtnChgPass().setOnAction(eHandler);
		getBtnMyUser().setOnAction(eHandler);
		
	}

	private void fillToolBar() {
		
		Button btnChgPass = new Button("Cambiar clave de acceso");
		btnChgPass.setGraphic(
				new ImageView(
						new Image(getClass()
								.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/change_password.png"))));		
		setBtnChgPass(btnChgPass);		
		
		Button btnMyUser = new Button("Mi usuario");
		btnMyUser.setGraphic(
				new ImageView(
						new Image(getClass()
								.getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/user_green.png"))));
		setBtnMyUser(btnMyUser);		
		
		toolBar.getItems().addAll(getBtnChgPass(),getBtnMyUser());
		
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
							getRoot(mouseEvent).setCenterPane(app);
						}
					}
				}
			}
		});
		
		setLeftMenu(leftMenu);
	}
	
	public void changePassword(ActionEvent e){
		Dialog<Pair<String,String>> dialog = new Dialog<>();
		dialog.setTitle("Cambiar clave de acceso");
		dialog.setHeaderText("Cambiar clave de acceso");
		dialog.setGraphic(new ImageView(
				new Image(getClass().getResourceAsStream(
						"/com/dotcook/resources/icons/FatCow_Icons32x32/change_password.png"))));
		ButtonType action = new ButtonType("Cambiar clave", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(action,ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20,150,10,10));
		
		PasswordField currPass = new PasswordField();
		currPass.setPromptText("Clave actual");
		
		PasswordField newPass = new PasswordField();
		newPass.setPromptText("Nueva clave");
		
		PasswordField rptPass = new PasswordField();
		rptPass.setPromptText("Repetir la nueva clave");
		
		grid.add(new Label("Clave actual"), 0, 0);
		grid.add(currPass, 1, 0);
		grid.add(new Label("Nueva clave"), 0, 1);
		grid.add(newPass, 1, 1);
		grid.add(new Label("Repetir nueva clave"), 0, 2);
		grid.add(rptPass, 1, 2);
		
		Node chgButton = dialog.getDialogPane().lookupButton(action);
		chgButton.setDisable(true);
		
		currPass.textProperty().addListener((observable,oldvalue,newValue) -> {
			chgButton.setDisable(newValue.trim().isEmpty());
		});
		
		dialog.getDialogPane().setContent(grid);
		
		((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons()
			.add(new Image(getClass().getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/change_password.png")));
		
		Platform.runLater(() -> currPass.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
			if(dialogButton == action){
				if((newPass.getText()).equals(rptPass.getText())){
					return new Pair<>(currPass.getText(),newPass.getText());					
				} else{
					getRoot(e).setStatusMessage("Nueva clave no coincide con la repetición. Intente nuevamente",'E');
				}
			}
			return null;
		});
		
		Optional<Pair<String, String>> result = dialog.showAndWait();
		
		result.ifPresent(currentPassword -> {
		    if((getUser().changePassword(getUser().getIdUser(), currPass.getText(), newPass.getText())==true)){
		    	System.out.println("Password updated successfully for the user: " + getUser().getIdUser());
		    	getRoot(e).setStatusMessage("Clave de acceso actualizada satisfactoriamente",'S');
		    } else {
		    	System.out.println("An error was ocurred, please try again later...");
		    	getRoot(e).setStatusMessage("Un error ha ocurrido, intentélo denuevo mas tarde",'E');		    	
		    }
		});		
		
	}
	
	public void showMyUser(ActionEvent e){
		getRoot(e).setStatusMessage("Show my User Pressed",'S');
	}
	
	public void setLeftMenu(TreeView<String> leftMenu){
		this.leftMenu = leftMenu;
	}
	
	public TreeView<String> getLeftMenu(){
		return leftMenu;
	}

	
	public void setCenterPane(BorderPane centerPane){
		this.centerPane = centerPane;
	}
	
	public BorderPane getCenterPane(){
		return centerPane;
	}

	public Button getBtnChgPass() {
		return btnChgPass;
	}

	public void setBtnChgPass(Button btnChgPass) {
		this.btnChgPass = btnChgPass;
	}

	public Button getBtnMyUser() {
		return btnMyUser;
	}

	public void setBtnMyUser(Button btnMyUser) {
		this.btnMyUser = btnMyUser;
	}

}
