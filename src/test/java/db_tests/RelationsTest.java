package db_tests;

import com.github.nvans.dao.AddressDao;
import com.github.nvans.dao.UserDao;
import com.github.nvans.domain.Address;
import com.github.nvans.domain.User;
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
public class RelationsTest {
    private static ApplicationContext ctx = null;

    private UserDao userDao;
    private AddressDao addressDao;

    @BeforeClass
    public static void setUpBeforeClass() {
        ctx = new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");

    }

    @Before
    public void setUp() {
        userDao = (UserDao) ctx.getBean("userDao");
        addressDao = (AddressDao) ctx.getBean("addressDao");
    }

    // Clean resources
    @After
    public void dispose() {
        userDao = null;
        addressDao = null;
    }

    @Test
    public void testUserAddressRelations() {

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

        // Address init
        Address address = new Address();
        address.setCountry("Country");
        address.setCity("City");
        address.setZip("12345");
        address.setStreet("Street");
        address.setDistrict("1");

        // Address saving
        addressDao.save(address);

        // set address to user
        user.setAddress(address);

        // update user
        userDao.save(user);

        User testUser = userDao.findById(user.getId());
        Address testAddress = addressDao.findById(address.getId());

        // Fields testing
        Assert.assertNotNull("Address is null", testUser.getAddress());
        Assert.assertEquals("Addresses ID's doesn't matches", testAddress.getId(), testUser.getAddress().getId());

        // Delete user
        userDao.delete(user);

        // Try to load address by deleted user
        testAddress = addressDao.findById(user.getAddress().getId());

        // Cascade removing test
        Assert.assertNull("Address exists. Cascade deleting doesn't work.", testAddress);
    }
}
