/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dblayer.Queries;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import queue.Node;

/**
 * REST Web Service
 *
 * @author Lasse
 */
@Path("/")
public class GenericResource {

    public static int counter = 0;
    private static final Logger log = Logger.getLogger(GenericResource.class.getName());

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response putPost(String body) throws InterruptedException, SQLException {
        log.info("Creating a Post");
        Node n = new Node(body, Node.KIND.POST);
        Node.insertNode(n);
        return Response.ok().build();
    }

    @POST
    @Path("signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertUser(String body) {
        log.info("Creating a User");
        Node n = new Node(body, Node.KIND.SIGNUP);
        Node.insertNode(n);
        return Response.ok().build();
    }
}
