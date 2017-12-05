package com.syeinfo.catnip.core.config;

import com.syeinfo.catnip.core.common.CatnipJsonProvider;
import com.syeinfo.catnip.core.exception.handler.*;
import com.syeinfo.catnip.core.filter.HttpLogFilter;
import com.syeinfo.catnip.core.filter.AuthFilter;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;

import javax.annotation.PostConstruct;
import javax.ws.rs.Priorities;

public class JerseyConfig extends ResourceConfig {

    private SwaggerConfig swaggerConfig;

    public JerseyConfig(Class[] resources, SwaggerConfig swaggerConfig) {

        this.swaggerConfig = swaggerConfig;

        registerClasses(resources);

        register(CommonExceptionHandler.class);
        register(ConstraintViolationExceptionHandler.class);
        register(JsonProcessingExceptionHandler.class);
        register(NotFoundExceptionHandler.class);
        register(WebApplicationExceptionHandler.class);

        register(HttpLogFilter.class);
        register(AuthFilter.class, Priorities.AUTHENTICATION);

        register(CatnipJsonProvider.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }

    @PostConstruct
    public void configureSwagger() {

        // Available at localhost:port/swagger.json
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);

        BeanConfig config = new BeanConfig();
        config.setTitle(swaggerConfig.getTitle());
        config.setVersion(swaggerConfig.getVersion());
        config.setBasePath(swaggerConfig.getBasePath());
        config.setResourcePackage(swaggerConfig.getResourcePackage());
        config.setPrettyPrint(true);
        config.setScan(true);

    }

}
