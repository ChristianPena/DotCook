package com.dotcook.popup.system;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;

import com.dotcook.session.Session;

public class SystemInfo extends com.dotcook.connection.Connection {
	
	private String username = "";
	private String systTime = "";
	private String startSession = "";
	private String servername = "";	
	private String serverSO = "";
	private String hostname = "";
	private String hostSO = "";
	private String hostIP = "";
	private String hostMac = "";
	private String DBSyst = "";
	private String DBStringConnection = "";
	private String DBSchema = "";
	private String DBDriver = "";
	private String DBDriverVersion = "";
	
	private Session session;
	
	public SystemInfo() {
		
	}
	
	public void getSystemInfo() throws Exception{
		
		setUsername(getSession().getUsername());
		setSystTime(super.getServerTime());
		setStartSession(formatDate(getSession().getStartDate(),getSession().getStartTime()));
		setServername(getSession().getServername());
		setServerSO(super.getServerOS());
		//serverMac
		//serverIP
		setHostname(getSession().getHostname());
		setHostSO();
		setHostIP();
		setHostMac();
		setDBSyst(getDbmd().getDatabaseProductName().toString());
		setDBStringConnection(getDbmd().getURL());
		setDBSchema(super.getDatabase());
		setDBDriver(getDbmd().getDriverName());
		setDBDriverVersion(getDbmd().getDriverVersion());		
	}

	private void setHostMac() {
		
		InetAddress ip;
		
		try {
				
			ip = InetAddress.getLocalHost();
					
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				
			byte[] mac = network.getHardwareAddress();
					
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}
			
			setHostMac(sb.toString());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void setHostIP() {		
		
		InetAddress ip;
		
		try {
				
			ip = InetAddress.getLocalHost();
			
			setHostIP(ip.getHostAddress());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public String formatDate(Date dt, Date tm){
		String date = "";
		String time = "";
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
		date = sdf.format(dt);
		
		java.text.SimpleDateFormat sd2 = new java.text.SimpleDateFormat("HH:mm:ss");
		time = sd2.format(tm);
		
		return date + " - " + time;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getSystTime() {
		return systTime;
	}

	public void setSystTime(String systTime) {
		this.systTime = systTime;
	}

	public String getStartSession() {
		return startSession;
	}

	public void setStartSession(String startSession) {
		this.startSession = startSession;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getServerSO() {
		return serverSO;
	}

	public void setServerSO(String serverSO) {
		this.serverSO = serverSO;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getHostSO() {
		return hostSO;
	}

	public void setHostSO() {
		
		String hostSO = "";		
		hostSO = System.getProperty("os.name") + " " + System.getProperty("os.arch");		
		this.hostSO = hostSO;
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public String getHostMac() {
		return hostMac;
	}

	public void setHostMac(String hostMac) {
		this.hostMac = hostMac;
	}

	public String getDBSyst() {
		return DBSyst;
	}

	public void setDBSyst(String dBSyst) {
		DBSyst = dBSyst;
	}

	public String getDBStringConnection() {
		return DBStringConnection;
	}

	public void setDBStringConnection(String dBStringConnection) {
		DBStringConnection = dBStringConnection;
	}

	public String getDBSchema() {
		return DBSchema;
	}

	public void setDBSchema(String dBSchema) {
		DBSchema = dBSchema;
	}

	public String getDBDriver() {
		return DBDriver;
	}

	public void setDBDriver(String dBDriver) {
		DBDriver = dBDriver;
	}

	public String getDBDriverVersion() {
		return DBDriverVersion;
	}

	public void setDBDriverVersion(String dBDriverVersion) {
		DBDriverVersion = dBDriverVersion;
	}

}
