/* 4.2.8
Recupera mediante un select todos los objetos insertados
*/
--SELECT * FROM PERSON_DIR_OBJ_TABLE; --Error, no devuelve correcto
SELECT value(p) FROM PERSON_DIR_OBJ_TABLE p;