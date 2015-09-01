package dbTests;

import com.github.nvans.dao.UserDao;
import com.github.nvans.domain.User;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by nvans on 31.08.2015.
 *
 * @author Ivan Konovalov
 */
public class UserDaoTest {

    private static ApplicationContext ctx = null;

    private UserDao userDao;

    @BeforeClass
    public static void setUpBeforeClass() {
        ctx = new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");

    }

    @Before
    public void setUp() {
        userDao = (UserDao) ctx.getBean("userDao");
    }

    // Clean resorces
    @After
    public void dispose() {
        userDao = null;
    }

    @Test
    public void testUserDao() {
        // New user initializing
        User user = new User();

        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setBirthday(LocalDate.of(1989, Month.JUNE, 28));
        user.setEmail("test@test.test");
        user.setIsActive(true);
        user.setUsername("test");
        user.setPassword("test");

        // Save user to DB
        userDao.save(user);

        // Get user from DB by id
        User testUser = userDao.findById(user.getId());

        // Fields testing
        Assert.assertEquals("First names do not match", "firstname", testUser.getFirstname());
        Assert.assertEquals("Last names do not match", "lastname", testUser.getLastname());
        Assert.assertEquals("Email addresses do not match", "test@test.test", testUser.getEmail());
        Assert.assertEquals("Passwords do not match", "test", testUser.getPassword());
        // Timestamps testing
        Assert.assertEquals("Timestamps must be equivalent", testUser.getCreateTS(), testUser.getLastUpdateTS());

        // Change users data
        testUser.setFirstname("changed");

        // Save user to DB
        userDao.save(testUser);

        // Get changed instance of user from DB
        testUser = userDao.findById(user.getId());

        // Fields testing
        Assert.assertEquals("First names do not match", "changed", testUser.getFirstname());
        // Timestamp testing
        Assert.assertNotEquals("lastUpdateTS must differ from createTS", testUser.getCreateTS(), testUser.getLastUpdateTS());

        // Delete user from DB
        userDao.delete(testUser);

        // Try to get deleted user
        testUser = userDao.findById(user.getId());

        // Removal mechanism test
        Assert.assertNull("User exists in DB. Check the removal mechanism.", testUser);

    }


}
