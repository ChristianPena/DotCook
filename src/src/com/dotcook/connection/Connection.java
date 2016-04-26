package com.dotcook.connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
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
				setMessage("Conexión establecida");				
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
				setMessage("ConexiÃ³n correcta");
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

}
