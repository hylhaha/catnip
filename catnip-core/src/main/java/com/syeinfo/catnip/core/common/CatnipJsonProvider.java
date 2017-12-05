package com.syeinfo.catnip.core.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class CatnipJsonProvider extends JacksonJaxbJsonProvider {

    private static ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new XSSJsonDeserializer());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        mapper.registerModule(module);
    }

    public CatnipJsonProvider() {

        super();
        setMapper(mapper);

    }

}
