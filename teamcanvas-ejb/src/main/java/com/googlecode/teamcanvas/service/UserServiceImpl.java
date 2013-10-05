package com.googlecode.teamcanvas.service;


import com.googlecode.teamcanvas.dao.UserDao;
import com.googlecode.teamcanvas.domain.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

@Stateless
public class UserServiceImpl implements UserService{

    @EJB
    private UserDao userDao;
    private final Logger log = Logger.getLogger(UserServiceImpl.class);

    public boolean saveUser(User userToSave){
        return userDao.saveUser(userToSave);
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
        log.info(storedUser.getEmail() + ", Found!");
        return storedUser;
    }

    private boolean isValidPassword(User storedUser, String providedPassword){
        return storedUser != null && storedUser.getHashedPassword().equals(providedPassword);
    }


}
