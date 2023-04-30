DROP TABLE PERSON_DIR_OBJ_TABLE;

/* 4.2.6
Crea una tabla que almacene objetos de tipo person_dir_typ
llamada PERSON_DIR_OBJ_TABLE donde la clave primaria sea idno
*/
CREATE TABLE PERSON_DIR_OBJ_TABLE OF person_dir_typ
(idno PRIMARY KEY); 

/* 4.2.5
Crea un objeto de tipo direccion_typ.
A continuación, crea un objeto de tipo person_dir_typ
y asócialos mediante el método setDireccion.
*/

-- Creación de un objeto direccion_typ
DECLARE
    l_direccion direccion_typ := null;
    l_persona person_dir_typ := null;
BEGIN
    -- Llamada al constructor de direccion_typ
    -- iddir NUMBER, calle VARCHAR2, numero VARCHAR2, ciudad VARCHAR2
    l_direccion := direccion_typ (17, 'Outeiro', 17, 'Ourense');
    DBMS_OUTPUT.PUT_LINE('ID Direccion: ' || l_direccion.iddir || ' - Calle ' || l_direccion.calle); -- Se visualizan en Output
    -- Llamada al constructor de direccion_typ
    -- idno NUMBER, first_name VARCHAR2, last_name VARCHAR2, email VARCHAR2, phone VARCHAR2, direccion direccion_typ
    l_persona := person_dir_typ (1, 'Sabela', 'Sabucedo', 'sabela@mail.com', '666666666', null);
    l_persona.setDireccion(l_direccion);
    DBMS_OUTPUT.PUT_LINE('ID Persona: ' || l_persona.idno || ' - Calle ' || l_persona.direccion.calle); -- Se visualizan en Output
    
    /* 4.2.7
    Inserta el objeto person_dir_typ en la nueva tabla PERSON_DIR_OBJ_TABLE
    */
    
    INSERT INTO PERSON_DIR_OBJ_TABLE VALUES l_persona;
    
END;
/














