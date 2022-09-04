package me.ronygomes.teamcanvas.dao;

import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface UserDao {
    public boolean saveUser(User userToSave);

    public User findUserByEmail(String emailOfUser);

    public List<User> findAllUser();

    public void update(User user);
}
