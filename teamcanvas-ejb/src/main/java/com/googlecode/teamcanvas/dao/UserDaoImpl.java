package com.googlecode.teamcanvas.dao;


import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.domain.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import java.util.List;

@Stateless
public class UserDaoImpl implements UserDao{
    private final Logger log = Logger.getLogger(UserDaoImpl.class);

    private static final String FIND_ALL_USER = "select u from User u";

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

    @Override
    public List<User> findAllUser() {
        TypedQuery<User> query = em.createQuery(FIND_ALL_USER, User.class);
        return query.getResultList();
    }


}
