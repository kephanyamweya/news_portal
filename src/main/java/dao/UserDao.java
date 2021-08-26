
package dao;

import models.User;

import java.util.List;

public interface UserDao {
    //create
    void add (User user);

    //read
    List<User> getAll();
    User findById(int id);

    //update
    void update(int id, String name, String role, int departmentId);

    //delete
    void deleteById(int id);
    void clearAll();
}