package com.googlecode.teamcanvas.dao;


import com.googlecode.teamcanvas.domain.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDaoImpl implements UserDao{

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public User saveUser(User userToSave){
        em.persist(userToSave);
        return null;
    }


    public User findUserByEmail(String emailOfUser) {
        return em.find(User.class, emailOfUser);
    }


}
