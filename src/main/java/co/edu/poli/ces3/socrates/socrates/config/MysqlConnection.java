package co.edu.poli.ces3.socrates.socrates.config;

import co.edu.poli.ces3.socrates.socrates.utils.annotations.Column;
import co.edu.poli.ces3.socrates.socrates.utils.annotations.Table;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

abstract public class MysqlConnection {
    //en esta clase puedo definir un metodo que se tienen que implementar en las clases donde se aplique herencia
    //se aplique el qué y no el cómo
    private String user;
    private String password;
    private String database;
    private String host;
    private String port;
    private String url;
    private Connection connection;

    public MysqlConnection() throws Exception {
        this.user = "root";
        this.password = "root";
        this.database = "socrates";
        this.host = "localhost"; //127.0.0.1
        this.port = "3306";
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        //this.connect();


        if(!this.connect()){
            throw new Exception("Error estableciendo la conexión a la base de datos");
        }


    }

    public boolean connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(this.url, this.user, this.password);

            System.out.println("Connected to the database: " + this.database);
            return true;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public abstract void disconnect();

    public List getAllUsers() {
        return List.of();
    }

    public String getUrl() {
        return this.url;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public QueryResult getQueryUpdateAndParams(Object data, Class<?> clazz) {
        boolean hasFieldsToUpdate = false;
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        LinkedList<Object> valuesFieldsToUpdate = new LinkedList<>();
        LinkedList<Object> valuesFieldsPrimaryKey = new LinkedList<>();
        if (tableAnnotation != null) {
            StringBuilder sql = new StringBuilder("UPDATE " + tableAnnotation.name() + " SET ");
            StringBuilder sqlWhere = new StringBuilder(" WHERE ");
            try {
                for (Field field: clazz.getDeclaredFields()) {
                    Column column = field.getAnnotation(Column.class);
                    field.setAccessible(true);
                    if (column != null && field.get(data) != null) {
                        if (!column.primaryKey()) {
                            sql.append(column.name()).append(" = ?, ");
                            valuesFieldsToUpdate.add(field.get(data));
                            hasFieldsToUpdate = true;
                        } else {
                            sqlWhere.append(column.name()).append(" = ? AND ");
                            valuesFieldsPrimaryKey.add(field.get(data));
                        }
                    }

                }
            } catch (IllegalAccessException e) {
                //throw new RuntimeException("Error al acceder a los campos de la clase: " + clazz.getName(), e);
                System.out.println(e.getMessage());
            }

            if (hasFieldsToUpdate) {
                sql.deleteCharAt(sql.length()-1);
                sqlWhere.delete(sqlWhere.length()-5, sqlWhere.length()); // Eliminar el último " AND "

                sql.append(sqlWhere);

                valuesFieldsToUpdate.addAll(valuesFieldsPrimaryKey);

                return new QueryResult(sql.toString(), valuesFieldsToUpdate);
            }

            return null;
        } else {
            throw new RuntimeException("La clase " + clazz.getName() + " no tiene la anotación @Table");
        }
    }
}
