package com.googlecode.teamcanvas.dao;


import com.googlecode.teamcanvas.domain.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.logging.Logger;

@Stateless
public class UserDaoImpl implements UserDao{
    private final Logger log = Logger.getLogger("UserDaoImpl");

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean saveUser(User userToSave){
        try{
            em.persist(userToSave);
            return true;
        }catch (PersistenceException e){
            log.info(userToSave.getEmail() + " already exists");
            return false;
        }
    }

    public User findUserByEmail(String emailOfUser) {
        log.info("Trying to find user");
        return em.find(User.class, emailOfUser);
    }


}
