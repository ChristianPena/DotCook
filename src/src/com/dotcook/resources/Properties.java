package com.dotcook.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Properties{
	
	private java.util.Properties prop;
	
	public Properties(){
		
	}
		
	public java.util.Properties getProp() {
		return prop;
	}

	public void setProp() {
		
		java.util.Properties prop = new java.util.Properties();	
		
		try{
						
			File file = new File("src/com/dotcook/resources/spanish.properties");
			InputStream input = new FileInputStream(file);
						
			prop.load(input);
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
		
		this.prop = prop;
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



}
