package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.User;

public interface UserDao {
    public User saveUser(User userToSave);
    public User findUserByEmail(String emailOfUser);
}
