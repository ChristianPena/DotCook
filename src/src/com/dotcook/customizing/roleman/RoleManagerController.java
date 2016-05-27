package com.dotcook.customizing.roleman;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.application.ApplicationController;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public class RoleManagerController extends ApplicationController{
	
	@FXML ToolBar toolbar;
	@FXML TreeTableView<RoleManager> treeRoles;
	
	private Button addRole;
	private Button editRole;
	private Button deleteRole;
	private Button privilegeMan;
	
	private RoleManager roleMan;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		setRoleMan(new RoleManager());
		getRoleMan().getRoles();
		fillToolBar();
		fillTreeRoles();		
	}
	
	public void fillToolBar(){	
		
		String pathIcons = "/com/dotcook/resources/icons/16x16/";
		Double withButtons = 80.0;
		
		setAddRole(new Button("Nuevo"));
		setEditRole(new Button("Editar"));
		setDeleteRole(new Button("Eliminar"));
		setPrivilegeMan(new Button("Gestion de Privilegios"));
		
		getAddRole().setGraphic(
				new ImageView(
						new Image(getClass().getResourceAsStream(pathIcons + "page_white.png"))));
		getAddRole().setMinWidth(withButtons);
		
		getEditRole().setGraphic(
				new ImageView(
						new Image(getClass().getResourceAsStream(pathIcons + "pencil.png"))));
		getEditRole().setMinWidth(withButtons);
		
		getDeleteRole().setGraphic(
				new ImageView(
						new Image(getClass().getResourceAsStream(pathIcons + "empty_trash.png"))));
		getDeleteRole().setMinWidth(withButtons);
		
		getPrivilegeMan().setGraphic(
				new ImageView(
						new Image(getClass().getResourceAsStream("/com/dotcook/resources/icons/FatCow_Icons16x16/shared_private.png"))));
				
		toolbar.getItems().addAll(getAddRole(),getEditRole(),getDeleteRole(),new Separator(),getPrivilegeMan());
		
	}
	
	@SuppressWarnings("unchecked")
	public void fillTreeRoles(){
				
//		Root
		RoleManager roleRoot = new RoleManager();
		roleRoot.setNameRow("Roles y Privilegios");
		roleRoot.setTypeRow("ROOT");
		final TreeItem<RoleManager> root = new TreeItem<>(roleRoot);		
		root.setExpanded(true);
		
//		Recorrer en busqueda de roles
		for(RoleManager rowRole : getRoleMan().getListRoles()){
//			Solo ingresa si la linea es de tipo ROLE
			if((rowRole.getTypeRow()).equals("ROLE")){
				TreeItem<RoleManager> itemRole = new TreeItem<>(rowRole);
				itemRole.setExpanded(true);				
//				Recorrer en busqueda de los privilegios
				for(RoleManager rowPrivilege : getRoleMan().getListRoles()){
//					Solo ingresa si la linea es de tipo PRIVILEGIO y esta asociado al ROLE de rowRole
					if((rowPrivilege.getTypeRow()).equals("PRIVILEGE")&&(rowPrivilege.getIdRole()==rowRole.getIdRole())){
						TreeItem<RoleManager> itemPrivilege = new TreeItem<>(rowPrivilege);
						itemPrivilege.setExpanded(true);						
//						Recorrer en busqueda de las aplicaciones asociadas al PRIVILEGE de rowPrivilege
						for(RoleManager rowApplication : getRoleMan().getListRoles()){
//							Solo ingresa si la linea es de tipo APPLICATION y esta asociado al privilegio y role indicado
							if((rowApplication.getTypeRow()).equals("APPLICATION")&&(rowApplication.getIdRole()==rowRole.getIdRole())&&
									(rowApplication.getIdPrivilege()==rowPrivilege.getIdPrivilege())){
								TreeItem<RoleManager> itemApplication = new TreeItem<>(rowApplication);
								itemPrivilege.getChildren().add(itemApplication);
							}
						}
						itemRole.getChildren().add(itemPrivilege);
					}
				}
				root.getChildren().add(itemRole);
			}			
		}
				
		TreeTableColumn<RoleManager, String> colName = new TreeTableColumn<>("Nombre");
		colName.setPrefWidth(300);
		colName.setCellValueFactory((CellDataFeatures<RoleManager,String> p) ->
			new ReadOnlyStringWrapper(p.getValue().getValue().getNameRow()));		
		
		TreeTableColumn<RoleManager, String> colDescription = new TreeTableColumn<>("Descripcion");
		colDescription.setPrefWidth(600);
		colDescription.setCellValueFactory((CellDataFeatures<RoleManager,String> p) ->
		new ReadOnlyStringWrapper(p.getValue().getValue().getDescriptionRow()));
				
		treeRoles.setRoot(root);
		treeRoles.getColumns().addAll(colName,colDescription);
		treeRoles.setRowFactory(ttv -> {			
			ContextMenu contextMenu = new ContextMenu();
			MenuItem addRow  = new MenuItem();
			MenuItem editRow = new MenuItem();
			MenuItem delRow  = new MenuItem();
			ObservableList<MenuItem> listMenus = FXCollections.observableArrayList();
			TreeTableRow<RoleManager> row = new TreeTableRow<RoleManager>(){				
				@Override
				public void updateItem(RoleManager item, boolean empty){
					super.updateItem(item, empty);
					if(empty){
						setContextMenu(null);
					} else{						
						switch(item.getTypeRow()){
						case "ROOT":
							addRow.setText("Agregar Rol");
							listMenus.add(addRow);
							break;
						case "ROLE":
							addRow.setText("Agregar Privilegio");
							editRow.setText("Editar Rol");
							delRow.setText("Eliminar Rol");
							listMenus.addAll(addRow,editRow,delRow);
							break;
						case "PRIVILEGE":
							addRow.setText("Agregar Aplicacion");
							editRow.setText("Editar Privilegio");
							delRow.setText("Quitar Privilegio");
							listMenus.addAll(addRow,editRow,delRow);							
							break;
						case "APPLICATION":
							delRow.setText("Quitar Aplicacion");
							listMenus.add(delRow);
							break;							
						}						
						
						contextMenu.getItems().addAll(listMenus);
						
						setContextMenu(contextMenu);
						
					}
				}
			};
			
			addRow.setOnAction(evt -> {
				addAction(row.getItem().getTypeRow());
			});
			
			editRow.setOnAction(evt -> {
				editAction(row.getItem());
			});
			
			delRow.setOnAction(evt -> {
				delAction(row.getItem());
			});
			
			return row;
		});
		
		treeRoles.setShowRoot(true);
		
	}
	
	public void addAction(String type){
		try{
			String descType = "";
			
			switch(type){
			case "ROOT":
				descType = "Rol";
				break;
			case "ROLE":
				descType = "Privilegio";
				break;
			case "PRIVILEGE":
				descType = "Aplicación";
				break;
			case "APPLICATION":
				break;							
			}	
			
			Dialog<Pair<String,String>> dialog = new Dialog<>();
			dialog.setTitle("Agregar " + descType);
			dialog.setHeaderText("Ingrese los datos correspondientes");
			dialog.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/dotcook/resources/icons/64x64/page_white.png/"))));
			
			
			
		} catch(Exception ex){
			ex.printStackTrace();
			launchExceptionDialog(ex);
		}
	}
	
	public void editAction(RoleManager item){
		
	}
	
	public void delAction(RoleManager item){
		
	}

	public RoleManager getRoleMan() {
		return roleMan;
	}

	public void setRoleMan(RoleManager roleMan) {
		this.roleMan = roleMan;
	}

	public Button getAddRole() {
		return addRole;
	}

	public void setAddRole(Button addRole) {
		this.addRole = addRole;
	}

	public Button getEditRole() {
		return editRole;
	}

	public void setEditRole(Button editRole) {
		this.editRole = editRole;
	}

	public Button getDeleteRole() {
		return deleteRole;
	}

	public void setDeleteRole(Button deleteRole) {
		this.deleteRole = deleteRole;
	}

	public Button getPrivilegeMan() {
		return privilegeMan;
	}

	public void setPrivilegeMan(Button privilegeMan) {
		this.privilegeMan = privilegeMan;
	}
}
