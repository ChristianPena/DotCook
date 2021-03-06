CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_ALL_ROLES`()
BEGIN

	SELECT A.ID_ROLE, A.NAME_ROLE, A.DESCRIPTION, 
       B.ID_PRIVILEGE, B.NAME_PRIVILEGE, B.DESCRIPTION,
       C.ID_APPLICATION, C.NAME_APPLICATION, C.DESCRIPTION
	  FROM ROLE AS A INNER JOIN ROLE_PRIVILEGE AS D
		ON A.ID_ROLE = D.ID_ROLE
	  INNER JOIN PRIVILEGE AS B
		ON B.ID_PRIVILEGE = D.ID_PRIVILEGE
	  INNER JOIN PRIVILEGE_APPLICATION AS E
		ON B.ID_PRIVILEGE = E.ID_PRIVILEGE
	  INNER JOIN APPLICATION AS C
		ON C.ID_APPLICATION = E.ID_APPLICATION
	ORDER BY A.ID_ROLE, B.ID_PRIVILEGE, C.ID_APPLICATION;

END