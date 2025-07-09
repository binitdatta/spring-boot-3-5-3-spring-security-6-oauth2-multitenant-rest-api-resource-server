package com.rollingstone.config;



import com.rollingstone.security.TenantMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "security")
public class IssuerProperties {
    private Map<String, String> issuers;
    private TenantMode mode = TenantMode.STATIC;

    public Map<String, String> getIssuers() {
        return issuers;
    }

    public void setIssuers(Map<String, String> issuers) {
        this.issuers = issuers;
    }

    public TenantMode getMode() {
        return mode;
    }

    public void setMode(TenantMode mode) {
        this.mode = mode;
    }
}


