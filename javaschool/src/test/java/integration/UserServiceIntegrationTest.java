package integration;

import com.tsystems.dao.api.UserDAO;
import com.tsystems.dto.UserDTO;
import com.tsystems.entity.User;
import com.tsystems.entity.converter.Converter;
import com.tsystems.exceptions.RegisterFailedException;
import com.tsystems.service.implementation.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceIntegrationTest {
    private static final String TEST_BIRTHDAY = "24/10/1997";
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
        user.setUsername("TEST_USER");
        user.setPassword("testPassword");
        user.setEmail("aspid888@yahoo.com");
        user.setFirstname("Maksim");
        user.setLastname("Ivanov");
        when(userDAO.getAll()).thenReturn(users);
    }

    @Test
    public void addUserTest() {
        int usersListSizeBefore = userService.getAllUsers().size();
        userService.addUser(user, TEST_BIRTHDAY);
        users.add(user);
        List<UserDTO> users = userService.getAllUsers();
        assertEquals(usersListSizeBefore + 1, users.size());
    }

    @Test(expected = RegisterFailedException.class)
    public void addUserWithInvalidData() {
        user.setEmail("testemail");
        userService.addUser(user, TEST_BIRTHDAY);
    }

}
