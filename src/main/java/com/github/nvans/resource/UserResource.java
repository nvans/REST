package com.github.nvans.resource;

import com.github.nvans.domain.User;
import com.github.nvans.service.AddressService;
import com.github.nvans.service.UserService;
import com.github.nvans.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Controller
@Path("users")
public class UserResource {

    @Autowired
    private UserService userService = new UserServiceImpl();

    @Autowired
    private AddressService addressService;

    /**
     * Retrieving all existing users from DB as JSON
     *
     * @return List with all users
     */
    @GET
    @Produces("application/json")
    public List<User> getAllUsers() {



        return userService.findAllUsers();

    }

    @GET
    @Produces({"application/json", "text/html"})
    @Path("{id}")
    public Response getUser(@PathParam("id") Long id) {

        User user = userService.findById(id);

        if (user != null) {
            return Response.status(Response.Status.OK).entity(user).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Produces("text/html")
    @Path("/new")
    public String aaa() {

        return " <form method=\"post\">" +
                "<input type=\"submit\" value=\"Submit\"/>\n" +
                "    </form>";
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces({"text/html"})
    @Path("/new")
    public Response createUser(
                 /*  @FormParam("firstname") String firstname,
                   @FormParam("lastname") String lastname,
                   @FormParam("username") String username,
                   @FormParam("password") String password,
                   @FormParam("email") String email,
                   @FormParam("birthday") LocalDate birthday,
                   @FormParam("active") Boolean isActive*/) {

//        User user = new User();
//        user.setFirstname(firstname);
//        user.setLastname(lastname);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//        user.setBirthday(birthday);
//        user.setIsActive(isActive);
        User user = new User();

        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setBirthday(LocalDate.of(1989, Month.JUNE, 28));
        user.setEmail("test@test.test");
        user.setIsActive(true);
        user.setUsername("test");
        user.setPassword("test");

        try {
            userService.save(user);

            return Response.seeOther(URI.create("" + user.getId())).build();

        } catch (IllegalArgumentException e) {

            return Response.noContent().build();
        }
    }

    @GET
    @Path("test")
    @Produces("text/plain")
    public String test() {
        return "test";
    }
}
