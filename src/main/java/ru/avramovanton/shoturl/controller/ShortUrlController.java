package ru.avramovanton.shoturl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import ru.avramovanton.shoturl.config.ShortUrlConfig;
import ru.avramovanton.shoturl.model.UrlMapping;
import ru.avramovanton.shoturl.service.ShortUrlService;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/api/url")
public class ShortUrlController {

    @Autowired
    private ShortUrlService ShortUrlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(
            @RequestParam String originalUrl,
            @RequestParam String userId,
            @RequestParam(defaultValue = "2999-01-01T12:00:00") String expirationDate,
            @RequestParam(defaultValue = "0") long limitVisits ,
            @RequestParam(defaultValue = "true") boolean active
    ) {
        String shortUrl = ShortUrlService.shortenUrl(originalUrl, userId, LocalDateTime.parse(expirationDate), limitVisits, active);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> redirectToOriginal(@PathVariable String shortUrl) {
        String originalUrl = ShortUrlService.getOriginalUrl(shortUrl);
        if (originalUrl.contains("http://") || originalUrl.contains("https://")) {
            return ResponseEntity.status(302).header("Location", originalUrl).build();
        }
        return ResponseEntity.ok().body(originalUrl);
    }

    @GetMapping("/registration")
    public ResponseEntity<String> registration() {
        String UUID = ShortUrlService.registration();
        return ResponseEntity.ok(UUID);
    }

    @GetMapping("/list")
    public ResponseEntity<Collection<UrlMapping>> listUrl(@RequestParam String userId) {
        Collection<UrlMapping> listUrl = ShortUrlService.listUrl(userId);
        System.out.println(listUrl);
        return ResponseEntity.ok(listUrl);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUrlMapping(
            @PathVariable Long id,
            @RequestParam String newOriginalUrl,
            @RequestParam String newExpirationDate,
            @RequestParam String userId,
            @RequestParam Long limitVisits
    ) {
        LocalDateTime expirationDate = LocalDateTime.parse(newExpirationDate);
        ShortUrlService.updateUrlMapping(id, newOriginalUrl, expirationDate, userId, limitVisits);
        return ResponseEntity.ok("URL mapping updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUrlMapping(
            @PathVariable Long id,
            @RequestParam String userId
    ) {
        ShortUrlService.deleteUrlMapping(id, userId);
        return ResponseEntity.ok("URL mapping deleted successfully");
    }

}
