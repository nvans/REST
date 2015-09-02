package service_tier_tests;

import com.github.nvans.dao.UserDao;
import com.github.nvans.domain.User;
import com.github.nvans.service.UserService;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by nvans on 01.09.2015.
 *
 * @author Ivan Konovalov
 */
public class UserServiceTest {

    private static ApplicationContext ctx = null;

    private UserDao userDao;
    private UserService userService;

    @BeforeClass
    public static void setUpBeforeClass() {
        ctx = new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");

    }

    @Before
    public void setUp() {
        userDao = (UserDao) ctx.getBean("userDao");
        userService = (UserService) ctx.getBean("userService");
    }

    // Clean resorces
    @After
    public void dispose() {
        userDao = null;
    }

    @Test
    public void testUserServiceSaveLogic() {
        // New user initializing
        // -->
        User user = new User();

        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setBirthday(LocalDate.of(1989, Month.JUNE, 28));
        user.setEmail("test@test.test");
        user.setIsActive(true);
        user.setUsername("test");
        user.setPassword("test");
        // <--

        // Save user to DB
        userService.save(user);

        // Get user from DB by id
        User testUser = userService.findById(user.getId());

        // Fields testing
        Assert.assertEquals("First names do not match", "firstname", testUser.getFirstname());
        Assert.assertEquals("Last names do not match", "lastname", testUser.getLastname());

        // Passivate user and update
        user.setIsActive(false);
        userService.save(user);

        // Reload test user
        testUser = userService.findById(user.getId());

        // Test activation field
        Assert.assertEquals("Activation field not changed", false, testUser.isActive());

        String message = null;
        // try to update field
        user.setUsername("root");
        try {
            userService.save(user);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }

        // Test passivation logic
        Assert.assertEquals("Passivation is not work", "*** User should be is activated before update ***", message);

        // Activate user
        user.setIsActive(true);
        userService.save(user);

        // Reload test user
        testUser = userService.findById(user.getId());

        // Test activation field
        Assert.assertTrue("User must be active now.", testUser.isActive());

        // Delete user and test it
        userService.delete(user);
        testUser = userService.findById(user.getId());
        Assert.assertNull("User must be removed.", testUser);
    }

    @Test
    public void testSameFieldsValidationLogic() {
        // New user initializing
        // -->
        User user = new User();

        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setBirthday(LocalDate.of(1989, Month.JUNE, 28));
        user.setEmail("test2@test.test");
        user.setIsActive(true);
        user.setUsername("test2");
        user.setPassword("test");
        // <--

        // Save user to DB
        userService.save(user);

        // Another user with existed email initializing
        // -->
        User fakeUser = new User();
        fakeUser.setFirstname("fakename");
        fakeUser.setLastname("fakelastname");
        fakeUser.setBirthday(LocalDate.of(1989, Month.JUNE, 28));
        fakeUser.setEmail("test2@test.test");
        fakeUser.setIsActive(true);
        fakeUser.setUsername("fake");
        fakeUser.setPassword("test");
        // <--

        String message = null;

        // Attempt to save user to DB
        try {
            userService.save(fakeUser);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }

        // Test email check logic
        Assert.assertEquals("*** User with this email exists ***", message);

        // Email changed to unique, but username exists
        fakeUser.setUsername("test2");
        fakeUser.setEmail("fake@fake.fk");

        // Attempt to save user to DB
        try {
            userService.save(fakeUser);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }

        // Test username check logic
        Assert.assertEquals("*** User with this username exists ***", message);

        // Delete user and test it
        userService.delete(user);
        user = userService.findById(user.getId());
        Assert.assertNull("User must be removed.", user);
    }

}
