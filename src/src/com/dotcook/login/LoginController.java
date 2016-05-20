package com.dotcook.login;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.main.Main;
import com.dotcook.resources.Properties;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;

public class LoginController implements Initializable{
	
	@FXML Label loginTitle;
	@FXML Label loginUsername;
	@FXML Label loginPassword;
	
	@FXML ImageView logoView;
	@FXML TextField inputUsername;
	@FXML PasswordField inputPassword;
	@FXML Button actionCancel;
	@FXML Button actionAccess;	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setImageLogo();
		
		Properties prop = new Properties();
		prop.setProp();
		
		this.loginTitle.setText(prop.getPropertyValue("login.title"));
		this.loginUsername.setText(prop.getPropertyValue("login.username"));
		this.loginPassword.setText(prop.getPropertyValue("login.password"));
		this.actionCancel.setText(prop.getPropertyValue("login.cancel"));
		this.actionAccess.setText(prop.getPropertyValue("login.access"));
		
		this.inputUsername.setText("CH_PENA");
		this.inputPassword.setText("123");
		
	}
	
	@FXML
	public void actionCancel(Event event){
		System.exit(0);
	}
	
	@FXML
	public void actionAccess(Event event){
		
		Login login = new Login();
		if(login.login(this.inputUsername.getText(), 
				       this.inputPassword.getText())==true){
						
			Stage currentStage = (Stage) actionAccess.getScene().getWindow();
						
			Main main = new Main();
			main.init(this.inputUsername.getText());
						
			Scene scene = new Scene(main);
			
			Stage stage = new Stage();
			stage.setMaximized(true);
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
			currentStage.hide();
			
			final BooleanProperty ignoreCloseRequest = new SimpleBooleanProperty(false);
		    stage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		      @Override 
		      public void handle(final KeyEvent event) {
		        if(event.getCode()==KeyCode.F4 && event.isAltDown()) {
		          event.consume();
		          ignoreCloseRequest.set(true);		          
		        }
		      }
		    });
		    
		    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      @Override 
		      public void handle(final WindowEvent event) {
		        if (ignoreCloseRequest.get()) {
		          event.consume();
		          ignoreCloseRequest.set(false);
		          main.exit();
		        }
		      }
		    });
			
			
		}else{
			
			System.out.println("Usuario y/o clave incorrectos");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No se puede iniciar sesi�n");
			alert.setContentText("Usuario y/o clave incorrectos");
			alert.showAndWait();
			
		}
		
	}
	
	public void setImageLogo(){		
		Image img = new Image("/com/dotcook/resources/logo/logo.png");
		setLogoView(img);		
	}
	
	public void setLogoView(Image img){
		getLogoView().setImage(img);	
	}
	
	public ImageView getLogoView(){
		return this.logoView;
	}

}
