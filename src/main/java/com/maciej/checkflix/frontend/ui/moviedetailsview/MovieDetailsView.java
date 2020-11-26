package com.maciej.checkflix.frontend.ui.moviedetailsview;

import com.maciej.checkflix.frontend.domain.MovieDetails.MovieDetailsDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route(value = "details", layout = MainLayout.class)
@PageTitle("Movie details")
public class MovieDetailsView extends VerticalLayout implements HasUrlParameter<String> {

    private BackEndService backEndService;
    private String movieImdbId;
    private MovieDetailsDto movieDetails;

    public MovieDetailsView(BackEndService backEndService) {
        this.backEndService = backEndService;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        movieImdbId = parameter;
        manageMovieDetailsDisplay();
    }

    private void manageMovieDetailsDisplay() {
        movieDetails = backEndService.getMovieDetails(movieImdbId);
        MovieDetailsDisplay movieDetailsDisplay = new MovieDetailsDisplay(movieDetails);
        add(movieDetailsDisplay);
    }
}
