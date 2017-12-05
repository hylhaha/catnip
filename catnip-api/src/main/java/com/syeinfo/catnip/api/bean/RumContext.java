package com.syeinfo.catnip.api.bean;

import javax.validation.constraints.NotNull;

public class RumContext {

    @NotNull
    private String browserEngine;

    @NotNull
    private String browserVersion;

    @NotNull
    private String platform;

    public String getBrowserEngine() {
        return browserEngine;
    }

    public void setBrowserEngine(String browserEngine) {
        this.browserEngine = browserEngine;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
