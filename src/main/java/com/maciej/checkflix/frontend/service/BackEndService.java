package com.maciej.checkflix.frontend.service;

import com.maciej.checkflix.frontend.client.*;
import com.maciej.checkflix.frontend.domain.ClientLocation.IpApiDto;
import com.maciej.checkflix.frontend.domain.MovieDetails.MovieDetailsDto;
import com.maciej.checkflix.frontend.domain.MovieProviders.CountryResultDto;
import com.maciej.checkflix.frontend.domain.MovieReviews.ReviewResultDto;
import com.maciej.checkflix.frontend.domain.MovieSearch.MovieDto;
import com.maciej.checkflix.frontend.domain.watchlist.ProvidersWatchlistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BackEndService {

    private final MoviesBackEndClient moviesBackEndClient;
    private final ProvidersBackEndClient providersBackEndClient;
    private final WatchlistBackEndClient watchlistBackEndClient;
    private final ReviewAnalyticsBackEndClient reviewAnalyticsBackEndClient;
    private final LocationClient locationClient;

    public List<MovieDto> getMoviesBy(String name, String year, String type) {
        return moviesBackEndClient.getMoviesBy(name, year, type);
    }

    public List<String> getMovieTypes() {
        return moviesBackEndClient.getMovieTypes();
    }

    public MovieDetailsDto getMovieDetails(String movieImdbId) {
        return moviesBackEndClient.getMovieDetails(movieImdbId);
    }

    public CountryResultDto getLocalProviders(String imdbId, String countryName) {
        return providersBackEndClient.getMovieProviders(imdbId, countryName);
    }

    public List<String> getSupportedCountryProvidersList() {
        return providersBackEndClient.getSupportedCountryProvidersList();
    }

    public List<ReviewResultDto> getReviewsFor(String movieImdbId) {
        return providersBackEndClient.getReviewsFor(movieImdbId);
    }

    public List<ProvidersWatchlistDto> getProvidersWatchlistItems(String phrase) {
        if (phrase == null || phrase.isEmpty()) {
            return watchlistBackEndClient.getProviderWatchlistItems();
        } else {
            return watchlistBackEndClient.getProviderWatchlistItemsBy(phrase);
        }
    }

    public void deleteItemFromProviderWatchlist(Long watchlistItemId) {
        watchlistBackEndClient.deleteItemFromProviderWatchlist(watchlistItemId);
    }

    public void addUpdateItemFromProviderWatchlist(ProvidersWatchlistDto providersWatchlistDto) {
        if (providersWatchlistDto.getId() != null) {
            watchlistBackEndClient.updateItemOnProviderWatchlist(providersWatchlistDto);
        } else {
            watchlistBackEndClient.addNewItemToProviderWatchlist(providersWatchlistDto);
        }
    }

    public List<String> getProviderTypes() {
        return watchlistBackEndClient.getProviderTypes();
    }

    public byte[] getWordCloudForPositiveReviewsOf(String imdbId) {
        return reviewAnalyticsBackEndClient.getPositiveReviewAnalyticsFor(imdbId);
    }

    public byte[] getWordCloudForNeutralReviewsOf(String imdbId) {
        return reviewAnalyticsBackEndClient.getNeutralReviewAnalyticsFor(imdbId);
    }

    public byte[] getWordCloudForNegativeReviewsOf(String imdbId) {
        return reviewAnalyticsBackEndClient.getNegativeReviewAnalyticsFor(imdbId);
    }

    public byte[] getReviewRatingsPieChartOf(String imdbId) {
        return reviewAnalyticsBackEndClient.getReviewRatingsPieChartFor(imdbId);
    }

    public byte[] getReviewRatingsDistributionOf(String imdbId) {
        return reviewAnalyticsBackEndClient.addReviewRatingsDistributionFor(imdbId);
    }

    public void prepareCachedImdbReviewsFor(String movieImdbId) {
        moviesBackEndClient.prepareCachedImdbReviewsFor(movieImdbId);
    }

    public String checkUserCountryName() {
        IpApiDto countryCheckResult = locationClient.checkCountryName();
        return countryCheckResult.getStatus().equals("success") ? countryCheckResult.getCountry() : "";
    }
}
