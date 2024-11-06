package org.ortofta.service;

import java.io.IOException;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dsl.yaml.YamlRoutesBuilderLoader;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.logging.Log;

@ApplicationScoped
public class RouteService {

    @Inject
    CamelContext context;

    public void addRoute(String yaml) {
        Resource resource = ResourceHelper.fromString("yaml-route", yaml);

        try (YamlRoutesBuilderLoader loader = new YamlRoutesBuilderLoader()) {
            context.addRoutes(loader.loadRoutesBuilder(resource));
        } catch(Exception e) {
            Log.error("Could not add route", e);
        }
    }

    public List<String> getRoutes() {
        return context.getRoutes().stream().map(Route::getId).toList();
    }
}
