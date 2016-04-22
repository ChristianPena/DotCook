package com.dotcook.login;

import java.sql.ResultSet;

import com.dotcook.connection.Connection;

public class Login extends Connection{
	
	
	public Login(){
		
	}
	
	public boolean login(String username, String password){
		
		boolean isTrue = false;
		
		super.openConnection();		
		ResultSet rs;
		try{
			String sql = "SELECT A.ID_USER, B.ID_PASSWORD, A.IS_ENABLED "+
						   "FROM USER AS A INNER JOIN PASSWORD AS B "+
							 "ON A.ID_USER    = B.ID_USER "+
						  "WHERE A.ID_USER    = '" + username + "' "+
							"AND B.PASSWORD   = PASSWORD('" + password + "') "+
							"AND A.IS_ENABLED = TRUE; ";
			rs = super.executeSelect(sql);
			while(rs.next()){
				isTrue = true;
			}
			rs.close();		
			
		}catch(Exception e){
			System.out.println(e.getClass() + ": "+ e.getMessage());
		}
		
		super.closeConnection();
		
		return isTrue;		
		
	}

}
