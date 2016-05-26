SELECT * FROM PASSWORD;
SELECT * FROM APPLICATION;
SELECT * FROM CATEGORY;
SELECT * FROM PRIVILEGE;
SELECT * FROM PRIVILEGE_APPLICATION;
SELECT * FROM ROLE;
SELECT * FROM ROLE_PRIVILEGE;
SELECT * FROM TOOLBAR_APPLICATION;
SELECT * FROM USER;
SELECT * FROM USER_ROLE;
    
CALL GET_APPLICATIONS ( 'CH_PENA' );
CALL GET_TOOLBAR_APPLICATIONS ( 'CH_PENA' );

-- INSERT INTO APPLICATION(NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER, POSITION)

/*
-- CATEGORY
INSERT INTO CATEGORY(ID_CATEGORY,NAME_CATEGORY,DESCRIPTION,POSITION) VALUES (1,'MAIN','Principal',100);
INSERT INTO CATEGORY(ID_CATEGORY,NAME_CATEGORY,DESCRIPTION,POSITION) VALUES (2,'SALES','Ventas',200);
INSERT INTO CATEGORY(ID_CATEGORY,NAME_CATEGORY,DESCRIPTION,POSITION) VALUES (3,'ADMIN','Administración',300);
INSERT INTO CATEGORY(ID_CATEGORY,NAME_CATEGORY,DESCRIPTION,POSITION) VALUES (4,'REPORTS','Reportes',400);
INSERT INTO CATEGORY(ID_CATEGORY,NAME_CATEGORY,DESCRIPTION,POSITION) VALUES (5,'SYSTEM','Sistema',500);

-- APPLICATION
INSERT INTO APPLICATION(ID_APPLICATION,NAME_APPLICATION,DESCRIPTION,SOURCE,ID_CATEGORY,CONTROLLER,POSITION) VALUES
(1,'MAIN_SCREEN','Pantalla Principal','/com/dotcook/main/Main.fxml',1,'com.dotcook.main.MainController',100),
(2,'POS','Punto de Venta','/com/dotcook/sales/pos/POS.fxml',2,'com.dotcook.sales.pos.POSController',100),
(3,'CAJA','Caja','/com/dotcook/sales/caja/Caja.fxml',2,'com.dotcook.sales.caja.CajaController',200),
(4,'PRODUCT_MANAGER','Gestión de Productos','/com/dotcook/manager/product/ProductManager.fxml',3,'com.dotcook.manager.product.ProductManagerController',100),
(5,'INVENTORY_MANAGER','Gestión de Inventario','/com/dotcook/manager/inventory/InventoryManager.fxml',3,'com.dotcook.manager.inventory.InventoryManagerController',200),
(6,'PRODUCT_REPORT','Reporte de Productos','/com/dotcook/reports/product/ProductReport.fxml',4,'com.dotcook.reports.product.ProductReportController',100),
(7,'INVENTORY_REPORT','Reporte de Inventario','/com/dotcook/reports/inventory/InventoryReport.fxml',4,'com.dotcook.reports.inventory.InventoryReportController',200),
(8,'CONFIG','Configuración de Sistema','/com/dotcook/custimizing/config/Config.fxml',5,'com.dotcook.customizing.config.ConfigController',100),
(9,'DATABASE','Configuración Base de Datos','/com/dotcook/customizing/database/Database.fxml',5,'com.dotcook.customizing.database.DatabaseController',200),
(10,'USER_MANAGER','Gestion de Usuarios','/com/dotcook/customizing/userman/UserManager.fxml',5,'com.dotcook.customizing.userman.UserManagerController',300),
(11,'MY_USER','Mi Usuario','/com/dotcook/customizing/myuser/MyUser.fxml',5,'com.dotcook.customizing.myuser.MyUserController',400);

-- PRIVILEGE_APPLICATION
INSERT PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES
(1,1),(2,2),(2,3),(3,4),(3,5),(4,6),(4,7),(5,8),(5,9),(5,10),(5,11);

INSERT INTO ROLE_PRIVILEGE(ID_ROLE,ID_PRIVILEGE) VALUES (2,1),(2,2),(2,3),(2,4),(2,5);

-- DELETE FROM TOOLBAR_APPLICATION WHERE ID_BUTTON IN ('FINISH','HELP','SYSTEM');

INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES
(12,'SAVE',TRUE),
(12,'CANCEL',TRUE),
(12,'PRINT',FALSE),
(12,'SEARCH',TRUE);

DELETE FROM TOOLBAR_APPLICATION WHERE ID_BUTTON = 'BACK';
    
INSERT PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (5,441);*/

