package com.syeinfo.catnip.api.bean;

import javax.validation.constraints.NotNull;

public class UserContext {

    @NotNull
    private String username;

    @NotNull
    private String language;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
