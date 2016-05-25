/**
 * ---------------------------------------------------------------------------------- *
 * CLASS APPLICATION
 * ---------------------------------------------------------------------------------- *
 * 	
 * ---------------------------------------------------------------------------------- *
 *	@author Christian Pena
 *  @version 0.1 2016-04-26
 * ---------------------------------------------------------------------------------- */
package com.dotcook.application;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Application extends com.dotcook.connection.Connection{
	
	private final SimpleIntegerProperty idApplication;
	private final SimpleStringProperty nameApplication;
	private final SimpleStringProperty description;
	private final SimpleStringProperty source;
	private final SimpleIntegerProperty idCategory;
	private final SimpleStringProperty nameCategory;
	private final SimpleStringProperty descriptionCategory;
	private final SimpleStringProperty controller;
	private final SimpleIntegerProperty posApp;
	private final SimpleIntegerProperty posCat;
	private ObservableList<ToolbarApplication> tool;
	private ObservableList<ToolbarApplication> toolbarApplication;	
	
	private ObservableList<Application> apps;
	
	public Application(){
		super();
		idApplication       = new SimpleIntegerProperty();
		nameApplication     = new SimpleStringProperty();
		description         = new SimpleStringProperty();
		source              = new SimpleStringProperty();
		idCategory          = new SimpleIntegerProperty();
		nameCategory        = new SimpleStringProperty();
		descriptionCategory = new SimpleStringProperty();
		controller          = new SimpleStringProperty();
		posApp              = new SimpleIntegerProperty();
		posCat              = new SimpleIntegerProperty();
	}
	
	public void setApplications(String idUser){
		
		ObservableList<Application> apps = FXCollections.observableArrayList();
		
		try{
			
			super.openConnection();
			String sql = "{CALL GET_APPLICATIONS(?)}";
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setString(1, idUser);
			ResultSet rs = stmt.executeQuery();
						
			while(rs.next()){
				
				Application app = new Application();
				app.setIdApplication(rs.getInt("ID_APPLICATION"));
				app.setNameApplication(rs.getString("NAME_APPLICATION"));
				app.setDescription(rs.getString("DESCRIPTION"));
				app.setSource(rs.getString("SOURCE"));
				app.setIdCategory(rs.getInt("ID_CATEGORY"));
				app.setNameCategory(rs.getString("NAME_CATEGORY"));
				app.setDescriptionCategory(rs.getString("DESCRIPTION_CATEGORY"));
				app.setController(rs.getString("CONTROLLER"));
				app.setPosApp(rs.getInt("APP_POS"));
				app.setPosCat(rs.getInt("CAT_POS"));
				apps.add(app);
				
			}
			
			this.apps = apps;
			
			rs.close();
			super.closeConnection();
			
			ToolbarApplication tool = new ToolbarApplication();
			tool.getData(idUser);
			setToolbarApplication(tool.getToolbarApplication());
			
			for(Application a : getApplications()){
				
				ObservableList<ToolbarApplication> tools = FXCollections.observableArrayList();
				
				for(ToolbarApplication t : getToolbarApplication()){
					if(a.getIdApplication() == t.getIdApplication()){						
						tools.add(t);						
					}
				}
				
				a.setTool(tools);
			}
				
		} catch(Exception e){
			
			System.out.println(e.getClass() + ": " + e.getMessage());
			
		}		
		
	}
	
	public ObservableList<Application> getApplications(){
		return apps;
	}
	
	public void setApplication(int idApplication){
		
		for(Application app : apps){
			if(app.getIdApplication() == idApplication){
				this.setIdApplication(app.getIdApplication());
				this.setNameApplication(app.getNameApplication());
				this.setDescription(app.getDescription());
				this.setSource(app.getSource());
				this.setIdCategory(app.getIdCategory());
				this.setNameCategory(app.getNameCategory());
				this.setDescriptionCategory(app.getDescriptionCategory());
				this.setController(app.getController());
				this.setPosApp(app.getPosApp());
				this.setPosCat(app.getPosCat());
			}
		}
		
	}
	

	public int getIdApplication() {
		return idApplication.get();
	}

	public void setIdApplication(int idApplication) {
		this.idApplication.set(idApplication);
	}

	public String getNameApplication() {
		return nameApplication.get();
	}

	public void setNameApplication(String nameApplication) {
		this.nameApplication.set(nameApplication);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public String getSource() {
		return source.get();
	}

	public void setSource(String source) {
		this.source.set(source);
	}

	public int getIdCategory() {
		return idCategory.get();
	}

	public void setIdCategory(int idCategory) {
		this.idCategory.set(idCategory);
	}

	public String getNameCategory() {
		return nameCategory.get();
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory.set(nameCategory);
	}

	public String getDescriptionCategory() {
		return descriptionCategory.get();
	}

	public void setDescriptionCategory(String descriptionCategory) {
		this.descriptionCategory.set(descriptionCategory);
	}

	public String getController() {
		return controller.get();
	}

	public void setController(String controller) {
		this.controller .set(controller);
	}

	public ObservableList<ToolbarApplication> getToolbarApplication() {
		return toolbarApplication;
	}

	public void setToolbarApplication(ObservableList<ToolbarApplication> toolbarApplication) {
		this.toolbarApplication = toolbarApplication;
	}

	public ObservableList<ToolbarApplication> getTool() {
		return tool;
	}

	public void setTool(ObservableList<ToolbarApplication> tool) {
		this.tool = tool;
	}

	public int getPosApp() {
		return posApp.get();
	}

	public void setPosApp(int posApp) {
		this.posApp.set(posApp);
	}

	public int getPosCat() {
		return posCat.get();
	}

	public void setPosCat(int posCat) {
		this.posCat.set(posCat);
	}

}
