package com.dotcook.session;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.dotcook.connection.Connection;

public class Session extends Connection{
	
	private String idSession;
	private String username;
	private String hostname;
	private String servername;
	private Date startDate;
	private Date startTime;
	private Date finishDate;
	private Date finishTime;
	
	public Session(){
		
	}
	
	public void createSession(String idUser){
		
		try {
			setHostname(InetAddress.getLocalHost().getHostName());			
			super.openConnection();
			String sql = "{CALL CREATE_SESSION(?,?)}";
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setString(1, idUser);
			stmt.setString(2, getHostname());
			ResultSet rs = stmt.executeQuery();
						
			while(rs.next()){
				
				setIdSession(rs.getString("ID_SESSION"));
				setUsername(rs.getString("ID_USER"));
				setHostname(rs.getString("HOSTNAME"));
				setServername(rs.getString("SERVERNAME"));
				setStartDate(rs.getDate("START_DATE"));
				setStartTime(rs.getTime("START_DATE"));
			
			}
			
			System.out.println("Session Created");
			
			
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}
	
	public void closeSession(){
		
		try {						
			
			super.openConnection();
			String sql = "{CALL CLOSE_SESSION(?,?)}";
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setString(1, getIdSession());
			stmt.setString(2, getUsername());
			stmt.execute();
			
			System.out.println("Session Closed");
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

}
