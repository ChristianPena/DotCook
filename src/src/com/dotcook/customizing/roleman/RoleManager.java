package com.dotcook.customizing.roleman;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import com.dotcook.connection.Connection;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoleManager extends Connection{

	private SimpleIntegerProperty idRole;
	private SimpleStringProperty nameRole;
	private SimpleStringProperty descriptionRole;
	
	private SimpleIntegerProperty idPrivilege;
	private SimpleStringProperty namePrivilege;
	private SimpleStringProperty descriptionPrivilege;
	
	private SimpleIntegerProperty idApplication;
	private SimpleStringProperty nameApplication;
	private SimpleStringProperty descriptionApplication;
	
	private SimpleIntegerProperty idRow;
	private SimpleStringProperty nameRow;
	private SimpleStringProperty descriptionRow;
	private SimpleStringProperty typeRow;
	
	private ObservableList<RoleManager> obsRoleMan;
	
	public RoleManager(){
		
		idRole = new SimpleIntegerProperty();
		nameRole = new SimpleStringProperty();
		descriptionRole = new SimpleStringProperty();
		
		idPrivilege = new SimpleIntegerProperty();
		namePrivilege = new SimpleStringProperty();
		descriptionPrivilege = new SimpleStringProperty();
		
		idApplication = new SimpleIntegerProperty();
		nameApplication = new SimpleStringProperty();
		descriptionApplication = new SimpleStringProperty();
		
		idRow = new SimpleIntegerProperty();
		nameRow = new SimpleStringProperty();
		descriptionRow = new SimpleStringProperty();
		typeRow = new SimpleStringProperty();
		
	}

	public void getRoles(){
		
		try{
			
			ObservableList<RoleManager> obsRoleMan = FXCollections.observableArrayList();
			
			super.openConnection();
			String sql = "{CALL GET_ALL_ROLES()}";
			CallableStatement stmt = super.conn.prepareCall(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				RoleManager roleMan = new RoleManager();
				roleMan.setIdRole(rs.getInt(1));
				roleMan.setNameRole(rs.getString(2));
				roleMan.setDescriptionRole(rs.getString(3));
				roleMan.setIdPrivilege(rs.getInt(4));
				roleMan.setNamePrivilege(rs.getString(5));
				roleMan.setDescriptionPrivilege(rs.getString(6));
				roleMan.setIdApplication(rs.getInt(7));
				roleMan.setNameApplication(rs.getString(8));
				roleMan.setDescriptionApplication(rs.getString(9));
				obsRoleMan.add(roleMan);
			}			
			rs.close();
			super.closeConnection();
			setObsRoleMan(obsRoleMan);
			
		} catch(Exception ex){
			ex.printStackTrace();
			launchExceptionDialog(ex);
		}
		
	}
	
	public ObservableList<RoleManager> getListRoles(){
		
		try{
			
			ObservableList<RoleManager> list = FXCollections.observableArrayList();
			
			int idRole = 0;
			int idPrivilege = 0;
			int idApplication = 0;
			for(RoleManager roleMan : getObsRoleMan()){
				
				if(roleMan.getIdRole() > idRole){
					idRole = roleMan.getIdRole();
					RoleManager roleRow = new RoleManager();
					roleRow.setIdRow(roleMan.getIdRole());
					roleRow.setNameRow(roleMan.getNameRole());
					roleRow.setDescriptionRow(roleMan.getDescriptionRole());
					roleRow.setTypeRow("ROLE");
					
					roleRow.setIdRole(roleMan.getIdRole());
					roleRow.setNameRole(roleMan.getNameRole());
					roleRow.setDescriptionRole(roleMan.getDescriptionRole());
					roleRow.setIdPrivilege(roleMan.getIdPrivilege());
					roleRow.setNamePrivilege(roleMan.getNamePrivilege());
					roleRow.setDescriptionPrivilege(roleMan.getDescriptionPrivilege());
					roleRow.setIdApplication(roleMan.getIdApplication());
					roleRow.setNameApplication(roleMan.getNameApplication());
					roleRow.setDescriptionApplication(roleMan.getDescriptionApplication());
					
					list.add(roleRow);
					idPrivilege = 0;
				}
				
				if((roleMan.getIdRole() == idRole)&&(roleMan.getIdPrivilege() > idPrivilege)){
					idPrivilege = roleMan.getIdPrivilege();
					RoleManager privilegeRow = new RoleManager();
					privilegeRow.setIdRow(roleMan.getIdPrivilege());
					privilegeRow.setNameRow(roleMan.getNamePrivilege());
					privilegeRow.setDescriptionRow(roleMan.getDescriptionPrivilege());
					privilegeRow.setTypeRow("PRIVILEGE");
					
					privilegeRow.setIdRole(roleMan.getIdRole());
					privilegeRow.setNameRole(roleMan.getNameRole());
					privilegeRow.setDescriptionRole(roleMan.getDescriptionRole());
					privilegeRow.setIdPrivilege(roleMan.getIdPrivilege());
					privilegeRow.setNamePrivilege(roleMan.getNamePrivilege());
					privilegeRow.setDescriptionPrivilege(roleMan.getDescriptionPrivilege());
					privilegeRow.setIdApplication(roleMan.getIdApplication());
					privilegeRow.setNameApplication(roleMan.getNameApplication());
					privilegeRow.setDescriptionApplication(roleMan.getDescriptionApplication());
					
					list.add(privilegeRow);
					idApplication = 0;
				}
				
				if((roleMan.getIdRole() == idRole)&&(roleMan.getIdPrivilege() == idPrivilege)&&(roleMan.getIdApplication() > idApplication)){
					idApplication = roleMan.getIdApplication();
					RoleManager applicationRow = new RoleManager();
					applicationRow.setIdRow(roleMan.getIdApplication());
					applicationRow.setNameRow(roleMan.getNameApplication());
					applicationRow.setDescriptionRow(roleMan.getDescriptionApplication());
					applicationRow.setTypeRow("APPLICATION");
					
					applicationRow.setIdRole(roleMan.getIdRole());
					applicationRow.setNameRole(roleMan.getNameRole());
					applicationRow.setDescriptionRole(roleMan.getDescriptionRole());
					applicationRow.setIdPrivilege(roleMan.getIdPrivilege());
					applicationRow.setNamePrivilege(roleMan.getNamePrivilege());
					applicationRow.setDescriptionPrivilege(roleMan.getDescriptionPrivilege());
					applicationRow.setIdApplication(roleMan.getIdApplication());
					applicationRow.setNameApplication(roleMan.getNameApplication());
					applicationRow.setDescriptionApplication(roleMan.getDescriptionApplication());
					
					list.add(applicationRow);
				}				
			}
			
			return list;
			
		} catch(Exception ex){
			ex.printStackTrace();
			launchExceptionDialog(ex);
			return null;			
		}		
		
	}

	public Integer getIdRole() {
		return idRole.get();
	}

	public void setIdRole(Integer idRole) {
		this.idRole.set(idRole);
	}

	public String getNameRole() {
		return nameRole.get();
	}

	public void setNameRole(String nameRole) {
		this.nameRole.set(nameRole);
	}

	public String getDescriptionRole() {
		return descriptionRole.get();
	}

	public void setDescriptionRole(String descriptionRole) {
		this.descriptionRole.set(descriptionRole);
	}

	public Integer getIdPrivilege() {
		return idPrivilege.get();
	}

	public void setIdPrivilege(Integer idPrivilege) {
		this.idPrivilege.set(idPrivilege);
	}

	public String getNamePrivilege() {
		return namePrivilege.get();
	}

	public void setNamePrivilege(String namePrivilege) {
		this.namePrivilege.set(namePrivilege);
	}

	public String getDescriptionPrivilege() {
		return descriptionPrivilege.get();
	}

	public void setDescriptionPrivilege(String descriptionPrivilege) {
		this.descriptionPrivilege.set(descriptionPrivilege);
	}

	public Integer getIdApplication() {
		return idApplication.get();
	}

	public void setIdApplication(Integer idApplication) {
		this.idApplication.set(idApplication);
	}

	public String getNameApplication() {
		return nameApplication.get();
	}

	public void setNameApplication(String nameApplication) {
		this.nameApplication.set(nameApplication);
	}

	public String getDescriptionApplication() {
		return descriptionApplication.get();
	}

	public void setDescriptionApplication(String descriptionApplication) {
		this.descriptionApplication.set(descriptionApplication);
	}

	public ObservableList<RoleManager> getObsRoleMan() {
		return obsRoleMan;
	}

	public void setObsRoleMan(ObservableList<RoleManager> obsRoleMan) {
		this.obsRoleMan = obsRoleMan;
	}
	
	public Integer getIdRow() {
		return idRow.get();
	}

	public void setIdRow(Integer idRow) {
		this.idRow.set(idRow);
	}

	public String getNameRow() {
		return nameRow.get();
	}

	public void setNameRow(String nameRow) {
		this.nameRow.set(nameRow);
	}

	public String getDescriptionRow() {
		return descriptionRow.get();
	}

	public void setDescriptionRow(String descriptionRow) {
		this.descriptionRow.set(descriptionRow);
	}
	
	public String getTypeRow() {
		return typeRow.get();
	}

	public void setTypeRow(String typeRow) {
		this.typeRow.set(typeRow);
	}
	
	

}
