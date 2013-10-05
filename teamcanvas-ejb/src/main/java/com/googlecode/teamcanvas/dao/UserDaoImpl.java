package com.googlecode.teamcanvas.dao;


import com.googlecode.teamcanvas.domain.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;

@Stateless
public class UserDaoImpl implements UserDao{
    private final Logger log = Logger.getLogger(UserDaoImpl.class);

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
        return em.find(User.class, emailOfUser);
    }


}
