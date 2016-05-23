package com.dotcook.user;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dotcook.connection.Connection;

public class User extends Connection{
	
	private String idUser;
	private boolean isEnabled;
	
	public User(){
		
	}
	
	public void getUser(String idUser){
		
		try {
			
			super.openConnection();
			String sql = "{CALL GET_USER(?)}";		
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setString(1, idUser);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				setIdUser(rs.getString("ID_USER"));
				setEnabled(rs.getBoolean("IS_ENABLED"));
			}
			
			rs.close();
			super.closeConnection();
			
		} catch (SQLException e) {
			
			System.out.println(e.getClass() + ": " + e.getMessage());			
			e.printStackTrace();
		}
		
	}
	
	public boolean changePassword(String idUser, String currPass, String newPass){
		
		boolean result = false;
		
		try{
			
			super.openConnection();
			String sql = "{CALL CHANGE_PASSWORD(?,?,?)}";		
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setString(1, idUser);
			stmt.setString(2, currPass);
			stmt.setString(3, newPass);
			
			result = stmt.execute();
			
		} catch(Exception ex){			
			ex.printStackTrace();
			result = false;
		}

		return result;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
