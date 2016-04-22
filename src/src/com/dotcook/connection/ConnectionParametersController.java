package com.dotcook.connection;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ConnectionParametersController implements Initializable{
	
	@FXML ImageView logoView;
	@FXML TextField inputServer;
	@FXML TextField inputPort;
	@FXML TextField inputDatabase;
	@FXML TextField inputUsername;
	@FXML PasswordField inputPassword;
	@FXML Button actionCancel;
	@FXML Button actionAccess;
	ConnectionParameters connParams;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setImageLogo();
		
		connParams = new ConnectionParameters();
		setServer(connParams.getServer());
		setPort(connParams.getPort());
		setDatebase(connParams.getDatabase());
		setUsername(connParams.getUsername());
		setPassword(connParams.getPassword());
		
	}
	
	@FXML
	public void actionCancel(Event event){
		System.exit(0);
	}
	
	@FXML
	public void actionSave(Event event){
		
		connParams.setParamsToFile(getServer(), 
								   getPort(), 
								   getDatabase(), 
								   getUsername(), 
								   getPassword());
		
		System.exit(0);
		
	}
	
	public void setImageLogo(){		
		Image img = new Image("/com/culinet/resources/logo/logo.png");
		setLogoView(img);		
	}
	
	public void setLogoView(Image img){
		getLogoView().setImage(img);	
	}
	
	public ImageView getLogoView(){
		return this.logoView;
	}
	
	public void setServer(String server){
		this.inputServer.setText(server);
	}
	
	public String getServer(){		
		return this.inputServer.getText();
	}
	
	public void setPort(String port){
		this.inputPort.setText(port);
	}
	
	public String getPort(){
		return this.inputPort.getText();
	}	
	
	public void setDatebase(String database){
		this.inputDatabase.setText(database);
	}
	
	public String getDatabase(){
		return this.inputDatabase.getText();
	}
	
	public void setUsername(String username){
		this.inputUsername.setText(username);
	}
	
	public String getUsername(){
		return this.inputUsername.getText();
	}
	
	public void setPassword(String password){
		this.inputPassword.setText(password);
	}
	
	public String getPassword(){
		return this.inputPassword.getText();
	}

}
