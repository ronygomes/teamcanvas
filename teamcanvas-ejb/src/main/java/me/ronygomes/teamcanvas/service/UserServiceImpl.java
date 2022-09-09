package me.ronygomes.teamcanvas.service;

import me.ronygomes.teamcanvas.dao.UserDao;
import me.ronygomes.teamcanvas.domain.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Stateless
public class UserServiceImpl implements UserService {

    private final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @EJB
    private UserDao userDao;

    public boolean saveUser(User userToSave) {
        return userDao.saveUser(userToSave);
    }

    public User findUserByEmail(String emailOfUser) {
        return userDao.findUserByEmail(emailOfUser);

    }

    @Override
    public User checkAuthenticity(String providedEmail, String providedPassword) {
        User storedUser = userDao.findUserByEmail(providedEmail);
        return checkForUserPassword(storedUser, providedPassword);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUser();
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    private User checkForUserPassword(User storedUser, String providedPassword) {
        if (!isValidPassword(storedUser, providedPassword)) {
            return null;
        }
        log.info(storedUser.getEmail() + ", Found!");
        return storedUser;
    }

    private boolean isValidPassword(User storedUser, String providedPassword) {
        return storedUser != null && storedUser.getHashedPassword().equals(providedPassword);
    }
}
