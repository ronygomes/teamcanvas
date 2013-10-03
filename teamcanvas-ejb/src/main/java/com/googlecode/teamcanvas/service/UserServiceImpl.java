package com.googlecode.teamcanvas.service;


import com.googlecode.teamcanvas.dao.UserDao;
import com.googlecode.teamcanvas.domain.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.logging.Logger;

@Stateless
public class UserServiceImpl implements UserService{

    @EJB
    private UserDao userDao;
    private final Logger log = Logger.getLogger("UserServiceImpl");

    public User saveUser(User userToSave){
        userDao.saveUser(userToSave);
        return null;
    }

    public User findUserByEmail(String emailOfUser){
        return userDao.findUserByEmail(emailOfUser);

    }

    @Override
    public User checkAuthenticity(String providedEmail, String providedPassword) {
        User storedUser = userDao.findUserByEmail(providedEmail);
        return checkForUserPassword(storedUser, providedPassword);
    }


    private User checkForUserPassword(User storedUser, String providedPassword) {
        if(!isValidPassword(storedUser, providedPassword)){
            return null;
        }
        return storedUser;
    }

    private boolean isValidPassword(User storedUser, String providedPassword){
        if(storedUser != null){
            return storedUser.getHashedPassword().equals(providedPassword);
        }
        return false;
    }


}
