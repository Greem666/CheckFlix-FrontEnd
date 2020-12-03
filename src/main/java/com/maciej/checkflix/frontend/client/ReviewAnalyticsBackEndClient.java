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

    public byte[] getReviewAnalyticsFor(String imdbId) {
        Optional<ResponseEntity<byte[]>> providersWatchlistItems = Optional.of(
                restTemplate.getForEntity(getProviderWatchlistItemsUri(imdbId), byte[].class)
        );

        return providersWatchlistItems.map(HttpEntity::getBody).orElse(new byte[0]);
    }

    private URI getProviderWatchlistItemsUri(String imdbId) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("analytics/v1/analyse/reviews")
                .queryParam("imdbId", imdbId)
                .encode()
                .build()
                .toUri();
    }
}
