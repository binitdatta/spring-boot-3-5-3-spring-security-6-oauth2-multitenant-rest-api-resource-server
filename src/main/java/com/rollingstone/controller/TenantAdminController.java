package com.rollingstone.controller;

import com.rollingstone.security.DynamicIssuerRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/tenants")
public class TenantAdminController {

    private final DynamicIssuerRegistry issuerRegistry;

    public TenantAdminController(DynamicIssuerRegistry registry) {
        this.issuerRegistry = registry;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTenant(@RequestParam String tenantId, @RequestParam String issuerUrl) {
        issuerRegistry.register(tenantId, issuerUrl);
        return ResponseEntity.ok("Tenant registered: " + tenantId);
    }

    @GetMapping
    public Map<String, String> getAllTenants() {
        return issuerRegistry.getAllIssuers();
    }
}

