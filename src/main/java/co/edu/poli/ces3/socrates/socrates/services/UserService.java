package co.edu.poli.ces3.socrates.socrates.services;

import co.edu.poli.ces3.socrates.socrates.dao.User;
import co.edu.poli.ces3.socrates.socrates.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserRepository repository;

    public UserService() {
        try {
            repository = new UserRepository();
        } catch (Exception e) {
            System.out.println("Error initializing UserRepository: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> listUser = null;
        try {
            listUser = this.repository.find();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching users: " + e.getMessage());
        } finally {
            return listUser;
        }
    }

}
