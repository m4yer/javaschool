package integration;

import com.tsystems.dao.api.UserDAO;
import com.tsystems.dto.UserDTO;
import com.tsystems.entity.User;
import com.tsystems.exceptions.RegisterFailedException;
import com.tsystems.service.implementation.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceIntegrationTest {
    private static final String TEST_BIRTHDAY = "24/10/1997";
    private static final String TEST_USERNAME = "TEST_USER";
    private static final String TEST_EMAIL = "aspid888@yahoo.com";
    private User user;
    private List<User> users;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDAO userDAO;

    @Before
    public void init() {
        users = new ArrayList<>();
        user = new User();
        user.setUsername(TEST_USERNAME);
        user.setPassword("testPassword");
        user.setEmail(TEST_EMAIL);
        user.setFirstname("Maksim");
        user.setLastname("Ivanov");
        users.add(user);
        when(userDAO.getAll()).thenReturn(users);
        when(userDAO.findByUsername(TEST_USERNAME)).thenReturn(findByUsernameMock(TEST_USERNAME));
    }

    @Test
    public void addUserTest() {
        int usersListSizeBefore = userService.getAllUsers().size();
        user.setEmail("test@test.ru");
        user.setUsername("anotherusername");
        userService.addUser(user, TEST_BIRTHDAY);
        users.add(user);
        List<UserDTO> users = userService.getAllUsers();
        assertEquals(usersListSizeBefore + 1, users.size());
    }

    @Test(expected = RegisterFailedException.class)
    public void addUserWithInvalidData() {
        userService.addUser(user, TEST_BIRTHDAY);
    }

    @Test(expected = RegisterFailedException.class)
    public void addUserWithInvalidData2() {
        userService.addUser(user, TEST_BIRTHDAY);
    }

    @Test(expected = RegisterFailedException.class)
    public void addUserWithAlreadyRegisteredUsername() {
        users.add(user);
        userService.addUser(user, TEST_BIRTHDAY);
    }

    private User findByUsernameMock(String username) {
        Predicate<User> userPredicate = userElem -> userElem.getUsername().equals(username);
        return users.stream().anyMatch(userPredicate) ? user : null;
    }

}
