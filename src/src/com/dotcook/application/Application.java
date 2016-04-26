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
	
	private ObservableList<Application> apps;
	
	public Application(){
		super();
	}
	
/** Set the applications authorized for the user idUser
 * @param idUser
 * @return ObservableList<Application> List with the applications for the user
 */
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
				apps.add(app);
				
			}
			
			rs.close();
			super.closeConnection();
				
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

}
