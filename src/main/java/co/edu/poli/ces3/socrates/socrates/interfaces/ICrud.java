package co.edu.poli.ces3.socrates.socrates.interfaces;

import java.util.List;
import java.util.Objects;

public interface ICrud {
    //polimorfismo
    //definir los metodos que tiene que implementar cada repository de conexión a db
    //asegurar que lo que se cree acá se implemente donde se llame

    int insert();
    List<Objects> select();
    int update(int id);
    double delete(int id);

}
