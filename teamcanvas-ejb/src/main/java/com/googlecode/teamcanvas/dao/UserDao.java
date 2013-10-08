package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.User;

import java.util.List;

public interface UserDao {
    public boolean saveUser(User userToSave);
    public User findUserByEmail(String emailOfUser);
    public List<User> findAllUser();
}
