package com.github.nvans.resource;

import com.github.nvans.domain.Group;
import com.github.nvans.domain.User;
import com.github.nvans.service.GroupService;
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
 * Group resources
 *
 * @author Ivan Konovalov
 */
@Controller
@Path("groups")
public class GroupResource {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    /**
     *  Retrieve list of groups
     *
     */
    // -->
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getAllGroups() {

        List<Group> groups = groupService.findAllGroups();

        if (groups != null) {
            return Response.ok().entity(groups).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    // <--

    /**
     * Retrieve group and
     * list of users in this group
     *
     */
    // -->
    @GET
    @Produces({"application/json", "application/xml"})
    @Path("{id}")
    public Response getGroupById(@PathParam("id") Integer id) {

        Group group = groupService.findById(id);

        if (group == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<User> users = userService.findUsersByGroup(group);
        if (users == null) {
            return Response.serverError().build();
        } else {
            return Response.ok().entity(users).entity(group).build();
        }
    }
    // <--

    /**
     * Group updating
     *
     */
    // -->
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("{id}")
    public Response updateGroup(@PathParam("id") Integer id, Group group) {
        if (group != null && id.equals(group.getId())) {

            try {
                groupService.save(group);
                return Response.ok().entity(group).build();

            } catch (TransactionFailException e) {
                return Response.serverError().build();
            }

        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    // <--

    /**
     * New group creating
     *
     */
    // -->
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("/new")
    public Response createGroup(Group group) {
        if (group == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            groupService.save(group);
            return Response.created(URI.create(group.getId() + "")).entity(group).build();

        } catch (TransactionFailException e) {
            return Response.serverError().build();
        }
    }
    // <--

}
