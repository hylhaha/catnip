package com.syeinfo.catnip.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.syeinfo.catnip.api.RestfulRequest;
import com.syeinfo.catnip.api.RestfulResponse;
import com.syeinfo.catnip.core.annotation.CatnipAuth;
import com.syeinfo.catnip.core.common.CatnipRestClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Component
@Path("/aaa")
@Api(tags = {"Mobile"}, description = "MobileEndpoint")
public class MyEndpoint {

    @Autowired
    private CatnipRestClient restClient;

    @Value("${goalie.login.api}")
    private String loginUrl;

    @Context
    private HttpHeaders headers;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @CatnipAuth(validatePermission = false)
//    @CatnipAuth
    @ApiOperation(value = "Say Hello", notes = "测试方法")
    public RestfulResponse<MyResponse> sayHello(RestfulRequest<MyRequest> request) {

        RestfulResponse<MyResponse> restfulResponse = new RestfulResponse<>();

        MyResponse response = new MyResponse();
//        response.setStr("test rows");

        restfulResponse.setPayload(response);

//        throw new CatnipBizException();

        return restfulResponse;

    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Say Hello", notes = "测试方法")
    public RestfulResponse<MyResponse> sayHello2(@Valid @NotNull RestfulRequest<MyRequest> request) throws JsonProcessingException {

        JsonNode resp = restClient.login(loginUrl, "testacct1", "7c4a8d09ca3762af61e59520943dc26494f8941b", headers.getRequestHeaders());

        System.out.println(resp.toString());

        RestfulResponse<MyResponse> restfulResponse = new RestfulResponse<>();

        MyResponse response = new MyResponse();
        response.setStr("test rows");

        restfulResponse.setPayload(response);

        return restfulResponse;

    }

}
