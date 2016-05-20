CREATE DEFINER=`sqladmin`@`%` PROCEDURE `CREATE_SESSION`(IN P_ID_USER VARCHAR(30), IN P_HOSTNAME VARCHAR(30))
BEGIN
	
    DECLARE LV_SESSION  VARCHAR(100);
    DECLARE LV_SERVERNAME VARCHAR(30);
    DECLARE LV_START_DATE DATE;
    DECLARE LV_LARGE VARCHAR(250);
    
    SELECT @@HOSTNAME INTO LV_SERVERNAME;
    SELECT CURDATE() INTO LV_START_DATE;
    
    SELECT CONCAT(P_ID_USER, P_HOSTNAME, LV_SERVERNAME, LV_START_DATE) INTO LV_LARGE;
        
    SELECT SHA1(LV_LARGE) INTO LV_SESSION;

	INSERT INTO SESSION(ID_SESSION,ID_USER,HOSTNAME,SERVERNAME,START_DATE) 
		VALUES (LV_SESSION,P_ID_USER,P_HOSTNAME,LV_SERVERNAME,LV_START_DATE);
        
	COMMIT WORK;
        
	SELECT *
      FROM SESSION
      WHERE ID_SESSION = LV_SESSION
		AND ID_USER    = P_ID_USER;	

END