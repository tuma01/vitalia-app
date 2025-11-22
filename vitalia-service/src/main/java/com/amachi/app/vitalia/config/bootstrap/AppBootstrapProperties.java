package com.amachi.app.vitalia.config.bootstrap;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.bootstrap")
@Getter
@Setter
public class AppBootstrapProperties {
    private TenantProperties tenant;
    private AdminProperties superAdmin;
    private AdminProperties tenantAdmin;

    @Getter @Setter
    public static class TenantProperties {
        private TenantGlobal tenantGlobal;
        private TenantLocal tenantLocal;
    }

    @Getter @Setter
    public static class TenantGlobal {
        private String code;
        private String name;
        private String type;
        private String description;
    }

    @Getter @Setter
    public static class TenantLocal {
        private String code;
        private String name;
        private String type;
        private String fallbackHeader;
        private boolean allowLocal = true;       // 👈 nuevo
        private String defaultDomain = "vitalia.local"; // 👈 nuevo
    }

    @Getter @Setter
    public static class AdminProperties {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String personType;
    }
}
