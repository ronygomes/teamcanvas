package com.googlecode.teamcanvas.dao;


import com.googlecode.teamcanvas.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserDaoImpl implements UserDao{

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    public User saveUser(User userToSave){
        em.persist(userToSave);
        return null;
    }
}
