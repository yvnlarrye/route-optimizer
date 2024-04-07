package com.diplom.routeoptimizer.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "geocoding")
public class GeocodingConfig {
    private String url;
    private String secret;
}


