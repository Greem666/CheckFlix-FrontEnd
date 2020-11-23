package com.maciej.checkflix.frontend.service;

import com.maciej.checkflix.frontend.client.BackEndClient;
import com.maciej.checkflix.frontend.domain.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackEndService {

    @Autowired
    private BackEndClient backEndClient;

    public List<MovieDto> getMoviesBy(String name, String year, String type) {
        return backEndClient.getMoviesBy(name, year, type);
    }

    public List<String> getMovieTypes() {
        return backEndClient.getMovieTypes();
    }
}
