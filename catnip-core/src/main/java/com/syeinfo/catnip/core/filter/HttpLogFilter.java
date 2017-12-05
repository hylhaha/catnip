package com.syeinfo.catnip.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syeinfo.catnip.core.common.CatnipErrorEnum;
import com.syeinfo.catnip.core.exception.CatnipBizException;
import com.syeinfo.catnip.utils.JsonMinify;
import com.syeinfo.catnip.utils.SecUtil;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@PreMatching
public class HttpLogFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private Logger logger = LoggerFactory.getLogger(HttpLogFilter.class);

    private JsonMinify jsonMinify = new JsonMinify();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String reqStr = IOUtils.toString(requestContext.getEntityStream(), StandardCharsets.UTF_8);
        String md5Token = buildMD5Token(requestContext);
        String reqPath = getRequestPath(requestContext);

        String logTxt;

        try {
            logTxt = buildLogTxt(md5Token, reqPath, jsonMinify.minify(reqStr));
        } catch (Exception e) {
            throw new CatnipBizException(CatnipErrorEnum.ERR_JSON_MINIFY.getErrNum(), CatnipErrorEnum.ERR_JSON_MINIFY.name(), CatnipErrorEnum.ERR_JSON_MINIFY.getErrMsgKey());
        }

        logger.info(logTxt);

        InputStream is = IOUtils.toInputStream(reqStr, StandardCharsets.UTF_8);
        requestContext.setEntityStream(is);

    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String respStr = mapper.writeValueAsString(responseContext.getEntity());

        String md5Token = buildMD5Token(requestContext);
        String reqPath = getRequestPath(requestContext);

        String logTxt = buildLogTxt(md5Token, reqPath, respStr);

        logger.info(logTxt);

    }

    private String buildMD5Token(ContainerRequestContext requestContext) {

        String token = requestContext.getHeaderString("Authorization");
        return null != token ? SecUtil.encryptMD5(token) : null;
    }

    private String buildLogTxt(String md5Token, String uri, String body) {

        StringBuilder buffer = new StringBuilder();
        buffer.append("[" + md5Token + "] ");
        buffer.append("[" + uri + "] ");
        buffer.append(body);

        return buffer.toString();

    }

    private String getRequestPath(ContainerRequestContext requestContext) {

        ContainerRequest request = (ContainerRequest) requestContext.getRequest();
        return request.getPath(true);

    }
}
