package com.dotcook.customizing.appman;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import com.dotcook.application.Application;
import com.dotcook.application.Category;
import com.dotcook.application.ToolbarApplication;
import com.dotcook.connection.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppsManager extends Connection{
	
	private Application app;
	private ObservableList<Application> appsLists;
	private ObservableList<ToolbarApplication> toolbarApps;
	
	public AppsManager(){
		
	}
	
	public Application searchApplication(String nameApplication, String description){		
				
		try{
			
			Application app = new Application();
			
			super.openConnection();
			String sql = "{CALL SEARCH_APPLICATION(?,?)}";
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setString(1, nameApplication);
			stmt.setString(2, description);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				app.setIdApplication(rs.getInt("ID_APPLICATION"));
				app.setNameApplication(rs.getString("NAME_APPLICATION"));
				app.setDescription(rs.getString("DESC_APP"));
				app.setSource(rs.getString("SOURCE"));
				app.setIdCategory(rs.getInt("ID_CATEGORY"));
				app.setController(rs.getString("CONTROLLER"));
				app.setPosApp(rs.getInt("POSITION"));
				app.setDescriptionCategory(rs.getString("DESC_CAT"));
				
			}
			
			rs.close();
			super.closeConnection();
			
			return app;	
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			launchExceptionDialog(ex);
			return null;
	
		}		
			
	}
	
	public ObservableList<Category> getCategories(){
		
		ObservableList<Category> categories = FXCollections.observableArrayList();
		
		try{			
						
			super.openConnection();
			String sql = "SELECT * FROM CATEGORY";
			CallableStatement stmt = super.conn.prepareCall(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Category cat = new Category();
				cat.setIdCategory(rs.getInt(1));
				cat.setNameCategory(rs.getString(2));
				cat.setDescriptionCategory(rs.getString(3));
				cat.setPositionCategory(rs.getInt(4));
				categories.add(cat);
				
			}
			
			rs.close();
			super.closeConnection();
			
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			launchExceptionDialog(ex);	
			return null;
	
		}
		
		
		return categories;	
		
	}
	
	public ObservableList<ToolbarApplication> getToolbarApplication(int idApp){
		
		try{
			
			ObservableList<ToolbarApplication> toolbarApps = FXCollections.observableArrayList();
			
			super.openConnection();
			String sql = "SELECT * FROM TOOLBAR_APPLICATION WHERE ID_APPLICATION = ?";
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setInt(1, idApp);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				ToolbarApplication tb = new ToolbarApplication();
				tb.setIdApplication(rs.getInt("ID_APPLICATION"));
				tb.setIdButton(rs.getString("ID_BUTTON"));
				tb.setEnabled(rs.getBoolean("IS_ENABLED"));
				toolbarApps.add(tb);
				
			}
			
			super.closeConnection();
			setToolbarApps(toolbarApps);
			return getToolbarApps();	
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			launchExceptionDialog(ex);
			return null;
	
		}	
		
	}
	
	public boolean saveChanges(char mode, Application app, boolean hasSave, boolean hasCancel, boolean hasPrint, boolean hasSearch){
		
		boolean result = false;
		int intResult = 0;
		
		try{
			
			super.openConnection();
			String sql = "{CALL ADD_APPLICATION(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement stmt = super.conn.prepareCall(sql);
			
			stmt.setString(1,String.valueOf(mode));
			stmt.setInt(2, app.getIdApplication());
			stmt.setString(3, app.getNameApplication());
			stmt.setString(4, app.getDescription());
			stmt.setString(5, app.getSource());
			stmt.setInt(6, app.getIdCategory());
			stmt.setString(7, app.getController());
			stmt.setInt(8, app.getPosApp());
			stmt.setBoolean(9, hasSave);
			stmt.setBoolean(10, hasCancel);
			stmt.setBoolean(11, hasPrint);
			stmt.setBoolean(12, hasSearch);
			stmt.setInt(13, intResult);
			
			stmt.execute();			
						
			intResult = stmt.getInt("P_RESULT");			
			
			if(intResult > 0)
				result = true;
			
			super.closeConnection();
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			launchExceptionDialog(ex);
	
		}	
		
		return result;		
	}
	
	public boolean deleteApplication(int idApp){
		
		try{
			
			super.openConnection();
			String sql = "DELETE FROM APPLICATION WHERE ID_APPLICATION = ?";
			CallableStatement stmt = super.conn.prepareCall(sql);
			stmt.setInt(1, idApp);
			stmt.execute();
			
			sql = "DELETE FROM TOOLBAR_APPLICATION WHERE ID_APPLICATION = ?";
			CallableStatement stmt2 = super.conn.prepareCall(sql);
			stmt2.setInt(1, idApp);
			stmt2.execute();		
						
			super.closeConnection();
			
			return true;
		
		} catch(Exception ex){
			ex.printStackTrace();
			launchExceptionDialog(ex);
			return false;
		}
		
	}
	
	public ObservableList<Application> getAllApplications(){
		
		ObservableList<Application> allApps = FXCollections.observableArrayList();
		
		try{			
			
			super.openConnection();
			String sql = "SELECT DISTINCT A.ID_APPLICATION, A.NAME_APPLICATION, A.DESCRIPTION AS DESC_APP, A.SOURCE, B.ID_CATEGORY, "
						+ "B.NAME_CATEGORY, B.DESCRIPTION AS DESC_CAT, A.CONTROLLER, A.POSITION AS APP_POS, B.POSITION AS CAT_POS "
					   	+ "FROM APPLICATION AS A INNER JOIN CATEGORY AS B "
					   	+ "ON A.ID_CATEGORY = B.ID_CATEGORY "
					   	+ "ORDER BY B.POSITION, A.POSITION;";
			CallableStatement stmt = super.conn.prepareCall(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Application app = new Application();
				
				app.setIdApplication(rs.getInt(1));
				app.setNameApplication(rs.getString(2));
				app.setDescription(rs.getString(3));
				app.setSource(rs.getString(4));
				app.setIdCategory(rs.getInt(5));
				app.setNameCategory(rs.getString(6));
				app.setDescriptionCategory(rs.getString(7));
				app.setController(rs.getString(8));
				app.setPosApp(rs.getInt(9));
				app.setPosCat(rs.getInt(10));
				
				allApps.add(app);
				
			}
			
			rs.close();
			super.closeConnection();	
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			launchExceptionDialog(ex);	
		}

		return allApps;
		
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public ObservableList<Application> getAppsLists() {
		return appsLists;
	}

	public void setAppsLists(ObservableList<Application> appsLists) {
		this.appsLists = appsLists;
	}

	public ObservableList<ToolbarApplication> getToolbarApps() {
		return toolbarApps;
	}

	public void setToolbarApps(ObservableList<ToolbarApplication> toolbarApps) {
		this.toolbarApps = toolbarApps;
	}

}
