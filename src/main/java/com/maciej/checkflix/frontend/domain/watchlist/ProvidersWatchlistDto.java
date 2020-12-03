package com.maciej.checkflix.frontend.domain.watchlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvidersWatchlistDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("imdbId")
    private String imdbId;

    @JsonProperty("movieName")
    private String movieName;

    @JsonProperty("country")
    private String country;

    @JsonProperty("providerType")
    private String providerType;
}
