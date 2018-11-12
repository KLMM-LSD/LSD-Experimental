/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import io.prometheus.client.CollectorRegistry;
import java.io.IOException;
import java.io.StringWriter;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Lasse
 */
@Path("/metrics")
public class MetricsResource {

    @Context
    private UriInfo context;

    public MetricsResource() {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMetrics() throws IOException {
        StringWriter sw = new StringWriter();
        io.prometheus.client.exporter.common.TextFormat.write004(
                sw,
                CollectorRegistry.defaultRegistry.metricFamilySamples());
        return Response.ok(sw.toString()).build();
    }
}
