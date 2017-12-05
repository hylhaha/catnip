package com.syeinfo.catnip;

import com.syeinfo.catnip.core.validator.RemoteSecurityValidator;
import com.syeinfo.catnip.core.common.CatnipRestClient;
import com.syeinfo.catnip.core.validator.ICatnipSecurityValidator;
import com.syeinfo.catnip.core.config.JerseyConfig;
import com.syeinfo.catnip.core.config.SwaggerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class AppConfiguration extends WebMvcConfigurerAdapter {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/" };

    @Value("${swagger.api.title:Default API title}")
    private String apiTitle;

    @Value("${swagger.api.version:unknown version}")
    private String apiVersion;

    @Value("${spring.jersey.application-path:/}")
    private String apiBasePath;

    @Value("${catnip.resource.package}")
    private String resourcePackage;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Bean
    public JerseyConfig configJersey() {

        SwaggerConfig swaggerConfig = new SwaggerConfig();
        swaggerConfig.setTitle(apiTitle);
        swaggerConfig.setBasePath(apiBasePath);
        swaggerConfig.setVersion(apiVersion);
        swaggerConfig.setResourcePackage(resourcePackage);

        List<Class> resList = ResourcesEnrollment.getResourceClasses();
        Class[] resources = new Class[resList.size()];

        return new JerseyConfig(resList.toArray(resources), swaggerConfig);

    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(15000);
        return factory;
    }

    @Bean
    public CatnipRestClient catnipRestClient(RestTemplate restTemplate, Environment ev) {
        return new CatnipRestClient(restTemplate, ev);
    }

    @Bean
    public ICatnipSecurityValidator catnipSecurityVerification(CatnipRestClient catnipRestClient) {
        return new RemoteSecurityValidator(catnipRestClient);
    }

}