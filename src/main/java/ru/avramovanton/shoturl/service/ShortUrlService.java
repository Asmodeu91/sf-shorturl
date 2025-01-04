package ru.avramovanton.shoturl.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avramovanton.shoturl.config.ShortUrlConfig;
import ru.avramovanton.shoturl.repository.UrlMappingRepository;
import ru.avramovanton.shoturl.model.UrlMapping;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Collection;
import java.util.UUID;

@Service
public class ShortUrlService {
    @Autowired
    private UrlMappingRepository repository;

    @Autowired
    private ShortUrlConfig config;

    public String shortenUrl(String originalUrl, String userId, LocalDateTime expirationDate , long limitVisits, boolean active) {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String shortUrl = Base64.getUrlEncoder().encodeToString((uniqueId + userId).getBytes(StandardCharsets.UTF_8));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime now = LocalDateTime.now();
        String baseurl = config.getBaseUrl() + "/api/url/";
        long daysBetween = ChronoUnit.DAYS.between(now, expirationDate);
        if (daysBetween > config.getMaxLifetimeDays()) {
            expirationDate = now.plusDays(config.getMaxLifetimeDays());
        }
        if (limitVisits > 0 && limitVisits <= config.getMaxLifetimeDays()) {
        }
        else {
            if (limitVisits != -1){
                limitVisits = config.getMaxVisitorCount();
            }
        }
        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .userId(userId)
                .expirationDate(expirationDate)
                .limitVisits(limitVisits)
                .active(active)
                .build();
        System.out.println(urlMapping);
        repository.save(urlMapping);
        shortUrl = baseurl + shortUrl;
        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        UrlMapping mapping = repository.findByShortUrl(shortUrl);
        if (mapping == null) {
            return "Short URL not found";
        }
        if (mapping.getExpirationDate().isBefore(LocalDateTime.now())) {
            return "Short URL expired";
        }
        if (mapping.getLimitVisits() == 0) {
            return "Short URL limit visits exceeded";
        }
        if (mapping.getLimitVisits() > 0 ){
            mapping.setLimitVisits(mapping.getLimitVisits() - 1);
            repository.save(mapping);
        }
        return mapping.getOriginalUrl().toString();
    }

    public String registration() {
        return UUID.randomUUID().toString();
    }

    public Collection<UrlMapping> listUrl(String userId) {
        return repository.findByUserId(userId);
    }

    public void updateUrlMapping(Long id, String newOriginalUrl, LocalDateTime newExpirationDate, String userId, Long limitVisits ) {
        int updatedRows = repository.updateUrlMappingById(id, newOriginalUrl, newExpirationDate, userId, limitVisits);
        if (updatedRows == 0) {
            throw new RuntimeException("URL mapping with ID " + id + " not found");
        }
    }

    public void deleteUrlMapping(Long id, String userId) {
        int deletedRows = repository.deleteUrlMappingById(id, userId);
        if (deletedRows == 0) {
            throw new RuntimeException("URL mapping with ID " + id + " not found");
        }
    }
}
