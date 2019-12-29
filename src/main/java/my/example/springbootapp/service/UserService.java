package my.example.springbootapp.service;

import my.example.springbootapp.entities.User;
import my.example.springbootapp.exception.DBException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Iterable<User> getAllUsers() throws DBException;
    User getUserByName(String name) throws DBException;
    User getUserById(Long id) throws DBException;
    Long getUserIdByName(String name) throws DBException;
    void addUser(User user) throws DBException;
    void editUser(User user) throws DBException;
    void deleteUserById(Long id) throws DBException;
    Boolean isExistsUser(String name) throws DBException;
}
