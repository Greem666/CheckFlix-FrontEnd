package com.maciej.checkflix.frontend.client;

import com.maciej.checkflix.frontend.config.BackEndConfig;
import com.maciej.checkflix.frontend.domain.watchlist.ProvidersWatchlistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewAnalyticsBackEndClient {

    private final RestTemplate restTemplate;
    private final BackEndConfig backEndConfig;

    public byte[] getPositiveReviewAnalyticsFor(String imdbId) {
        Optional<ResponseEntity<byte[]>> providersWatchlistItems = Optional.of(
                restTemplate.getForEntity(getPositiveReviewAnalyticsForUri(imdbId), byte[].class)
        );

        return providersWatchlistItems.map(HttpEntity::getBody).orElse(new byte[0]);
    }

    private URI getPositiveReviewAnalyticsForUri(String imdbId) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .pathSegment("analytics", "v1" , "analyse", "reviews", "positive")
                .queryParam("imdbId", imdbId)
                .encode()
                .build()
                .toUri();
    }

    public byte[] getNeutralReviewAnalyticsFor(String imdbId) {
        Optional<ResponseEntity<byte[]>> providersWatchlistItems = Optional.of(
                restTemplate.getForEntity(getNeutralReviewAnalyticsForUri(imdbId), byte[].class)
        );

        return providersWatchlistItems.map(HttpEntity::getBody).orElse(new byte[0]);
    }

    private URI getNeutralReviewAnalyticsForUri(String imdbId) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("analytics/v1/analyse/reviews/neutral")
                .queryParam("imdbId", imdbId)
                .encode()
                .build()
                .toUri();
    }

    public byte[] getNegativeReviewAnalyticsFor(String imdbId) {
        Optional<ResponseEntity<byte[]>> providersWatchlistItems = Optional.of(
                restTemplate.getForEntity(getNegativeReviewAnalyticsForUri(imdbId), byte[].class)
        );

        return providersWatchlistItems.map(HttpEntity::getBody).orElse(new byte[0]);
    }

    private URI getNegativeReviewAnalyticsForUri(String imdbId) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("analytics/v1/analyse/reviews/negative")
                .queryParam("imdbId", imdbId)
                .encode()
                .build()
                .toUri();
    }

    public byte[] getReviewRatingsPieChartFor(String imdbId) {
        Optional<ResponseEntity<byte[]>> providersWatchlistItems = Optional.of(
                restTemplate.getForEntity(getReviewAnalyticsForUri(imdbId, "piechart"), byte[].class)
        );

        return providersWatchlistItems.map(HttpEntity::getBody).orElse(new byte[0]);
    }

    public byte[] addReviewRatingsDistributionFor(String imdbId) {
        Optional<ResponseEntity<byte[]>> providersWatchlistItems = Optional.of(
                restTemplate.getForEntity(getReviewAnalyticsForUri(imdbId, "histogram"), byte[].class)
        );

        return providersWatchlistItems.map(HttpEntity::getBody).orElse(new byte[0]);
    }

    private URI getReviewAnalyticsForUri(String imdbId, String chartType) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .pathSegment("analytics", "v1", "analyse", "reviews", chartType)
                .queryParam("imdbId", imdbId)
                .encode()
                .build()
                .toUri();
    }
}
