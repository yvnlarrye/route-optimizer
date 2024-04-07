package com.diplom.routeoptimizer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "graphhopper")
public class GraphHopperConfig {
    private String url;
    private String secret;
}
