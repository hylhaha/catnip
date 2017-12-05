package com.syeinfo.catnip.endpoint;

import com.syeinfo.catnip.api.base.BaseRequest;
import com.syeinfo.catnip.vo.UserVO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class MyRequest extends BaseRequest {

    @Valid
    @NotNull
    private UserVO user;

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
