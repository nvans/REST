package dbTests;

import com.github.nvans.dao.AddressDao;
import com.github.nvans.domain.Address;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by nvans on 31.08.2015.
 *
 * @author Ivan Konovalov
 */
public class AddressDaoTest {
    private static ApplicationContext ctx = null;

    private AddressDao addressDao;

    @BeforeClass
    public static void setUpBeforeClass() {
        ctx = new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");

    }

    @Before
    public void setUp() {
        addressDao = (AddressDao) ctx.getBean("addressDao");
    }

    // Clean resources
    @After
    public void dispose() {
        addressDao = null;
    }

    @Test
    public void testAddressDao() {

        // Address init
        Address address = new Address();
        address.setCountry("Country");
        address.setCity("City");
        address.setZip("12345");
        address.setStreet("Street");
        address.setDistrict("1");

        // Address saving
        addressDao.save(address);

        Address testAddress = addressDao.findById(address.getId());

        // Fields testing
        Assert.assertEquals("Countries do not match", "Country", testAddress.getCountry());
        Assert.assertEquals("Cities names do not match", "City", testAddress.getCity());

        // Change city
        address.setCity("Gotham city");
        addressDao.save(address);

        testAddress = addressDao.findById(address.getId());

        // Updating mechanism test
        Assert.assertEquals("Cities do not match", "Gotham city", address.getCity());
        Assert.assertEquals("ID's does not match", address.getId(), testAddress.getId());

        // Delete address instant
        addressDao.delete(address);

        // Try to get instance
        address = addressDao.findById(testAddress.getId());

        // Removal testing
        Assert.assertNull("Object exists. Check removal mechanism", address);
    }
}
