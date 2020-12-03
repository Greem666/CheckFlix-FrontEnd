package com.maciej.checkflix.frontend.domain.MovieProviders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryResultDto {

    @JsonProperty("link")
    private String link;

    @JsonProperty("flatrate")
    private List<ProviderDetailsDto> flatrate;

    @JsonProperty("rent")
    private List<ProviderDetailsDto> rent;

    @JsonProperty("buy")
    private List<ProviderDetailsDto> buy;
}
