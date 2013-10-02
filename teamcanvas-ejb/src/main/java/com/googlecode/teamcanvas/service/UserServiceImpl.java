package com.googlecode.teamcanvas.service;


import com.googlecode.teamcanvas.dao.UserDao;
import com.googlecode.teamcanvas.domain.User;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserServiceImpl implements UserService{

    @EJB
    private UserDao userDao;

    public User saveUser(User userToSave){
        userDao.saveUser(userToSave);
        return null;
    }
}
