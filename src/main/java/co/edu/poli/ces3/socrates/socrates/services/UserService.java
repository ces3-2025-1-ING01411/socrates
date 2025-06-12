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

    public User findById(Integer id_user) {
        User user = null;
        try {
            user = this.repository.findById(id_user);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching the user: " + e.getMessage());
        } finally {
            return  user;
        }
    }

    public User upgrade(User userUpdate) {
        try {
            return (User)repository.upgrade(userUpdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User create(User userCreate) {
        try {
            return (User)repository.insert(userCreate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User update(User userUpdate) {
        try {
            return (User)repository.update(userUpdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        try {
            return repository.delete(id) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting the user: " + e.getMessage());
        }
    }

}
