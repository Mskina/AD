-- 2 Creación tabla
CREATE TABLE dept_table OF departamento_typ (deptno PRIMARY KEY);

-- 3 Almacenar objetos
DECLARE
    dept1 departamento_typ := null;
    dept2 departamento_typ := null;
BEGIN
    dept1 := departamento_typ(1, 'Departamento 1', 'Vigo');    
    dept2 := departamento_typ(2, 'Departamento 2', 'Ourense');
    
    INSERT INTO dept_table VALUES dept1;
    INSERT INTO dept_table VALUES dept2;

END;
/