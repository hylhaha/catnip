package com.syeinfo.catnip.core.filter;

import com.syeinfo.catnip.core.annotation.CatnipAuth;
import com.syeinfo.catnip.core.common.CatnipErrorEnum;
import com.syeinfo.catnip.core.validator.ICatnipSecurityValidator;
import com.syeinfo.catnip.core.exception.CatnipBizException;
import com.syeinfo.catnip.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.lang.reflect.Method;

@CatnipAuth
public class AuthFilter implements ContainerRequestFilter {

    private String tokenPrefix;
    private String authVerifyApi;

    @Context
    private ResourceInfo resourceInfo;

    @Autowired
    private Environment ev;

    @Autowired
    private ICatnipSecurityValidator catnipSecurityValidator;

    @PostConstruct
    public void setProperties() {
        this.tokenPrefix = ev.getProperty("goalie.token.prefix", "Bearer");
        this.authVerifyApi = ev.getProperty("goalie.auth.verify.api", "");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String token = requestContext.getHeaderString("Authorization");
        String requestPath = "/" + requestContext.getUriInfo().getPath();

        Method method = resourceInfo.getResourceMethod();
        CatnipAuth catnipAuth = method.getAnnotation(CatnipAuth.class);
        boolean shouldValidatePermission = catnipAuth.validatePermission();

        if (null != token) {

            token = token.replaceAll(tokenPrefix + " ", "");
            String account = JWTUtil.getAccount(token);

            catnipSecurityValidator.verifyAuth(account, token, requestPath, shouldValidatePermission, authVerifyApi, requestContext.getHeaders());

        } else {

            throw new CatnipBizException(
                    CatnipErrorEnum.ERR_MISSING_TOKEN.getErrNum(),
                    CatnipErrorEnum.ERR_MISSING_TOKEN.name(),
                    CatnipErrorEnum.ERR_MISSING_TOKEN.getErrMsgKey()
            );

        }

    }

}
