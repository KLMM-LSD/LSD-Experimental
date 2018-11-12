/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import com.google.gson.JsonObject;
import dblayer.PostQueries;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
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
    static final Histogram metric_frontpage_latency = Histogram.build()
            .name("frontpage_latency").help("Frontpage JSON latencies in seconds").register();

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
        Histogram.Timer timer = metric_frontpage_latency.startTimer();
        try {
            JsonObject ret = PostQueries.getFrontpage();
            return Response.ok(ret.toString()).build();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).build();
        } finally {
            timer.observeDuration();
        }
    }
}
