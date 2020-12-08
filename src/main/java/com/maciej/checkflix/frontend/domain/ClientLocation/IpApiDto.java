package com.maciej.checkflix.frontend.domain.ClientLocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpApiDto {
    @JsonProperty("query")
    private String query;

    @JsonProperty("status")
    private String status;

    @JsonProperty("country")
    private String country;
}
