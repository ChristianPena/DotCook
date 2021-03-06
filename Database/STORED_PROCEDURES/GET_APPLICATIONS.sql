CREATE DEFINER=`sqladmin`@`%` PROCEDURE `GET_APPLICATIONS`(IN P_ID_USER VARCHAR(30))
BEGIN

	SELECT DISTINCT A.ID_APPLICATION, A.NAME_APPLICATION, A.DESCRIPTION, A.SOURCE, E.ID_CATEGORY, E.NAME_CATEGORY, E.DESCRIPTION AS DESCRIPTION_CATEGORY, A.CONTROLLER
	  FROM APPLICATION AS A INNER JOIN PRIVILEGE_APPLICATION AS B
		ON A.ID_APPLICATION = B.ID_APPLICATION
	  INNER JOIN ROLE_PRIVILEGE AS C
		ON B.ID_PRIVILEGE = C.ID_PRIVILEGE
	  INNER JOIN USER_ROLE AS D
        ON C.ID_ROLE = D.ID_ROLE
	  INNER JOIN CATEGORY AS E
        ON A.ID_CATEGORY = E.ID_CATEGORY
	  WHERE D.ID_USER = P_ID_USER
      ORDER BY ID_CATEGORY, ID_APPLICATION;

END