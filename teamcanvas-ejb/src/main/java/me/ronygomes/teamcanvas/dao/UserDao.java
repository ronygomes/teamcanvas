package me.ronygomes.teamcanvas.dao;

import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface UserDao {

    boolean saveUser(User userToSave);

    User findUserByEmail(String emailOfUser);

    List<User> findAllUser();

    void update(User user);
}
