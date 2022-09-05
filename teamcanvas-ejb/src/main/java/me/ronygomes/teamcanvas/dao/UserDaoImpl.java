package me.ronygomes.teamcanvas.dao;


import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import me.ronygomes.teamcanvas.domain.User;
import org.apache.log4j.Logger;

import java.util.List;

@Stateless
public class UserDaoImpl implements UserDao {

    private final Logger log = Logger.getLogger(UserDaoImpl.class);

    private static final String FIND_ALL_USER = "FROM User";
    private static final String FIND_USER_EMAIL = "FROM User u WHERE u.email = :email";

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean saveUser(User userToSave) {
        try {
            em.persist(userToSave);
            return true;
        } catch (PersistenceException e) {
            log.info(userToSave.getEmail() + " already exists");
            return false;
        }
    }

    public User findUserByEmail(String emailOfUser) {
        List<User> users = em.createQuery(FIND_USER_EMAIL, User.class)
                .setParameter("email", emailOfUser)
                .getResultList();

        log.error("users.size(): " + (users == null ? -1 : users.size()));
        if (users == null || users.isEmpty()) return null;
        return users.get(0);
    }

    @Override
    public List<User> findAllUser() {
        TypedQuery<User> query = em.createQuery(FIND_ALL_USER, User.class);
        return query.getResultList();
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }
}
