package co.edu.poli.ces3.socrates.socrates.repositories;

import co.edu.poli.ces3.socrates.socrates.config.MysqlConnection;
import co.edu.poli.ces3.socrates.socrates.config.QueryResult;
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
        PreparedStatement stm = this.getConnection()
                .prepareStatement("SELECT * FROM users WHERE id = ?");

        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            return new User(
                    rs.getInt("id"),
                    rs.getString("names"),
                    rs.getString("last_name"),
                    rs.getDate("birthdate"),
                    rs.getString("email"),
                    rs.getBoolean("is_active"),
                    rs.getString("phone"),
                    rs.getString("gender"),
                    rs.getString("password"),
                    rs.getDate("created_at"),
                    rs.getDate("updated_at"),
                    rs.getDate("deleted_at")
            );
        }

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
     * @param userUpdate
     * @return
     */
    @Override
    public Object upgrade(Object userUpdate) throws SQLException {
        QueryResult queryResult = getQueryUpdateAndParams(userUpdate, User.class);

        if (queryResult != null) {
            PreparedStatement stm = this.getConnection()
                    .prepareStatement(queryResult.getSql());
            int i = 1;
            for(Object value: queryResult.getParameters()) {
                stm.setObject(i++, value);
            }

            if (stm.executeUpdate() > 0) {
                return this.findById(((User)userUpdate).getId());
            }

        }
        return null;
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
