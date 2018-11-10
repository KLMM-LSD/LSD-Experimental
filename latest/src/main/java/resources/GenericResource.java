/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import com.google.gson.JsonObject;
import dblayer.PostQueries;
import dblayer.UserQueries;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Lasse
 */
@Path("/")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getLatest() {
        try {
            int ret = PostQueries.getLatestID();
            return Response.ok(ret).build();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).build();
        }
    }

    @GET
    @Path("thread")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getThread(@QueryParam("id") int threadid) {
        try {
            JsonObject ret = PostQueries.getThread(threadid);
            return Response.ok(ret.toString()).build();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).build();
        }
    }

    @GET
    @Path("frontpage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFrontpage() {
        try {
            JsonObject ret = PostQueries.getFrontpage();
            return Response.ok(ret.toString()).build();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).build();
        }
    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserpage(@QueryParam("id") int userid) {
        try {
            JsonObject ret = UserQueries.getUserpage(userid);
            return Response.ok(ret.toString()).build();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).build();
        }
    }
}
