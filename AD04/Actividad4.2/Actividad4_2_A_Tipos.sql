DROP TYPE person_dir_typ;
DROP TYPE direccion_typ;

/* 4.2.1
Crea un nuevo tipo de objeto direccion_typ que tenga:
iddir NUMBER
calle varchar2(30)
numero varchar2(20)
codigo_postal number(5)
ciudad varchar2(35)
un constructor con iddir, calle, numero y ciudad que inicialice codigo_postal a '00000'
*/
-- Definición
 CREATE OR REPLACE TYPE direccion_typ AS OBJECT (
 -- Atributos
 iddir          NUMBER,
 calle          VARCHAR2(30),
 numero         VARCHAR2(20),
 codigo_postal  NUMBER(5),
 ciudad         VARCHAR2(35),
 -- Constructor
 CONSTRUCTOR FUNCTION direccion_typ(iddir NUMBER, calle VARCHAR2, numero VARCHAR2, ciudad VARCHAR2)
 RETURN SELF AS RESULT);
 /
 -- Implementación
 CREATE OR REPLACE TYPE BODY direccion_typ AS
    CONSTRUCTOR FUNCTION direccion_typ(iddir NUMBER, calle VARCHAR2, numero VARCHAR2, ciudad VARCHAR2)
        RETURN SELF AS RESULT
    AS
    BEGIN
        SELF.iddir := iddir;
        SELF.calle := calle;
        SELF.numero := numero;
        SELF.codigo_postal := 00000;
        SELF.ciudad := ciudad;
        RETURN;
    END;
END;
/

/* 4.2.2
Crea person_dir_typ para que sea una copia de person_typ,
pero que tenga un nuevo atributo de tipo direccion_typ llamado direccion
*/
CREATE OR REPLACE TYPE person_dir_typ AS OBJECT (
    idno            NUMBER,
    first_name      VARCHAR2(20),
    last_name       VARCHAR2(25),
    email           VARCHAR2(25),
    phone           VARCHAR2(20),
    direccion       direccion_typ, -- Añadimos el nuevo atributo
    MAP MEMBER FUNCTION get_idno RETURN NUMBER,
    MEMBER PROCEDURE display_details ( SELF IN OUT NOCOPY person_dir_typ ) -- Nombre correcto
);
/
CREATE OR REPLACE TYPE BODY person_dir_typ AS
    MAP MEMBER FUNCTION get_idno RETURN NUMBER IS
    BEGIN
        RETURN idno;
    END;
    MEMBER PROCEDURE display_details ( SELF IN OUT NOCOPY person_dir_typ ) IS  -- Nombre correcto
    BEGIN
        -- use the PUT_LINE procedure of the DBMS_OUTPUT package to display details
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(idno) || ' ' || first_name || ' ' || last_name);
        DBMS_OUTPUT.PUT_LINE(email || ' ' || phone);
    END;
END;
/

/* 4.2.3
Añade un método setDireccion que tome como entrada un objeto de tipo direccion_typ
*/
-- Creo el método en la definición
CREATE OR REPLACE TYPE person_dir_typ AS OBJECT (
    idno            NUMBER,
    first_name      VARCHAR2(20),
    last_name       VARCHAR2(25),
    email           VARCHAR2(25),
    phone           VARCHAR2(20),
    direccion       direccion_typ, -- Añadimos el nuevo atributo
    MAP MEMBER FUNCTION get_idno RETURN NUMBER,
    MEMBER PROCEDURE display_details ( SELF IN OUT NOCOPY person_dir_typ ), -- Nombre correcto
    MEMBER PROCEDURE setDireccion (direccion IN direccion_typ)
);
/
-- Se define cómo funciona el método setDireccion
CREATE OR REPLACE TYPE BODY person_dir_typ AS
    MAP MEMBER FUNCTION get_idno RETURN NUMBER IS
    BEGIN
        RETURN idno;
    END;
    MEMBER PROCEDURE setDireccion (direccion IN direccion_typ) IS -- (nombre, tipo); 'IN' para aclarar que es solo de entrada
    BEGIN
        SELF.direccion := direccion;
    END;
    MEMBER PROCEDURE display_details ( SELF IN OUT NOCOPY person_dir_typ ) IS  -- Nombre correcto
    BEGIN
        -- use the PUT_LINE procedure of the DBMS_OUTPUT package to display details
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(idno) || ' ' || first_name || ' ' || last_name);
        DBMS_OUTPUT.PUT_LINE(email || ' ' || phone);
    END;
END;
/

/* 4.2.4
Añade un método getDireccion que devuelva el atributo direccion
*/
-- Creo el método en la definición
CREATE OR REPLACE TYPE person_dir_typ AS OBJECT (
    idno            NUMBER,
    first_name      VARCHAR2(20),
    last_name       VARCHAR2(25),
    email           VARCHAR2(25),
    phone           VARCHAR2(20),
    direccion       direccion_typ, -- Añadimos el nuevo atributo
    MAP MEMBER FUNCTION get_idno RETURN NUMBER,
    MEMBER PROCEDURE display_details ( SELF IN OUT NOCOPY person_dir_typ ), -- Nombre correcto
    MEMBER PROCEDURE setDireccion (direccion IN direccion_typ),
    MEMBER FUNCTION getDireccion RETURN direccion_typ
);
/
-- Se define cómo funciona el método setDireccion
CREATE OR REPLACE TYPE BODY person_dir_typ AS
    MAP MEMBER FUNCTION get_idno RETURN NUMBER IS
    BEGIN
        RETURN idno;
    END;    
    MEMBER PROCEDURE display_details ( SELF IN OUT NOCOPY person_dir_typ ) IS  -- Nombre correcto
    BEGIN
        -- use the PUT_LINE procedure of the DBMS_OUTPUT package to display details
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(idno) || ' ' || first_name || ' ' || last_name);
        DBMS_OUTPUT.PUT_LINE(email || ' ' || phone);
    END;
    MEMBER PROCEDURE setDireccion (direccion IN direccion_typ) IS -- (nombre, tipo); 'IN' para aclarar que es solo de entrada
    BEGIN
        SELF.direccion := direccion;
    END;
    MEMBER FUNCTION getDireccion RETURN direccion_typ IS
    BEGIN
        RETURN direccion;
    END;  
END;
/




