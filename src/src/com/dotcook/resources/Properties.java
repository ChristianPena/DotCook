package com.dotcook.resources;

import java.io.InputStream;

public class Properties{
	
	private java.util.Properties prop;
	private InputStream input = null;
	
	
	public Properties(){
		
	}
	
	public String getPropertyValue(String property){
		
		String propertyValue = null;
		
		try{
			
			propertyValue = getProp().getProperty(property);
			
		} catch(Exception ex){
			
			ex.printStackTrace();
		}
		
		return propertyValue;
	}
	
	public void closeInput(){
		
		try{			
			if(getInput()!=null){
				getInput().close();
			}
			
		} catch(Exception ex){
			
			ex.printStackTrace();
			
		}
	}

	public java.util.Properties getProp() {
		return prop;
	}

	public void setProp() {
		
		java.util.Properties prop = new java.util.Properties();	
		
		try{
			
			setInput("spanish.properties");
			getProp().load(getInput());
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
		
		this.prop = prop;
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(String filename) {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(filename);
		this.input = input;
	}

}
