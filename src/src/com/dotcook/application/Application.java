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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Application extends com.dotcook.connection.Connection{
	
	private int idApplication;
	private String nameApplication;
	private String description;
	private String source;
	private int idCategory;
	private String nameCategory;
	private String descriptionCategory;
	private String controller;
	private int posApp;
	private int posCat;
	private ObservableList<ToolbarApplication> tool;
	private ObservableList<ToolbarApplication> toolbarApplication;	
	
	private ObservableList<Application> apps;
	
	public Application(){
		super();
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
		return idApplication;
	}

	public void setIdApplication(int idApplication) {
		this.idApplication = idApplication;
	}

	public String getNameApplication() {
		return nameApplication;
	}

	public void setNameApplication(String nameApplication) {
		this.nameApplication = nameApplication;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getNameCategory() {
		return nameCategory;
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}

	public String getDescriptionCategory() {
		return descriptionCategory;
	}

	public void setDescriptionCategory(String descriptionCategory) {
		this.descriptionCategory = descriptionCategory;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
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
		return posApp;
	}

	public void setPosApp(int posApp) {
		this.posApp = posApp;
	}

	public int getPosCat() {
		return posCat;
	}

	public void setPosCat(int posCat) {
		this.posCat = posCat;
	}

}
