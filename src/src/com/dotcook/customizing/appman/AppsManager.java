package com.dotcook.customizing.appman;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import com.dotcook.application.Application;
import com.dotcook.application.Category;
import com.dotcook.connection.Connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppsManager extends Connection{
	
	private Application app;
	private ObservableList<Application> appsLists;
	
	public AppsManager(){
		
	}
	
	public Application searchApplication(String nameApplication, String description){		
				
		try{
			
			Application app = new Application();
			
			super.openConnection();
			String sql = "{CALL SEARCH_APPLICATION(?,?)}";
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setString(1, nameApplication);
			stmt.setString(2, description);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				app.setIdApplication(rs.getInt("ID_APPLICATION"));
				app.setNameApplication(rs.getString("NAME_APPLICATION"));
				app.setDescription(rs.getString("DESCRIPTION"));
				app.setSource(rs.getString("SOURCE"));
				app.setIdCategory(rs.getInt("ID_CATEGORY"));
				app.setController(rs.getString("CONTROLLER"));
				app.setPosApp(rs.getInt("POSITION"));
				
			}
			
			return app;	
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
			return null;
	
		}		
			
	}
	
	public ObservableList<Category> getCategories(){
		
		ObservableList<Category> categories = FXCollections.observableArrayList();
		
		try{			
						
			super.openConnection();
			String sql = "SELECT * FROM CATEGORY";
			CallableStatement stmt = super.conn.prepareCall(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Category cat = new Category();
				cat.setIdCategory(rs.getInt(1));
				cat.setNameCategory(rs.getString(2));
				cat.setDescriptionCategory(rs.getString(3));
				cat.setPositionCategory(rs.getInt(4));
				categories.add(cat);
				
			}		
			
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
			return null;
	
		}
		
		
		return categories;	
		
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public ObservableList<Application> getAppsLists() {
		return appsLists;
	}

	public void setAppsLists(ObservableList<Application> appsLists) {
		this.appsLists = appsLists;
	}

}
