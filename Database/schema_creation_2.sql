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
-- CALL CREATE_SESSION( 'CH_PENA','CLSTGNBK205');

-- DELETE FROM TOOLBAR_APPLICATION WHERE ID_APPLICATION = 2;
-- INSERT INTO TOOLBAR_APPLICATION(ID_APPLICATION,ID_BUTTON,IS_ENABLED) VALUES (1,'SAVE',FALSE);

/*UPDATE TOOLBAR_APPLICATION
   SET IS_ENABLED = FALSE
   WHERE ID_APPLICATION = 1
	 AND ID_BUTTON IN ('BACK','CANCEL');*/
SELECT * FROM SESSION;     
-- DELETE FROM SESSION;
     
ALTER TABLE SESSION
	MODIFY COLUMN END_DATE DATETIME;







