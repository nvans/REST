package com.github.nvans.controller;

import com.github.nvans.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Path("test")
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getString() {

        System.out.println(testService);

        return testService.getMessage();
    }


}
