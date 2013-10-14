package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.domain.User;

import java.util.List;

public interface UserService {
    public boolean saveUser(User userToSave);
    public User findUserByEmail(String userEmail);
    public User checkAuthenticity(String userEmail, String userPassword);
    public List<User> findAllUsers();
    public void updateUser(User user);
}
