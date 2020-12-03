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

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BackEndClient {

    private final RestTemplate restTemplate;
    private final BackEndConfig backEndConfig;

    public List<MovieDto> getMoviesBy(String name, String year, String type) {
        Optional<MovieDto[]> movies = Optional.ofNullable(
                restTemplate.getForObject(getMoviesByUri(name, year, type), MovieDto[].class)
        );

        return movies.map(Arrays::asList).orElseGet(ArrayList::new);
    }

    private URI getMoviesByUri(String name, String year, String type) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("omdb-service/v1/movies")
                .queryParam("name", name)
                .queryParam("year", year)
                .queryParam("type", type)
                .encode()
                .build()
                .toUri();
    }

    public List<String> getMovieTypes() {
        Optional<String[]> movieTypes = Optional.ofNullable(
                restTemplate.getForObject(getMovieTypesUri(), String[].class)
        );

        return movieTypes.map(Arrays::asList).orElseGet(ArrayList::new);
    }

    private URI getMovieTypesUri() {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("omdb-service/v1/types")
                .encode()
                .build()
                .toUri();
    }

    public MovieDetailsDto getMovieDetails(String movieImdbId) {
        Optional<MovieDetailsDto> movieDetails = Optional.ofNullable(
                restTemplate.getForObject(getMoviesDetailsUri(movieImdbId), MovieDetailsDto.class)
        );

        return movieDetails.orElseGet(MovieDetailsDto::new);
    }

    private URI getMoviesDetailsUri(String movieImdbId) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("omdb-service/v1/movie")
                .queryParam("movieImdbId", movieImdbId)
                .encode()
                .build()
                .toUri();
    }

    public CountryResultDto getMovieProviders(String imdbId, String countryName) {
        Optional<CountryResultDto> movies = Optional.ofNullable(
                restTemplate.getForObject(getMovieProvidersUri(imdbId, countryName), CountryResultDto.class)
        );

        return movies.orElseGet(CountryResultDto::new);
    }

    private URI getMovieProvidersUri(String movieImdbId, String countryName) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("tmdb-service/v1/providers")
                .queryParam("movieImdbId", movieImdbId)
                .queryParam("countryName", countryName)
                .encode()
                .build()
                .toUri();
    }

    public List<String> getSupportedCountryProvidersList() {
        Optional<String[]> movies = Optional.ofNullable(
                restTemplate.getForObject(getSupportedCountryProvidersListUri(), String[].class)
        );

        return Arrays.asList(movies.orElse(new String[0]));
    }

    private URI getSupportedCountryProvidersListUri() {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("tmdb-service/v1/providers/countries")
                .encode()
                .build()
                .toUri();
    }

    public List<ReviewResultDto> getReviewsFor(String movieImdbId) {
        Optional<ReviewResultDto[]> movies = Optional.ofNullable(
                restTemplate.getForObject(getReviewsForUri(movieImdbId), ReviewResultDto[].class)
        );

        return Arrays.asList(movies.orElse(new ReviewResultDto[0]));
    }

    private URI getReviewsForUri(String movieImdbId) {
        return UriComponentsBuilder.fromHttpUrl(backEndConfig.getBackEndUrl())
                .port(backEndConfig.getPort())
                .path("tmdb-service/v1/reviews")
                .queryParam("imdbId", movieImdbId)
                .encode()
                .build()
                .toUri();
    }

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
