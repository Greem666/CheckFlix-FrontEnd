package com.maciej.checkflix.frontend.client;

import com.maciej.checkflix.frontend.config.BackEndConfig;
import com.maciej.checkflix.frontend.domain.MovieDetails.MovieDetailsDto;
import com.maciej.checkflix.frontend.domain.MovieSearch.MovieDto;
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
                .path("v1/omdb/movies")
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
                .path("v1/omdb/types")
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
                .path("v1/omdb/movie")
                .queryParam("movieImdbId", movieImdbId)
                .encode()
                .build()
                .toUri();
    }
}
