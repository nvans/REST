package com.github.nvans.controller;

import com.github.nvans.domain.User;
import com.github.nvans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    private UserService userService;

    /**
     * Retrieving all existing users from DB
     *
     * @return List with all users
     */
    @GET
    @Produces("application/json")
    public List<User> getAllUsers() {

        return userService.findAllUsers();

    }
}
