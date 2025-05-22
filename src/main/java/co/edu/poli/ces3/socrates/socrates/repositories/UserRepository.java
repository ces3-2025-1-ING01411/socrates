package co.edu.poli.ces3.socrates.socrates.repositories;

import co.edu.poli.ces3.socrates.socrates.config.MysqlConnection;
import co.edu.poli.ces3.socrates.socrates.dao.User;
import co.edu.poli.ces3.socrates.socrates.interfaces.ICrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends MysqlConnection implements ICrud {

    public UserRepository() throws Exception {
    }

    /**
     *
     */
    @Override
    public void disconnect() {

    }

    /**
     * @return
     */
    @Override
    public int insert() {
        return 0;
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Object findById(int id) throws SQLException {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<User> find() throws SQLException {
        //consultas precompiladas
        PreparedStatement pst = this.getConnection()
                .prepareStatement("select * from users");
        ResultSet result = pst.executeQuery();

        List<User> users = new ArrayList<>();
        while(result.next()) {
            users.add(
                    new User(
                            result.getInt("id"),
                            result.getString("names"),
                            result.getString("last_name"),
                            result.getDate("birthdate"),
                            result.getString("email"),
                            result.getBoolean("is_active"),
                            result.getString("phone"),
                            result.getString("gender"),
                            result.getString("password"),
                            result.getDate("created_at"),
                            result.getDate("updated_at"),
                            result.getDate("deleted_at")
                    )
            );
        }

        return users;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public int update(int id) {
        return 0;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public double delete(int id) {
        return 0;
    }

    /*
    public static void main(String[] args) {
        UserRepository usr = null;
        try {
            usr = new UserRepository();

            for (User x: usr.find()) {
                System.out.println(x.getNames());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(usr.getUrl());
    }

     */
}
