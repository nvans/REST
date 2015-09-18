package com.github.nvans.resource;

import com.github.nvans.domain.Address;
import com.github.nvans.domain.User;
import com.github.nvans.service.AddressService;
import com.github.nvans.service.UserService;
import com.github.nvans.utils.exceptions.TransactionFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 *
 * User resources
 *
 * @author Ivan Konovalov
 */
@Controller
@Path("users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    /**
     * Retrieve all existing users from DB as JSON
     *
     * @return List with all users or empty list
     */
    // -->
    @GET
    @Produces({"application/json", "application/xml"})
    public List<User> getAllUsers() {

        List<User> users = userService.findAllUsers();

        return users;
    }
    // <--

    /**
     * Retrieve user by id
     *
     * @param id
     * @return
     */
    // -->
    @GET
    @Produces({"application/json", "application/xml"})
    @Path("{id}")
    public Response getUser(@PathParam("id") Long id) {

        User user = userService.findById(id);

        if (user != null) {
            return Response.ok().entity(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    // <--

    /**
     *
     * Update user
     *
     */
    // -->
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("{id}")
    public Response editUser(@PathParam("id") Long id, User user) {

        if (user != null && id.equals(user.getId())) {
            try {

                userService.save(user);
                return Response.ok().entity(user).build();

            } catch (IllegalArgumentException e) {

                return Response.status(Response.Status.BAD_REQUEST).build();

            } catch (TransactionFailException e) {

                return Response.serverError().build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    // <--

    /**
     * Create user
     *
     * @return
     */
    // -->
    @POST
    @Consumes({ "application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("/new")
    public Response createUser(User user) {

        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            userService.save(user);

            return Response.created(URI.create(user.getId() + "")).entity(user).build();

        } catch (IllegalArgumentException e) {

            return Response.status(Response.Status.BAD_REQUEST).build();

        } catch (TransactionFailException e) {

            return Response.serverError().build();
        }
    }
    // <--

    /**
     * Delete user
     *
     * @param id
     */
    @DELETE
    @Path("/{id}")
    // -->
    public Response deleteUser(@PathParam("id") Long id) {
        User user = userService.findById(id);

        if (user != null) {
            try {

                userService.delete(user);
                return Response.status(Response.Status.OK).build();

            } catch (TransactionFailException e) {
                return Response.serverError().build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    // <--

    /**
     * Retrieve users address
     *
     * @param id
     * @return address
     */
    // -->
    @GET
    @Path("{id}/address")
    @Produces({"application/json", "application/xml"})
    public Response getUserAddress(@PathParam("id") Long id) {

        User user = userService.findById(id);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Address address = user.getAddress();

        if (address != null) {
            return Response.ok().entity(address).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
    // <--

    /**
     * Address creating
     *
     */
    // -->
    @POST
    @Path("{id}/address")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Response createAddress(@PathParam("id") Long id, Address address) {

        try {

            User user = userService.findById(id);

            if (user == null || address == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            addressService.save(address);
            user.setAddress(address);
            userService.save(user);

            return Response.created(URI.create(id + "/address")).entity(address).build();

        } catch (TransactionFailException e) {
            return Response.serverError().build();
        }
    }
    // <--

    /**
     * Delete address
     *
     * @param id
     */
    // -->
    @DELETE
    @Path("{id}/address")
    public Response deleteAddress(@PathParam("id") Long id) {

        User user = userService.findById(id);

        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Address address = user.getAddress();

        if (address == null) {
            return Response.ok().build();
        }

        try {
            user.setAddress(null);
            userService.save(user);

            addressService.delete(address);

            return Response.ok().build();

        } catch (TransactionFailException e) {
            return Response.serverError().build();
        }

    }
    // <--

}