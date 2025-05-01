package co.edu.poli.ces3.socrates.socrates.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public MysqlConnection() {
        this.user = "root";
        this.password = "root";
        this.database = "socrates";
        this.host = "localhost"; //127.0.0.1
        this.port = "3306";
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    public abstract void disconnect();

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
}
