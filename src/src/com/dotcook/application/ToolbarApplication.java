package com.dotcook.application;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ToolbarApplication extends com.dotcook.connection.Connection{
	
	private int idApplication;
	private String idButton;
	private boolean isEnabled;
	private ObservableList<ToolbarApplication> toolbarApplication;
	
	public ToolbarApplication(){
		
	}
	
	public void getData(String idUser){
		
		ObservableList<ToolbarApplication> toolbarApplication = FXCollections.observableArrayList();
		
		try{
			
			super.openConnection();
			String sql = "{CALL GET_TOOLBAR_APPLICATIONS(?)}";
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setString(1, idUser);
			ResultSet rs = stmt.executeQuery();
						
			while(rs.next()){
				
				ToolbarApplication tool = new ToolbarApplication();
				tool.setIdApplication(rs.getInt("ID_APPLICATION"));
				tool.setIdButton(rs.getString("ID_BUTTON"));
				tool.setEnabled(rs.getBoolean("IS_ENABLED"));
				toolbarApplication.add(tool);
				
			}
			
			setToolbarApplication(toolbarApplication);
			
			rs.close();
			super.closeConnection();
				
		} catch(Exception e){
			
			System.out.println(e.getClass() + ": " + e.getMessage());
			
		}		
		
	}

	public int getIdApplication() {
		return idApplication;
	}

	public void setIdApplication(int idApplication) {
		this.idApplication = idApplication;
	}

	public String getIdButton() {
		return idButton;
	}

	public void setIdButton(String idButton) {
		this.idButton = idButton;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public ObservableList<ToolbarApplication> getToolbarApplication() {
		return toolbarApplication;
	}

	public void setToolbarApplication(ObservableList<ToolbarApplication> toolbarApplication) {
		this.toolbarApplication = toolbarApplication;
	}
	

}
