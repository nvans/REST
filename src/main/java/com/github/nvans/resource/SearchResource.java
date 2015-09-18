package com.github.nvans.resource;

import com.github.nvans.domain.User;
import com.github.nvans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

/**
 * Search resources
 *
 * @author Ivan Konovalov
 */
@Controller
@Path("search")
public class SearchResource {

    @Autowired
    private UserService userService;

    /**
     * Search by birthday
     *
     * return: list of users or empty list
     *
     */
    @GET
    @Path("birthday/{birthday}")
    @Produces({"application/json", "application/xml"})
    public List<User> findUsersByBirthday (@PathParam("birthday") String birthday) {

        LocalDate birthdayDate = LocalDate.parse(birthday);

        List<User> users = userService.findByBirthday(birthdayDate);

        return users;
    }

    /**
     * Search by firstname
     *
     * return: list of users or empty list
     *
     */
    @GET
    @Path("firstname/{firstname}")
    @Produces({"application/json", "application/xml"})
    public List<User> findUsersByFirstName (@PathParam("firstname") String firstname) {


        List<User> users = userService.findByFirstname(firstname);

        return users;
    }

    /**
     * Search by lastname
     *
     * return: list of users or empty list
     *
     */
    @GET
    @Path("lastname/{lastname}")
    @Produces({"application/json", "application/xml"})
    public List<User> findUsersByLastName (@PathParam("lastname") String lastname) {

        List<User> users = userService.findByFirstname(lastname);

        return users;
    }

    /**
     * Search by email
     *
     * return: Response object
     *
     */
    @GET
    @Path("email/{email}")
    @Produces({"application/json", "application/xml"})
    public Response findUsersByEmail (@PathParam("email") String email) {

        User user = userService.findByEmail(email);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(user).build();
        }

    }

}
