package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.domain.User;

public interface UserService {
    public boolean saveUser(User userToSave);
    public User findUserByEmail(String userEmail);
    public User checkAuthenticity(String userEmail, String userPassword);
}
