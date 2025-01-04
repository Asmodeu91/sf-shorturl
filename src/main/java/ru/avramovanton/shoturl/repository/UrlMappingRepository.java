package ru.avramovanton.shoturl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.avramovanton.shoturl.model.UrlMapping;

import java.time.LocalDateTime;
import java.util.Collection;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    UrlMapping findByShortUrl(String shortUrl);

    @Query("SELECT u FROM UrlMapping u WHERE u.userId = :userId")
    Collection<UrlMapping> findByUserId(String userId);

    @Modifying
    @Transactional
    @Query("UPDATE UrlMapping u SET u.originalUrl = :originalUrl, u.expirationDate = :expirationDate , u.limitVisits = :limitVisits WHERE u.id = :id AND u.userId = :userId")
    int updateUrlMappingById(
            @Param("id") Long id,
            @Param("originalUrl") String originalUrl,
            @Param("expirationDate") LocalDateTime expirationDate,
            @Param("userId") String userId,
            @Param("limitVisits") Long limitVisits
    );

    @Modifying
    @Transactional
    @Query(value="DELETE FROM UrlMapping u WHERE u.id = :id AND u.userId = :userId")
    int deleteUrlMappingById(
            @Param("id") Long id,
            @Param("userId") String userId
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM UrlMapping u WHERE u.expirationDate < CURRENT_TIMESTAMP OR u.limitVisits = 0")
    int deleteExpiredUrls();




}
