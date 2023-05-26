CREATE OR REPLACE TYPE departamento_typ AS OBJECT (
 deptno NUMBER,
 name VARCHAR2(35),
 location VARCHAR2(35),
 MAP MEMBER FUNCTION get_deptno RETURN NUMBER,
 MEMBER PROCEDURE display_details(SELF departamento_typ)
);
/

CREATE OR REPLACE TYPE BODY departamento_typ AS
 MAP MEMBER FUNCTION get_deptno RETURN NUMBER IS
 BEGIN
    RETURN deptno;
 END;
 MEMBER PROCEDURE display_details(SELF departamento_typ) IS
 BEGIN
    DBMS_OUTPUT.PUT_LINE(TO_CHAR(deptno) || ' ' || name || ' ' || location);
 END;
END;
/