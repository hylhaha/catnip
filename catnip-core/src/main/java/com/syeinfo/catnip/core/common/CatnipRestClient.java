package com.syeinfo.catnip.core.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.syeinfo.catnip.api.RestfulRequest;
import com.syeinfo.catnip.api.bean.RequestHeader;
import com.syeinfo.catnip.api.bean.RumContext;
import com.syeinfo.catnip.api.bean.ServiceContext;
import com.syeinfo.catnip.api.bean.UserContext;
import com.syeinfo.catnip.core.filter.AuthRequest;
import com.syeinfo.catnip.utils.DateTimeUtil;
import com.syeinfo.catnip.utils.SecUtil;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Set;

public class CatnipRestClient {

    private String sysCode;
    private String aesKey;
    private String rsaKey;

    private Environment ev;
    private RestTemplate restTemplate;

    public CatnipRestClient(RestTemplate restTemplate, Environment ev) {
        this.restTemplate = restTemplate;
        this.ev = ev;
    }

//    @Autowired
//    private RestTemplate restTemplate;

//    @Autowired
//    private Environment ev;

    @PostConstruct
    public void setProperties() {
        this.sysCode = ev.getProperty("goalie.system.code", "");
        this.aesKey = ev.getProperty("goalie.aes.key", "");
        this.rsaKey = ev.getProperty("goalie.rsa.key", "");
    }

    public JsonNode login(String url, String account, String passwd, MultivaluedMap<String, String> requestHeaders) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("account", account);
        node.put("passwd", passwd);

        String params = SecUtil.encryptRSA(node.toString(), rsaKey, false);
        RestfulRequest<AuthRequest> restfulRequest = buildRestfulRequest(params);

        HttpEntity<String> httpEntity = new HttpEntity<>(mapper.writeValueAsString(restfulRequest), buildHttpHeaders(requestHeaders));

        return restTemplate.postForObject(url, httpEntity, JsonNode.class);

    }

    public JsonNode verifyAuth(String url, String account, String token, String requestPath, boolean validatePermission, MultivaluedMap<String, String> requestHeaders) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("account", account);
        node.put("token", token);

        if (validatePermission) {
            node.put("api", requestPath);
        }

        String params = SecUtil.encryptAES(node.toString(), aesKey);
        RestfulRequest<AuthRequest> restfulRequest = buildRestfulRequest(params);

        HttpEntity<String> httpEntity = new HttpEntity<>(mapper.writeValueAsString(restfulRequest), buildHttpHeaders(requestHeaders));

        return restTemplate.postForObject(url, httpEntity, JsonNode.class);

    }

    public JsonNode changePwd(String url, String account, String token, String shaOldPwd, String shaNewPwd, MultivaluedMap<String, String> requestHeaders) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("account", account);
        node.put("token", token);
        node.put("oldPwd", shaOldPwd);
        node.put("newPwd", shaNewPwd);

        String params = SecUtil.encryptAES(node.toString(), aesKey);
        RestfulRequest<AuthRequest> restfulRequest = buildRestfulRequest(params);

        HttpEntity<String> httpEntity = new HttpEntity<>(mapper.writeValueAsString(restfulRequest), buildHttpHeaders(requestHeaders));

        return restTemplate.postForObject(url, httpEntity, JsonNode.class);

    }

    private RestfulRequest<AuthRequest> buildRestfulRequest(String params) {

        RequestHeader header = buildRequestHeader();

        AuthRequest authRequest = new AuthRequest();
        authRequest.setParams(params);

        RestfulRequest<AuthRequest> restfulRequest = new RestfulRequest<>();
        restfulRequest.setPayload(authRequest);
        restfulRequest.setRequestHeader(header);

        return restfulRequest;

    }

    private RequestHeader buildRequestHeader() {

        ServiceContext serviceContext = new ServiceContext();
        serviceContext.setChannel("SYSTEM");
        serviceContext.setServiceId("GOALIESVC");

        UserContext userContext = new UserContext();
        userContext.setUsername(sysCode);
        userContext.setLanguage(LocaleContextHolder.getLocale().toLanguageTag());

        RumContext rumContext = new RumContext();
        rumContext.setBrowserEngine("");
        rumContext.setBrowserVersion("");
        rumContext.setPlatform("");

        RequestHeader header = new RequestHeader();
        header.setServiceContext(serviceContext);
        header.setUserContext(userContext);
        header.setRumContext(rumContext);
        header.setTimestamp(DateTimeUtil.getNow());

        return header;

    }

    private HttpHeaders buildHttpHeaders(MultivaluedMap<String, String> requestHeaders) {

        HttpHeaders httpHeaders = new HttpHeaders();
        Set<String> headerNames = requestHeaders.keySet();

        for (String key : headerNames) {

            if ("Authorization".equals(key)) {
                continue;
            }

            List<String> val = requestHeaders.get(key);

            if (val.size() == 1) {
                httpHeaders.add(key, val.get(0));
            } else {
                httpHeaders.put(key, val);
            }

        }

        return httpHeaders;

    }

}
