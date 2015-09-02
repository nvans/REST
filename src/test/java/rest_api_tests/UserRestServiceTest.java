package rest_api_tests;

import com.github.nvans.domain.Address;
import com.github.nvans.domain.User;
import com.github.nvans.resource.UserResource;
import com.github.nvans.service.AddressService;
import com.github.nvans.service.UserService;
import helpers.DBUtils;
import helpers.UserList;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.net.URI;
import java.time.LocalDate;
import java.time.Month;

/**
 * REST api tests
 *
 * @author Ivan Konovalov
 */
public class UserRestServiceTest {

    private static final String BASE_URI = "http://localhost:8989/UserManagement/";

    private static Client client;
    private static HttpServer server;

    private static ApplicationContext ctx;
    private static UserService userService;
    private static AddressService addressService;

    private static HttpServer startServer() {
        ResourceConfig rc = new ResourceConfig();

        rc.packages("com.github.nvans.resource").registerClasses(UserResource.class);

        rc.register(SpringLifecycleListener.class);
        rc.property("contextConfigLocation", "META-INF/applicationContext.xml");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Server, client and spring context initializing
     */
    // -->
    @BeforeClass
    public static void setUp() {
        server = startServer();
        client = ClientBuilder.newClient();
        ctx = new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");

    }
    // <--

    /**
     * Fill database with static data for tests
     *
     * todo: replace with sql
     */
    // -->
    @BeforeClass
    public static void initDB() {
        userService = (UserService) ctx.getBean("userService");
        addressService = (AddressService) ctx.getBean("addressService");

        User user = new User();
        user.setFirstname("Ivan");
        user.setLastname("Konovalov");
        user.setBirthday(LocalDate.of(1989, Month.JUNE, 28));
        user.setEmail("ivan@konovalov.ts");
        user.setIsActive(true);
        user.setUsername("test");
        user.setPassword("test");

        userService.save(user);

        User user1 = new User();
        user1.setFirstname("Petr");
        user1.setLastname("Petrov");
        user1.setBirthday(LocalDate.of(1991, Month.JANUARY, 1));
        user1.setEmail("petr@petrov.ts");
        user1.setIsActive(true);
        user1.setUsername("petr");
        user1.setPassword("petr");

        userService.save(user1);

        Address address = new Address();
        address.setCountry("Russia");
        address.setCity("Saint-Petersburg");
        address.setDistrict("Test District");
        address.setZip("123456");
        address.setStreet("Test street");

        addressService.save(address);
        user.setAddress(address);
        userService.save(user);

        Address address1 = new Address();
        address1.setCountry("Russia");
        address1.setCity("Saint-Petersburg");
        address1.setDistrict("Test2 District2");
        address1.setZip("123451");
        address1.setStreet("Test street2");

        addressService.save(address1);
        user1.setAddress(address1);
        userService.save(user1);
    }
    // <--

    /**
     * Close resources and drop db
     */
    // -->
    @AfterClass
    public static void dispose() {
        client.close();
        server.stop();
        ctx = null;

        DBUtils.dropTestDB();
    }
    // <--

    /**
     * All users test
     *
     */
    // -->
    @Test
    public void testGetAllUsers() {
        WebTarget target = client.target(BASE_URI);
        Response response = target.path("/users").request(MediaType.APPLICATION_XML).get();

        // Save XML body for unmarshalling
        String users = response.readEntity(String.class);

        Assert.assertNotNull(users);

        // try to unmarshalling XML
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UserList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            UserList userList = (UserList) unmarshaller.unmarshal(new StringReader(users));

            // Test users existing
            for (User u: userList.getUsers()) {
                Assert.assertNotNull("User == null", u);
            }

            User user = userList.getUsers().get(0);
            Assert.assertEquals("", "Ivan", user.getFirstname());



        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
    // <--

    /**
     * Unique user test.
     *
     */
    @Test
    public void testGetUserById() {
        WebTarget target = client.target(BASE_URI);
        Response response = target.path("/users/1").request().get();

        Assert.assertEquals("Response must be 200",
                Response.Status.OK.getStatusCode(), response.getStatus());

        User user = response.readEntity(User.class);

        Assert.assertEquals("Ivan", user.getFirstname());

        response = target.path("/users/2").request().get();
        user = response.readEntity(User.class);

        Assert.assertEquals("Petr", user.getFirstname());
    }

    /**
     * Address resource test
     *
     */
    // -->
    @Test
    public void testGetAddress() {
        WebTarget target = client.target(BASE_URI);
        Response response = target.path("/users/1/address").request().get();

        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Address address = userService.findById(1L).getAddress();

        Address testAddress = response.readEntity(Address.class);

        Assert.assertEquals("Addresses is not equals", address, testAddress);
    }
    // <--

    /**
     * Address resource test
     *
     */
    // -->
    @Test
    public void testCreateUser() {

        WebTarget target = client.target(BASE_URI);

        // Create new user
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Smith");
        user.setEmail("johnsmith@js.com");
        user.setUsername("JohnSmith");
        user.setPassword("aoeu");
        user.setBirthday(LocalDate.of(1985, Month.DECEMBER, 31));
        user.setIsActive(true);

        // post request
        Response response = target
                .path("users/new")
                .request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        // read response
        User userResponse = response.readEntity(User.class);

        Assert.assertEquals("John", userResponse.getFirstname());
        Assert.assertNotNull(userResponse);
        Assert.assertNotNull(userResponse.getId());

        // delete user
        userService.delete(user);
    }
    // <--

    /**
     * Address creating test
     */
    // -->
    @Test
    public void testCreateDeleteAddress(){
        WebTarget target = client.target(BASE_URI);

        // Create new user
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Smith");
        user.setEmail("johnsmith@js.com");
        user.setUsername("JohnSmith");
        user.setPassword("aoeu");
        user.setBirthday(LocalDate.of(1985, Month.DECEMBER, 31));
        user.setIsActive(true);

        userService.save(user);

        // Create new address
        Address address = new Address();
        address.setCountry("Country");
        address.setCity("City");
        address.setDistrict("District");
        address.setZip("Zip");
        address.setStreet("Street");

        // POST request
        Response response = target
                .path("users/" + user.getId() + "/address")
                .request()
                .post(Entity.entity(address, MediaType.APPLICATION_JSON));

        // Retrieve address object from response
        Address addressResponse = response.readEntity(Address.class);

        Assert.assertNotNull(addressResponse);

        // reload user
        user = userService.findById(user.getId());
        Assert.assertEquals("Addresses is not equals", addressResponse, user.getAddress());

        response = target.path("users/" + user.getId() + "/address").request().delete();

        // delete user (address will remove by cascading)
        userService.delete(user);
    }
    // <--
}
