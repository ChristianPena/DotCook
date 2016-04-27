package com.dotcook.main;

import java.io.IOException;

import com.dotcook.connection.Connection;
import com.dotcook.resources.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DotCook extends Application{
	
	@Override
	public void start(Stage stage) throws IOException{
		
		Properties prop = new Properties();
		Connection conn = new Connection();
		conn.openConnection();
		prop.setProp();
		
		if(conn.checkConnection() == true ){
			
			Parent root = FXMLLoader.load(getClass().getResource("/com/dotcook/login/Login.fxml"));
			
			Scene scene = new Scene(root);
			
			stage.setTitle(prop.getPropertyValue("main.title"));
			prop.closeInput();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);   
			stage.setMinHeight(320);
			stage.setMinWidth(600);
			stage.setMaxHeight(320);
			stage.setMaxWidth(600);
			stage.setResizable(false);
			stage.show();
			
		} else{
			
			System.out.println("Connection failed!");
			
			Parent root = FXMLLoader.load(getClass().getResource("/com/dotcook/connection/ConnectionParameters.fxml"));
			
			Scene scene = new Scene(root);
			
			stage.setTitle("DotCook: Sistema de Gestion de Restaurante");
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);   
			stage.setMinHeight(400);
			stage.setMinWidth(650);
			stage.setMaxHeight(400);
			stage.setMaxWidth(650);
			stage.setResizable(false);
			stage.show();
			
		}
		
	}
	
	public static void main(String[]args){
		launch(args);
	}

}
