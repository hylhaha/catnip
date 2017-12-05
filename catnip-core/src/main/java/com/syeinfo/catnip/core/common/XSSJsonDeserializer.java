package com.syeinfo.catnip.core.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.IOException;

public class XSSJsonDeserializer extends JsonDeserializer<String> implements ContextualDeserializer {

    @Override
    public String deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
        String value = parser.getValueAsString();
        return Jsoup.clean(value, Whitelist.basicWithImages());
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctx, BeanProperty property) throws JsonMappingException {
        return this;
    }

}
