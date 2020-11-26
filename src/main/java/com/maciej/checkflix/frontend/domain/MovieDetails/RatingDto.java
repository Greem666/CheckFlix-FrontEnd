package com.maciej.checkflix.frontend.domain.MovieDetails;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingDto {
    @JsonProperty("Source")
    public String source;

    @JsonProperty("Value")
    public String value;
}
