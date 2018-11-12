/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import com.google.gson.JsonObject;
import dblayer.PostQueries;
import dblayer.UserQueries;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
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

    static final Gauge metric_latest = Gauge.build()
            .name("latest_id").help("Latest hanesst_id in DB").register();

    static final Gauge metric_latest_frontpage_query = Gauge.build()
            .name("frontpage_query").help("Last frontpage query time in ms").register();

    static final Gauge metric_latest_frontpage_response = Gauge.build()
            .name("frontpage_request").help("Last frontpage response time in ms").register();

    static final Counter metric_500 = Counter.build()
            .name("status_500").help("Amount of HTTP 500 status codes returned").register();

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
        int ret = 1;
        try {
            if (Math.random() > 0.5) {
                ret = PostQueries.getLatest();
            }

            metric_latest.set(ret);

            return Response.ok(ret).build();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            metric_500.inc();
            return Response.status(500).build();
        }
    }

    @GET
    @Path("real")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getLatestReal() {
        try {
            int ret = PostQueries.getLatest();
            return Response.ok(ret).build();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            metric_500.inc();
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

            metric_500.inc();
            return Response.status(500).build();
        }
    }

    @GET
    @Path("frontpage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFrontpage() {
        long before, after_query = 0, after_response;

        before = System.currentTimeMillis();
        try {
            JsonObject ret = PostQueries.getFrontpage();
            after_query = System.currentTimeMillis();

            return Response.ok(ret.toString()).build();
        } catch (SQLException ex) {
            after_query = System.currentTimeMillis();

            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            metric_500.inc();
            return Response.status(500).build();
        } finally {
            after_response = System.currentTimeMillis();

            metric_latest_frontpage_query.set(after_query - before);
            metric_latest_frontpage_response.set(after_response - before);
        }
    }

    @GET
    @Path("userpage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserpage(@QueryParam("id") int userid) {
        try {
            JsonObject ret = UserQueries.getUserpage(userid);
            return Response.ok(ret.toString()).build();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);

            metric_500.inc();
            return Response.status(500).build();
        }
    }
}
