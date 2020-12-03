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
public class ProvidersBackEndClient {

    private final RestTemplate restTemplate;
    private final BackEndConfig backEndConfig;

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
}
