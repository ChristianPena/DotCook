/*
INSERT APPLICATION(NAME_APPLICATION,DESCRIPTION,SOURCE) VALUES ("MAIN_SCREEN","Pantalla Principal","/com/dotcook/main/Main.fxml");
INSERT PRIVILEGE(NAME_PRIVILEGE,DESCRIPTION) VALUES ("LOGIN","Acceso al sistema");
INSERT PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (1,1);
INSERT ROLE(NAME_ROLE,DESCRIPTION) VALUES ("BASICOS","Rol Básico");
INSERT ROLE_PRIVILEGE(ID_ROLE,ID_PRIVILEGE) VALUES (1,1);
INSERT USER_ROLE(ID_USER,ID_ROLE) VALUES ("CH_PENA",1);    
INSERT APPLICATION(NAME_APPLICATION,DESCRIPTION,SOURCE) VALUES ("CONFIG","Configuración","/com/dotcook/main/Config.fxml");
INSERT PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (1,2);
    
SELECT * FROM CATEGORY;
    
INSERT CATEGORY(NAME_CATEGORY,DESCRIPTION) VALUES ("MAIN","Principal");
INSERT CATEGORY(NAME_CATEGORY,DESCRIPTION) VALUES ("SYSTEM","Sistema");

UPDATE APPLICATION SET ID_CATEGORY = 1 WHERE ID_APPLICATION = 1;
UPDATE APPLICATION SET ID_CATEGORY = 2 WHERE ID_APPLICATION = 2;
        
UPDATE APPLICATION SET CONTROLLER = 'com.dotcook.main.MainController' WHERE ID_APPLICATION = 1;
UPDATE APPLICATION SET CONTROLLER = 'com.dotcook.config.ConfigController' WHERE ID_APPLICATION = 2;

UPDATE APPLICATION SET SOURCE = '/com/dotcook/config/Config.fxml' WHERE ID_APPLICATION = 2;

INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (1,'BACK',TRUE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (1,'FINISH',TRUE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (1,'CANCEL',TRUE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (1,'PRINT',FALSE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (1,'SEARCH',FALSE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (1,'HELP',TRUE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (1,'SYSTEM',TRUE);
    
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (2,'BACK',TRUE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (2,'FINISH',TRUE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (2,'CANCEL',TRUE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (2,'PRINT',FALSE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (2,'SEARCH',FALSE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (2,'HELP',TRUE);
INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (2,'SYSTEM',TRUE);

UPDATE TOOLBAR_APPLICATION SET IS_ENABLED = TRUE WHERE ID_APPLICATION = 2 AND ID_BUTTON = 'SEARCH';

CALL GET_APPLICATIONS ( 'CH_PENA' );
CALL GET_TOOLBAR_APPLICATIONS ( 'CH_PENA' );

SELECT * FROM PRIVILEGE;
SELECT * FROM PRIVILEGE_APPLICATION;

-- DELETE FROM APPLICATION WHERE ID_APPLICATION > 320;
-- DELETE FROM CATEGORY WHERE ID_CATEGORY > 300;
-- DELETE FROM PRIVILEGE WHERE ID_PRIVILEGE = 5;
-- UPDATE PRIVILEGE_APPLICATION SET ID_PRIVILEGE = 3 WHERE ID_APPLICATION IN (210,220);
-- DELETE FROM PRIVILEGE_APPLICATION WHERE ID_PRIVILEGE = 1;
-- INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (1,1);
-- DELETE FROM ROLE WHERE ID_ROLE = 2;
-- INSERT ROLE(ID_ROLE,NAME_ROLE,DESCRIPTION) VALUES (2,'ALL','Todos los privilegios');
-- INSERT INTO ROLE_PRIVILEGE(ID_ROLE,ID_PRIVILEGE) VALUES (2,1);
-- INSERT INTO ROLE_PRIVILEGE(ID_ROLE,ID_PRIVILEGE) VALUES (2,2);
-- INSERT INTO ROLE_PRIVILEGE(ID_ROLE,ID_PRIVILEGE) VALUES (2,3);
-- INSERT INTO ROLE_PRIVILEGE(ID_ROLE,ID_PRIVILEGE) VALUES (2,4);
-- DELETE FROM USER_ROLE;
-- INSERT INTO USER_ROLE(ID_USER,ID_ROLE) VALUES ('CH_PENA',2);
-- INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (6,410);
-- INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (6,420);
-- INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (6,430);
-- INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (6,440);
-- INSERT INTO ROLE_PRIVILEGE(ID_ROLE,ID_PRIVILEGE) VALUES (2,6);

-- VENTAS

INSERT INTO PRIVILEGE (NAME_PRIVILEGE,DESCRIPTION) VALUES ('SALES','Ventas');

INSERT INTO CATEGORY(ID_CATEGORY,NAME_CATEGORY,DESCRIPTION) VALUES (100,'SALES','Ventas');

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (110,'POS','Punto de Venta','/com/dotcook/sales/POS.fxml',100,'com.dotcook.sales.POSController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (2,110);

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (120,'CAJA','Caja','/com/dotcook/sales/Caja.fxml',100,'com.dotcook.sales.CajaController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (2,120);
 
-- ADMINISTRACION
 
INSERT INTO PRIVILEGE (NAME_PRIVILEGE,DESCRIPTION) VALUES ('ADMINISTRATOR','Administrador');

INSERT INTO CATEGORY(ID_CATEGORY,NAME_CATEGORY,DESCRIPTION) VALUES (200,'ADMIN','Administración');

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (210,'PRODUCT_MANAGER','Gestión de Productos','/com/dotcook/admin/ProductManager.fxml',200,'com.dotcook.admin.ProductManagerController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (2,210);

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (220,'INVENTORY_MANAGER','Gestión de Inventario','/com/dotcook/admin/InventoryManager.fxml',200,'com.dotcook.admin.InventoryManagerController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (2,220);

-- GERENCIAL

INSERT INTO PRIVILEGE (NAME_PRIVILEGE,DESCRIPTION) VALUES ('EXECUTIVE','Gerencial Ejecutivo');

INSERT INTO CATEGORY(ID_CATEGORY,NAME_CATEGORY,DESCRIPTION) VALUES (300,'REPORTS','Reportes');

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (310,'PRODUCT_REPORT','Reporte de Productos','/com/dotcook/manager/ProductReport.fxml',300,'com.dotcook.manager.ProductReportController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (4,310);

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (320,'INVENTORY_REPORT','Reporte de Inventario','/com/dotcook/manager/InventoryReport.fxml',300,'com.dotcook.manager.InventoryReportController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (4,320);

-- CONFIGURACION

INSERT INTO PRIVILEGE (NAME_PRIVILEGE,DESCRIPTION) VALUES ('CUSTOMIZER','Configuración');

INSERT INTO CATEGORY(ID_CATEGORY,NAME_CATEGORY,DESCRIPTION) VALUES (400,'SYSTEM','Sistema');

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (410,'CONFIG','Configuración de Sistema','/com/dotcook/custimizing/config/Config.fxml',400,'com.dotcook.customizing.config.ConfigController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (5,410);

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (420,'DATABASE','Configuración Base de Datos','/com/dotcook/customizing/database/Database.fxml',400,'com.dotcook.customizing.database.DatabaseController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (5,420);

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (430,'USER_MANAGER','Gestion de Usuarios','/com/dotcook/customizing/userman/UserManager.fxml',400,'com.dotcook.customizing.userman.UserManagerController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (5,430);

INSERT INTO APPLICATION(ID_APPLICATION, NAME_APPLICATION, DESCRIPTION, SOURCE, ID_CATEGORY, CONTROLLER) VALUES
 (440,'MY_USER','Mi Usuario','/com/dotcook/customizing/myuser/MyUser.fxml',400,'com.dotcook.customizing.myuser.MyUserController'); 
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (5,440);
INSERT INTO PRIVILEGE_APPLICATION(ID_PRIVILEGE,ID_APPLICATION) VALUES (1,440);

UPDATE APPLICATION 
   SET SOURCE     = '/com/dotcook/sales/pos/POS.fxml',
       CONTROLLER = 'com.dotcook.sales.pos.POSController'
 WHERE ID_APPLICATION = 110;
 
UPDATE APPLICATION 
   SET SOURCE     = '/com/dotcook/sales/caja/Caja.fxml',
       CONTROLLER = 'com.dotcook.sales.caja.CajaController'
 WHERE ID_APPLICATION = 120;

UPDATE APPLICATION 
   SET SOURCE     = '/com/dotcook/manager/product/ProductManager.fxml',
       CONTROLLER = 'com.dotcook.manager.product.ProductManagerController'
 WHERE ID_APPLICATION = 210; 
 
 UPDATE APPLICATION 
   SET SOURCE     = '/com/dotcook/manager/inventory/InventoryManager.fxml',
       CONTROLLER = 'com.dotcook.manager.inventory.InventoryManagerController'
 WHERE ID_APPLICATION = 220; 
 
UPDATE APPLICATION 
   SET SOURCE     = '/com/dotcook/reports/product/ProductReport.fxml',
       CONTROLLER = 'com.dotcook.reports.product.ProductReportController'
 WHERE ID_APPLICATION = 310; 
 
UPDATE APPLICATION 
   SET SOURCE     = '/com/dotcook/reports/inventory/InventoryReport.fxml',
       CONTROLLER = 'com.dotcook.reports.inventory.InventoryReportController'
 WHERE ID_APPLICATION = 320; 

 */

