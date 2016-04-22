package com.dotcook.connection;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConnectionParameters {
	
    public static final String xmlFilePath = ConnectionParameters.class.getResource("ConnectionParameters.xml").getPath();
	
	private String database;
	private String username;
	private String password;
	private String server;
	private String port;
	
	public ConnectionParameters() {

		getParamsFromFile();
	}
	
	public void getParamsFromFile(){
		
		try{
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFilePath);			
			Node parameters = document.getElementsByTagName("parameters").item(0);			
			NodeList nodes =parameters.getChildNodes();
			
			for (int i = 0; i < nodes.getLength(); i++) {
				
				Node element = nodes.item(i);
				
				switch(element.getNodeName()){
				
				case "server":
					setServer(element.getTextContent());
					break;
				
				case "port":
					setPort(element.getTextContent());
					break;
				
				case "database":
					setDatabase(element.getTextContent());
					break;
				
				case "username":
					setUsername(element.getTextContent());
					break;
				
				case "password":
					setPassword(element.getTextContent());
					break;			
				
				}
				
			}			
			
		}catch(Exception e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage() );			
		}
		
	}
	
	public void setParamsToFile(String server, String port, String database, String username, String password){
		
		try{
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFilePath);			
			Node parameters = document.getElementsByTagName("parameters").item(0);			
			NodeList nodes =parameters.getChildNodes();
			
			for (int i = 0; i < nodes.getLength(); i++) {
				
				Node element = nodes.item(i);
				
				switch(element.getNodeName()){
				
				case "server":
					element.setTextContent(server);
					break;
				
				case "port":
					element.setTextContent(port);
					break;
				
				case "database":
					element.setTextContent(database);
					break;
				
				case "username":
					element.setTextContent(username);
					break;
				
				case "password":
					element.setTextContent(password);
					break;			
				
				}				 
				
			}	
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			 
			Transformer transformer = transformerFactory.newTransformer();
			 
			DOMSource domSource = new DOMSource(document);
			 
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));
			 
			transformer.transform(domSource, streamResult);
			
		}catch(Exception e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage() );			
		}
		
	}
	
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
}
