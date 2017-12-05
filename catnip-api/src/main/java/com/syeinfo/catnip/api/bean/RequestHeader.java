package com.syeinfo.catnip.api.bean;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class RequestHeader {

    @Valid
    @NotNull
    private ServiceContext serviceContext;

    @Valid
    @NotNull
    private UserContext userContext;

    @Valid
    @NotNull
    private RumContext rumContext;

    @NotNull
    private Date timestamp;

    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    public void setServiceContext(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    public RumContext getRumContext() {
        return rumContext;
    }

    public void setRumContext(RumContext rumContext) {
        this.rumContext = rumContext;
    }

    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = null == timestamp ? null : new Date(timestamp.getTime());
    }
}
