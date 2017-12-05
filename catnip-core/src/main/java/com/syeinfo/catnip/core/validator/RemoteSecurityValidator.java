package com.syeinfo.catnip.core.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.syeinfo.catnip.api.bean.ResponseHeader;
import com.syeinfo.catnip.core.common.CatnipErrorEnum;
import com.syeinfo.catnip.core.common.CatnipRestClient;
import com.syeinfo.catnip.core.exception.CatnipBizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.MultivaluedMap;

public class RemoteSecurityValidator implements ICatnipSecurityValidator {

    private Logger logger = LoggerFactory.getLogger(RemoteSecurityValidator.class);

//    @Autowired
    private CatnipRestClient catnipRestClient;

    public RemoteSecurityValidator(CatnipRestClient catnipRestClient) {
        this.catnipRestClient = catnipRestClient;
    }

    @Override
    public void verifyAuth(String account, String token, String requestPath, boolean validatePermission, String url, MultivaluedMap<String, String> requestHeaders) throws JsonProcessingException {

        if (StringUtils.isEmpty(url)) {
            throw new CatnipBizException(
                    CatnipErrorEnum.ERR_MISSING_REMOTE_API.getErrNum(),
                    CatnipErrorEnum.ERR_MISSING_REMOTE_API.name(),
                    CatnipErrorEnum.ERR_MISSING_REMOTE_API.getErrMsgKey()
            );
        }

        JsonNode response = catnipRestClient.verifyAuth(url, account, token, requestPath, validatePermission, requestHeaders);
        JsonNode responseHeader = response.get("responseHeader");

        int status = responseHeader.get("status").asInt();

        if (status == ResponseHeader.STATUS_FAILED) {

            throw new CatnipBizException(
                    responseHeader.get("errorCode").asText(),
                    responseHeader.get("errorMessage").asText()
            );

        }

        logger.info(response.toString());

    }

}
