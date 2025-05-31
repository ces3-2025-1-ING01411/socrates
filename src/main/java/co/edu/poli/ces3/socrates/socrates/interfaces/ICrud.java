package co.edu.poli.ces3.socrates.socrates.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface ICrud {
    //polimorfismo
    //definir los metodos que tiene que implementar cada repository de conexión a db
    //asegurar que lo que se cree acá se implemente donde se llame

    int insert(); //usar generics
    List<?> find() throws SQLException;
    Object findById(int id) throws SQLException;
    int update(int id);
    Object upgrade(Object object) throws SQLException;
    double delete(int id);

}
