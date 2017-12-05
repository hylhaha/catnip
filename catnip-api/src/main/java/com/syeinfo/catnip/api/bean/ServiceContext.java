package com.syeinfo.catnip.api.bean;

import com.syeinfo.catnip.api.annotation.Channel;
import com.syeinfo.catnip.api.annotation.ServiceId;

import javax.validation.constraints.NotNull;

public class ServiceContext {

    @NotNull
    @ServiceId
    private String serviceId;

    @NotNull
    @Channel
    private String channel;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
