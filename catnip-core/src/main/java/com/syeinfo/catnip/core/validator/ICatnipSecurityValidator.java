package com.syeinfo.catnip.core.validator;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.MultivaluedMap;

public interface ICatnipSecurityValidator {

    /**
     *
     * @param account               Account
     * @param token                 JWT
     * @param requestPath           Request URL
     * @param validatePermission    Whether validate permission
     * @param url                   Remote API URL
     * @param requestHeaders        Http request headers
     * @throws JsonProcessingException
     */
    void verifyAuth(String account,
                    String token,
                    String requestPath,
                    boolean validatePermission,
                    String url,
                    MultivaluedMap<String, String> requestHeaders) throws JsonProcessingException;

}
