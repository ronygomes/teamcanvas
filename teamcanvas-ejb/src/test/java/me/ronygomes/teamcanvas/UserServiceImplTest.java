package me.ronygomes.teamcanvas;


import me.ronygomes.teamcanvas.dao.UserDao;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.UserService;
import me.ronygomes.teamcanvas.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

    private User createUser() {
        User user = new User();
        user.setEmail(TEST_USER_EMAIL);
        user.setHashedPassword(TEST_USER_PASSWORD);

        return user;
    }
}
