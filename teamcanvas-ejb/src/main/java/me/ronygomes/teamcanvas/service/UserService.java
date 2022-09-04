package me.ronygomes.teamcanvas.service;

import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface UserService {

    boolean saveUser(User userToSave);

    User findUserByEmail(String userEmail);

    User checkAuthenticity(String userEmail, String userPassword);

    List<User> findAllUsers();

    void updateUser(User user);
}
