package ru.avramovanton.shoturl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import  ru.avramovanton.shoturl.repository.UrlMappingRepository;

@Service
public class CleanupUrlService {

    @Autowired
    private UrlMappingRepository repository;
    @Scheduled(cron = "0 */10 * * * *")
    public void cleanExpiredUrls() {
        int deletedCount = repository.deleteExpiredUrls();
        if (deletedCount > 0) {
            System.out.println("Deleted " + deletedCount + " expired URLs.");
        }
    }
}
