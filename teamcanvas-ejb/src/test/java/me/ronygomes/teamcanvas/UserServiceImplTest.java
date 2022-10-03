package me.ronygomes.teamcanvas;

import me.ronygomes.teamcanvas.dao.UserDao;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.UserService;
import me.ronygomes.teamcanvas.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class UserServiceImplTest {

    private static final String TEST_USER_EMAIL = "john@exmaple.com";
    private static final String TEST_USER_PASSWORD = "12345";

    private static final String TEST_USER_EMAIL_OTHER = "jane@exmaple.com";
    private static final String TEST_USER_PASSWORD_OTHER = "54321";

    private UserDao userDao;

    private UserService userService;

    @BeforeEach
    void setup() {
        this.userDao = Mockito.mock(UserDao.class);
        this.userService = new UserServiceImpl(userDao);
    }

    @Test
    void testSaveUser() {
        User user = createUser();

        Mockito.when(userDao.saveUser(user)).thenReturn(true);
        Assertions.assertTrue(userService.saveUser(user));

        Mockito.verify(userDao, Mockito.times(1)).saveUser(user);
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    void testFindUserByEmail() {
        User user = createUser();

        Mockito.when(userDao.findUserByEmail(TEST_USER_EMAIL)).thenReturn(user);
        Assertions.assertSame(user, userService.findUserByEmail(TEST_USER_EMAIL));

        Mockito.verify(userDao, Mockito.times(1)).findUserByEmail(TEST_USER_EMAIL);
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    void testCheckAuthenticity() {
        User user = createUser();

        Mockito.when(userDao.findUserByEmail(TEST_USER_EMAIL)).thenReturn(user);

        Assertions.assertNull(userService.checkAuthenticity(TEST_USER_EMAIL, TEST_USER_PASSWORD_OTHER));
        Assertions.assertNull(userService.checkAuthenticity(TEST_USER_EMAIL_OTHER, TEST_USER_PASSWORD_OTHER));
        Assertions.assertNull(userService.checkAuthenticity(TEST_USER_EMAIL_OTHER, TEST_USER_PASSWORD));
        Assertions.assertSame(user, userService.checkAuthenticity(TEST_USER_EMAIL, TEST_USER_PASSWORD));

        Mockito.verify(userDao, Mockito.times(2)).findUserByEmail(TEST_USER_EMAIL_OTHER);
        Mockito.verify(userDao, Mockito.times(2)).findUserByEmail(TEST_USER_EMAIL);
    }

    @Test
    void testFindAllUsers() {
        List<User> users = Arrays.asList(createUser());

        Mockito.when(userDao.findAllUser()).thenReturn(users);
        Assertions.assertSame(users, userService.findAllUsers());

        Mockito.verify(userDao, Mockito.times(1)).findAllUser();
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    void testUpdateUser() {
        ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        User expected = createUser();

        userService.updateUser(expected);
        Mockito.verify(userDao).update(ac.capture());

        User actual = ac.getValue();
        Assertions.assertSame(expected, actual);
    }

    private User createUser() {
        User user = new User();
        user.setEmail(TEST_USER_EMAIL);
        user.setHashedPassword(TEST_USER_PASSWORD);

        return user;
    }
}
