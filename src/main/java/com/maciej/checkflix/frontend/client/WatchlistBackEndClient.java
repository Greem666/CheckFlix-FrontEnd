package com.maciej.checkflix.frontend.client;

import com.maciej.checkflix.frontend.config.BackEndConfig;
import com.maciej.checkflix.frontend.domain.MovieDetails.MovieDetailsDto;
import com.maciej.checkflix.frontend.domain.MovieProviders.CountryResultDto;
import com.maciej.checkflix.frontend.domain.MovieReviews.ReviewResultDto;
import com.maciej.checkflix.frontend.domain.MovieSearch.MovieDto;
import com.maciej.checkflix.frontend.domain.watchlist.ProvidersWatchlistDto;
import lombok.RequiredArgsConstructor;
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
public class WatchlistBackEndClient {

    private final RestTemplate restTemplate;
    private final BackEndConfig backEndConfig;

    public List<ProvidersWatchlistDto> getProviderWatchlistItems() {
        Optional<ProvidersWatchlistDto[]> providersWatchlistItems = Optional.ofNullable(
                restTemplate.getForObject(getProviderWatchlistItemsUri(), ProvidersWatchlistDto[].class)
        );

        return providersWatchlistItems.map(Arrays::asList).orElseGet(ArrayList::new);
    }

    private URI getProviderWatchlistItemsUri() {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("watchlist-service/v1/watchlist")
                .encode()
                .build()
                .toUri();
    }

    public List<ProvidersWatchlistDto> getProviderWatchlistItemsBy(String phrase) {
        Optional<ProvidersWatchlistDto[]> providersWatchlistItems = Optional.ofNullable(
                restTemplate.getForObject(getProviderWatchlistItemsByPhraseUri(phrase), ProvidersWatchlistDto[].class)
        );

        return providersWatchlistItems.map(Arrays::asList).orElseGet(ArrayList::new);
    }

    private URI getProviderWatchlistItemsByPhraseUri(String phrase) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("watchlist-service/v1/watchlist/search")
                .queryParam("phrase", phrase)
                .encode()
                .build()
                .toUri();
    }

    public void deleteItemFromProviderWatchlist(Long watchlistItemId) {
        restTemplate.delete(deleteItemFromProviderWatchlistUri(watchlistItemId));
    }

    private URI deleteItemFromProviderWatchlistUri(Long watchlistItemId) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .pathSegment("watchlist-service", "v1", "watchlist", String.valueOf(watchlistItemId))
                .encode()
                .build()
                .toUri();
    }

    public void updateItemOnProviderWatchlist(ProvidersWatchlistDto providersWatchlistDto) {
        restTemplate.put(updateItemOnProviderWatchlistUri(), providersWatchlistDto);
    }

    private URI updateItemOnProviderWatchlistUri() {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .pathSegment("watchlist-service", "v1", "watchlist")
                .encode()
                .build()
                .toUri();
    }

    public void addNewItemToProviderWatchlist(ProvidersWatchlistDto providersWatchlistDto) {
        restTemplate.postForObject(addItemOnProviderWatchlistUri(), providersWatchlistDto, ProvidersWatchlistDto.class);
    }

    private URI addItemOnProviderWatchlistUri() {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .pathSegment("watchlist-service", "v1", "watchlist")
                .encode()
                .build()
                .toUri();
    }

    public List<String> getProviderTypes() {
        Optional<String[]> types = Optional.ofNullable(restTemplate.getForObject(getProviderTypesUri(), String[].class));

        return types.map(Arrays::asList).orElse(new ArrayList<>());
    }

    private URI getProviderTypesUri() {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .pathSegment("watchlist-service", "v1", "watchlist", "types")
                .encode()
                .build()
                .toUri();
    }
}
