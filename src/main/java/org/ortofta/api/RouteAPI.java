package org.ortofta.api;

import java.util.List;

import org.jboss.resteasy.reactive.ResponseStatus;
import org.ortofta.service.RouteService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/api/route")
public class RouteAPI {
    
    @Inject
    RouteService service;

    @POST
    @ResponseStatus(201)
    public void addRoute(String dslRoute) {
        service.addRoute(dslRoute);
    }

    @GET
    public List<String> getRoutes() {
        return service.getRoutes();
    }
}
