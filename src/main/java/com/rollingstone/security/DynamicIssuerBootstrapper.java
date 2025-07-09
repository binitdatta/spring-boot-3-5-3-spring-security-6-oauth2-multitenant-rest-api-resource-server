package com.rollingstone.security;


import com.rollingstone.config.IssuerProperties;
import com.rollingstone.security.DynamicIssuerRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicIssuerBootstrapper {

    private final IssuerProperties issuerProperties;
    private final DynamicIssuerRegistry dynamicIssuerRegistry;

    public DynamicIssuerBootstrapper(IssuerProperties issuerProperties,
                                     DynamicIssuerRegistry dynamicIssuerRegistry) {
        this.issuerProperties = issuerProperties;
        this.dynamicIssuerRegistry = dynamicIssuerRegistry;
    }

    @PostConstruct
    public void init() {
        if (issuerProperties.getIssuers() != null && !issuerProperties.getIssuers().isEmpty()) {
            dynamicIssuerRegistry.initializeWithStaticIssuers(issuerProperties.getIssuers());
        }
    }
}
