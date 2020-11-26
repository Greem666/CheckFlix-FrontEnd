package com.maciej.checkflix.frontend.domain.MovieSearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearchFormDto {
    private String title;
    private String year;
    private String type;
}
