package ru.avramovanton.shoturl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShortUrlConfig {
    @Value("${shorturl.max-lifetime-days}")
    private int maxLifetimeDays;

    @Value("${shorturl.max-visitor-count}")
    private int maxVisitorCount;

    @Value("${shorturl.base-url}")
    private String baseurl;

    public int getMaxLifetimeDays() {
        return maxLifetimeDays;
    }

    public int getMaxVisitorCount() {
        return maxVisitorCount;
    }

    public String getBaseUrl() {
        return baseurl;
    }
}
