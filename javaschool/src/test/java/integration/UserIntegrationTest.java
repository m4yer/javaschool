package integration;

import com.tsystems.dao.api.UserDAO;
import com.tsystems.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/appconfig-root.xml"})
@WebAppConfiguration(value = "classpath:/spring/web.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Mock
    private UserDAO userDAO;

    private MockMvc mockMvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(userDAO.findByUsername("alreadyregistered")).thenReturn(new User());
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidehomeController() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("homeController"));
    }

    @Test
    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(view().name("index"));
    }

    @Test
    public void performRegistrationProccessHttpPost() throws Exception {
        this.mockMvc.perform(post("/register")
                .param("username", "testusername")
                .param("email", "aspid999@gmail.com")
                .param("firstname", "Dennis")
                .param("lastname", "Kernel")
                .param("birthday", "24/10/1997")
                .param("password", "somepassword")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void performRegistrationProccessWithInvalidUserData() throws Exception {
        this.mockMvc.perform(post("/register")
                .param("username", "testusername")
                .param("email", "aspid999@")
                .param("firstname", "Dennis")
                .param("lastname", "Kernel")
                .param("birthday", "24/10/1997")
                .param("password", "somepassword")).andDo(print()).andExpect(status().isOk())

                .andExpect(view().name("/error/error-default"));
    }

    @Test
    public void performRegistrationProccessWithAlreadyRegisteredUsername() throws Exception {
        this.mockMvc.perform(post("/register")
                .param("username", "alreadyregistered")
                .param("email", "aspid99921@gmail.com")
                .param("firstname", "Dennis")
                .param("lastname", "Kernel")
                .param("birthday", "24/10/1997")
                .param("password", "somepassword")).andDo(print()).andExpect(status().isOk())

                .andExpect(view().name("/error/error-default"));
    }
}
