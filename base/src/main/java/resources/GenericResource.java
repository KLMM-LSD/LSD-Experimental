/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import io.prometheus.client.Counter;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import queue.Node;

/**
 * REST Web Service
 *
 * @author Lasse
 */
@Path("/")
public class GenericResource {

    static final Counter metric_post_request = Counter.build()
            .name("post_requests").help("Amount of requests on /post").register();

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
        Node n = new Node(body, Node.KIND.POST);
        Node.insertNode(n);
        metric_post_request.inc();
        return Response.ok().build();
    }

    @POST
    @Path("signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertUser(String body) {
        Node n = new Node(body, Node.KIND.SIGNUP);
        Node.insertNode(n);
        return Response.ok().build();
    }
}
