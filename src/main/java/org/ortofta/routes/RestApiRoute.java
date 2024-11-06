package org.ortofta.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dsl.yaml.YamlRoutesBuilderLoader;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RestApiRoute extends RouteBuilder {

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {
        restConfiguration()
            .component("platform-http")
            .host("0.0.0.0")
            .port(8080);

        rest("/management")
            .post("/addRoute")
            .consumes("application/yaml")
            .to("direct:addRoute");

        from("direct:addRoute")
            .process(exchange -> {
                String yamlContent = exchange.getIn().getBody(String.class);

                // Create a Resource from the YAML content
                Resource resource = ResourceHelper.fromString("yaml-route", yamlContent);

                try (// Register and load the route using YamlRoutesBuilderLoader
                YamlRoutesBuilderLoader loader = new YamlRoutesBuilderLoader()) {
                    camelContext.addRoutes(loader.loadRoutesBuilder(resource));
                }
            });
    }
}
