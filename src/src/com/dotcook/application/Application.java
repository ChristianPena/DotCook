package com.dotcook.application;

import java.sql.CallableStatement;
import java.sql.ResultSet;

public class Application extends com.dotcook.connection.Connection{
	
	private int idApplication;
	private String nameApplication;
	private String description;
	private String source;
	
	public Application(){
		super();
	}
	
	
	public Application[] getApplication(String idUser){
		
		Application apps[] = null;
		
		apps = new Application[10];		
		
		try{
			
			super.openConnection();
			String sql = "{CALL GET_APPLICATIONS(?)}";
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setString(1, idUser);
			ResultSet rs = stmt.executeQuery();
			
			int i = 0;
			
			while(rs.next()){
				
				apps[i] = new Application();
				apps[i].setIdApplication(rs.getInt("ID_APPLICATION"));
				apps[i].setNameApplication(rs.getString("NAME_APPLICATION"));
				apps[i].setDescription(rs.getString("DESCRIPTION"));
				apps[i].setSource(rs.getString("SOURCE"));				
				i++;
				
			}
			
			rs.close();
			super.closeConnection();
				
		} catch(Exception e){
			
			System.out.println(e.getClass() + ": " + e.getMessage());
			
		}		
		
		return apps;
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
