package com.dotcook.connection;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.util.Date;

import com.dotcook.main.Main;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
	
	protected java.sql.Connection conn;
	private ConnectionParameters connParams;
	
	private static String database;
	private static String user;
	private static String password;
	private static String server;
	private static String port;
	private static boolean status;
	
	private String message;
	
	public Connection(){
		
	}
	
	public void openConnection(){
		
		setConnParams();
		
		setDatabase(getConnParams().getDatabase());
		setUser(getConnParams().getUsername());
		setPassword(getConnParams().getPassword());
		setPort(getConnParams().getPort());
		setServer(getConnParams().getServer());		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(getServer(),getUser(),getPassword());
		}
		catch(Exception e) {
			System.out.println(e.getClass() + ": " + e.getMessage());
		}
		
	}
	
	public void closeConnection() {
		if(getConn() != null) {
			try {
				getConn().close();
			}
			catch(Exception e) {				
				System.out.println(e.getClass() + ": " + e.getMessage());
			}
		}
	}
	
	public ResultSet executeSelect(String sql) {
		
		ResultSet rs = null;
		
		try{			
			openConnection();		
			Statement stmt = getConn().createStatement();		
			rs = stmt.executeQuery(sql);
						
		}		
		catch(Exception e) {			
			System.out.println(e.getClass().getName() + ": " + e.getMessage() );			
		}
		
		return rs;
		
	}
	
	protected boolean executeQuery(String sql){
		
		Statement stmt = null;
		
		try {			
			openConnection();
			stmt = getConn().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			closeConnection();
			return true;			
		}		
		catch(Exception e) {			
			System.out.println(e.getClass().getName() + ": " + e.getMessage() );
			return false;			
		}
		
	}
	
	public boolean checkConnection() {
		if(conn != null) {
			try {
				setStatus(true);
				setMessage("Conexi�n establecida");				
			}
			catch(Exception e) {
				setStatus(false);
				setMessage(e.getMessage());
				System.out.println(e.getMessage());
			}
		}
		
		return status;
	}
	
	public boolean testConnection(String database, String user, String passwd, String server, String port) {
		
		java.sql.Connection testConn = null;
		
		String srv = null;
		
		srv = "jdbc:mysql://" + server + ":" + port + "/" + database;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			testConn = DriverManager.getConnection(srv,user,passwd);
		}
		catch(Exception e) {
			message = e.getMessage();
			System.out.println(e.getMessage());
		}				
		
		if(testConn != null) {
			try {
				setStatus(true);
				setMessage("Conexión correcta");
				testConn.close();
			}
			catch(Exception e) {
				setStatus(false);
				setMessage(e.getMessage());
				System.out.println(e.getMessage());
			}
		}		
		
		return status;
	}
	
	public DatabaseMetaData getDbmd(){
		try{
			openConnection();
			DatabaseMetaData dbmd = getConn().getMetaData();
			closeConnection();
			
			return dbmd;
			
		}catch(SQLException e){
			e.printStackTrace();
			return null;			
		}
		
	}
	
	public String getServerTime(){
		
		String serverTime = "";
		
		try {			
			openConnection();
			String sql = "SELECT NOW()";
			CallableStatement stmt = getConn().prepareCall(sql);
			ResultSet rs = stmt.executeQuery();			
			Date now = null;
			
			while(rs.next()) {
				now = rs.getTime(1);				
			}		
			
			rs.close();			
			closeConnection();
			
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
			serverTime = sdf.format(now);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return serverTime;
	}
	
	public String getServerOS(){
		
		String serverOS = "";
		
		try {			
			openConnection();
			String sql = "SHOW VARIABLES WHERE VARIABLE_NAME IN ('VERSION_COMPILE_OS','VERSION_COMPILE_MACHINE');";
			CallableStatement stmt = getConn().prepareCall(sql);
			ResultSet rs = stmt.executeQuery();			
			
			String srvOS = "";
			String srvOSVersion = "";
			
			while(rs.next()) {
				
				switch(rs.getString("Variable_name")){
				case "version_compile_os":
					srvOS = rs.getString("Value");
					break;
				case "version_compile_machine":
					srvOSVersion = rs.getString("Value");
					break;
				}
								
			}
			
			serverOS = srvOS + " " + srvOSVersion;
			
			rs.close();			
			closeConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return serverOS;
	}

	public java.sql.Connection getConn() {
		return conn;
	}

	public void setConn(java.sql.Connection conn) {
		this.conn = conn;
	}

	public ConnectionParameters getConnParams() {
		return connParams;
	}

	public void setConnParams() {
		ConnectionParameters connParams = new ConnectionParameters();
		
		this.connParams = connParams;
	}

	public static String getDatabase() {
		return database;
	}

	public static void setDatabase(String database) {
		Connection.database = database;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		Connection.user = user;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Connection.password = password;
	}

	public static String getServer() {
		return server;
	}

	public static void setServer(String server) {
		server = "jdbc:mysql://" + server + ":" + getPort() + "/" + getDatabase();
		Connection.server = server;
	}

	public static boolean isStatus() {
		return status;
	}

	public static void setStatus(boolean status) {
		Connection.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		Connection.port = port;
	}
	
	public void launchExceptionDialog(Exception ex){
		Main root = new com.dotcook.main.Main();
		root.showDump(ex);
	}

}
