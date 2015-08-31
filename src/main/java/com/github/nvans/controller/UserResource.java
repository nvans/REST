package com.github.nvans.controller;

import com.github.nvans.domain.Address;
import com.github.nvans.domain.User;
import com.github.nvans.service.AddressService;
import com.github.nvans.service.UserService;
import com.github.nvans.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.time.LocalDate;
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

        LocalDate date = LocalDate.of(1989, 6, 28);

        User user = new User();
        user.setFirstname("ivan");
        user.setLastname("konovalov");
        user.setBirthday(date);
        user.setEmail("aaa@aaa.aa");
        user.setIsActive(true);
        user.setUsername("aaa");
        user.setPassword("aaa");

        try {
            userService.save(user);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(userService.findByEmail("aaa@aaa.aa"));

        return userService.findAllUsers();

    }

    @GET
    @Produces("text/plain")
    @Path("/user")
    public String ggg() {


        Address address = new Address();
        address.setCity("spb");
        address.setCountry("rus");
        address.setDistrict("district 9");
        address.setStreet("str");
        address.setZip("6");

        addressService.save(address);


        Address address2 = new Address();
        address2.setCity("spb2");
        address2.setCountry("rus2");
        address2.setDistrict("district 9");
        address2.setStreet("str2");
        address2.setZip("62");

        addressService.save(address2);


        User user = userService.findById(1L);
        user.setLastname("nenenen");
        user.setAddress(addressService.findById(1L));

        userService.save(user);

        user = userService.findById(1L);
        user.setAddress(addressService.findById(2L));

        userService.save(user);

        return "ggg";

    }
}
