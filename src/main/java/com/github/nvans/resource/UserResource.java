package com.github.nvans.resource;

import com.github.nvans.domain.Address;
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
     * @return List with all users or empty list
     */
    @GET
    @Produces("application/json")
    public List<User> getAllUsers() {



        return userService.findAllUsers();

    }


    /**
     * Retrieve user by id
     *
     * @param id
     * @return
     */
    @GET
    @Produces({"application/json", "text/html"})
    @Path("{id}")
    public User getUser(@PathParam("id") Long id) {

        User user = userService.findById(id);

        if (user == null) {
            throw new RuntimeException("User with id = " + id + " not found.");
        }

        return user;
    }


    /**
     * Retrieve user creating form
     *
     * @return
     */
    @GET
    @Produces("text/html")
    @Path("/new")
    public String getUsersCreatingPage() {

        return "<h1>User creating form</h1>";
    }

    /**
     * Create user
     *
     * @return
     */
    @POST
//    @Consumes({"application/x-www-form-urlencoded", "application/json"})
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


        User user = new User();

        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setBirthday(LocalDate.of(1989, Month.JUNE, 28));
        user.setEmail("test@test.test");
        user.setIsActive(true);
        user.setUsername("test");
        user.setPassword("test");

        Address address = new Address();
        address.setCountry("Russia");

        user.setAddress(address);

        try {
            userService.save(user);
            user.setAddress(address);
            userService.save(user);
            return Response.seeOther(URI.create("" + user.getId())).build();

        } catch (IllegalArgumentException e) {

            return Response.noContent().build();
        }
    }


    /**
     * Retrieve users address
     *
     * @param id
     * @return address
     */
    @GET
    @Path("{id}/address")
    @Produces("application/json")
    public Address getUserAddress(@PathParam("id") Long id) {
        User user = userService.findById(id);
        Address address = null;

        if (user != null) {
            address = user.getAddress();
        }

        return address;

    }

    /**
     * Delete address
     *
     * @param id
     */
    @DELETE
    @Path("{id}/address")
    @Produces("text/html")
    public void deleteAddress(@PathParam("id") Long id) {

        User user = userService.findById(id);

        if (user != null) {
            Address address = user.getAddress();
            addressService.delete(address);
        } else {
            throw new RuntimeException(
                    "Can't delete. User with id = " + id + "doesn't exist");
        }

    }

    /**
     * Delete user
     *
     * @param id
     */
    @DELETE
    @Path("/{id}")
    public void deleteUserById(@PathParam("id") Long id) {
        userService.deleteById(id);
    }
}
