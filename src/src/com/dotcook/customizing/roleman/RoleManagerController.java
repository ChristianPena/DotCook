package com.dotcook.customizing.roleman;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.application.ApplicationController;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;

public class RoleManagerController extends ApplicationController{
	
	@FXML TreeTableView<RoleManager> treeRoles;
	
	private RoleManager roleMan;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		setRoleMan(new RoleManager());
		getRoleMan().getRoles();
		fillTreeRoles();
		
	}
	
	@SuppressWarnings("unchecked")
	public void fillTreeRoles(){
		
		int idRole = 0;
		int idPrivilege = 0;
		int idApp = 0;
		
//		Root
		final TreeItem<RoleManager> root = new TreeItem<>(new RoleManager());
		root.setExpanded(true);
		
//		Recorrer todos los registros en busca de roles
		for(RoleManager role : getRoleMan().getObsRoleMan()){									
//			Si el id es mayor al que esta en memoria
			if(role.getIdRole() > idRole){
				idRole = role.getIdRole();
				TreeItem<RoleManager> itemRole = new TreeItem<>(role);
				itemRole.setExpanded(true);
				idPrivilege = 0;
//				Recorrer los registros en busca de los privilegios del rol
				for(RoleManager privilege : getRoleMan().getObsRoleMan()){					
					if((privilege.getIdPrivilege() > idPrivilege ) && (privilege.getIdRole() == idRole)){
						idPrivilege = privilege.getIdPrivilege();
						TreeItem<RoleManager> itemPrivilege = new TreeItem<>(privilege);
						itemPrivilege.setExpanded(true);
						idApp = 0;
//						Recorrer los registros en busca de las aplicationes de los privilegios
						for(RoleManager app : getRoleMan().getObsRoleMan()){
							if((app.getIdApplication() > idApp) && (app.getIdRole() == idRole) && (app.getIdPrivilege() == idPrivilege)){
								idApp = app.getIdApplication();
								TreeItem<RoleManager> itemApp = new TreeItem<>(app);
								itemPrivilege.getChildren().add(itemApp);
							}
						}
						itemRole.getChildren().add(itemPrivilege);
					}
				}
				root.getChildren().add(itemRole);
			}			
		}
		
		TreeTableColumn<RoleManager, String> colName = new TreeTableColumn<>("Nombre tecnico");
		colName.setPrefWidth(150);
		colName.setCellValueFactory((CellDataFeatures<RoleManager,String> p) ->
			new ReadOnlyStringWrapper(p.getValue().getValue().getNameRole()));
		
		
		treeRoles.getColumns().addAll(colName);
		treeRoles.setShowRoot(true);
		
	}

	public RoleManager getRoleMan() {
		return roleMan;
	}

	public void setRoleMan(RoleManager roleMan) {
		this.roleMan = roleMan;
	}

}
