package rest_api_tests;

import com.github.nvans.domain.Address;
import com.github.nvans.domain.Group;
import com.github.nvans.domain.GroupType;
import com.github.nvans.domain.User;
import com.github.nvans.resource.UserResource;
import com.github.nvans.service.AddressService;
import com.github.nvans.service.UserService;
import helpers.DBUtils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        initDB();

    }
    // <--

    /**
     * Fill database with static data for tests
     *
     * todo: replace with sql
     */
    // -->
    private static void initDB() {
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
        user.setGroup(GroupType.DEVELOPERS.asGroup());

        userService.save(user);

        User user1 = new User();
        user1.setFirstname("Petr");
        user1.setLastname("Petrov");
        user1.setBirthday(LocalDate.of(1991, Month.JANUARY, 1));
        user1.setEmail("petr@petrov.ts");
        user1.setIsActive(true);
        user1.setUsername("petr");
        user1.setPassword("petr");
        user.setGroup(GroupType.MANAGERS.asGroup());

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
    public void testUserCrud() {

        WebTarget target = client.target(BASE_URI);

        // Init new user
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Smith");
        user.setEmail("johnsmith@js.com");
        user.setUsername("JohnSmith");
        user.setPassword("aoeu");
        user.setBirthday(LocalDate.of(1985, Month.DECEMBER, 31));
        user.setIsActive(true);



        /************* Test create *************/
        // Create request
        Response response = target
                .path("users/new")
                .request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        // read response
        User userResponse = response.readEntity(User.class);

        Assert.assertNotNull(userResponse);
        Assert.assertEquals(user.getFirstname(), userResponse.getFirstname());
        Assert.assertNotNull(userResponse.getId());
        // <--


        /************* Test update *************/
        user = userResponse;
        user.setUsername("SmithJohn");
        response = target
                    .path("users/" + userResponse.getId())
                    .request()
                    .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        userResponse = response.readEntity(User.class);

        Assert.assertNotNull(userResponse);
        Assert.assertEquals(user.getUsername(), userResponse.getUsername());
        Assert.assertEquals(user.getId(), userResponse.getId());

        user = userResponse;


        /************* Test delete *************/
        // delete user
        target.path("users/" + user.getId())
              .request()
              .delete();

        userResponse = userService.findById(user.getId());
        Assert.assertNull("User exists in db", userResponse);


    }
    // <--



    /**
     * Address CRUD tests
     */
    // -->
    @Test
    public void testAddressCRUD(){
        WebTarget target = client.target(BASE_URI);

        /** Initialization **/
        // -->
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
        // <--

        /************* Test create *************/
        // -->
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
        // <--

        /************* Test update *************/
        // -->
        addressResponse.setStreet("changed");

        response = target
                .path("users/" + user.getId() + "/address")
                .request()
                .post(Entity.entity(addressResponse, MediaType.APPLICATION_JSON));

        addressResponse = response.readEntity(Address.class);
        Assert.assertNotNull(addressResponse);

        // reload user
        user = userService.findById(user.getId());

        Assert.assertEquals("Addresses is not equals", addressResponse, user.getAddress());
        // <--

        /************* Test delete *************/
        // -->
        target.path("users/" + user.getId() + "/address")
              .request()
              .delete();

        // reload user
        user = userService.findById(user.getId());
        Assert.assertNull("Address exist", user.getAddress());

        // delete user
        userService.delete(user);
        // <--
    }
    // <--

    /**
     * Group CRUD tests
     *
     */
    @Test
    public void testGroupCRUD() {
        WebTarget target = client.target(BASE_URI);

        /************* Test create *************/
        Group group = new Group();
        group.setName("CEO");
//        group.setId(5);

        Response response = target
                    .path("groups/new")
                    .request()
                    .post(Entity.entity(group, MediaType.APPLICATION_JSON));



        Group groupResponse = response.readEntity(Group.class);

        System.out.println(groupResponse);

        Assert.assertNotNull(groupResponse);
        Assert.assertNotNull(groupResponse.getId());
        Assert.assertEquals(group.getName(), groupResponse.getName());

        group = groupResponse;

        /************* Test update *************/
        group.setName("EX CEO");

        response = target
                    .path("groups/" + group.getId())
                    .request()
                    .post(Entity.entity(group, MediaType.APPLICATION_JSON));

        groupResponse = response.readEntity(Group.class);

        Assert.assertNotNull(groupResponse);
        Assert.assertEquals(group.getName(), groupResponse.getName());
        Assert.assertEquals(group.getId(), groupResponse.getId());


    }
}
