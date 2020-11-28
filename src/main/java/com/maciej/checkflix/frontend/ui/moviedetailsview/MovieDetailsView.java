package com.maciej.checkflix.frontend.ui.moviedetailsview;

import com.maciej.checkflix.frontend.domain.MovieDetails.MovieDetailsDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.maciej.checkflix.frontend.ui.movieprovidersview.MovieProvidersView;
import com.maciej.checkflix.frontend.ui.moviesearchview.MovieSearchView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.RouteRegistry;

import java.util.*;

@Route(value = "details", layout = MainLayout.class)
@PageTitle("Movie details")
public class MovieDetailsView extends VerticalLayout implements HasUrlParameter<String>, BeforeLeaveObserver {

    private BackEndService backEndService;
    private String movieImdbId;
    private MovieDetailsDto movieDetails;

    public MovieDetailsView(BackEndService backEndService) {
        this.backEndService = backEndService;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        movieImdbId = parameter;
        manageMovieDetailsDisplay();
    }

    private void manageMovieDetailsDisplay() {
        movieDetails = backEndService.getMovieDetails(movieImdbId);
        MovieDetailsDisplay movieDetailsDisplay = new MovieDetailsDisplay(movieDetails);
        add(movieDetailsDisplay);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        String route = RouteConfiguration.forApplicationScope().getUrl((Class<? extends Component>) event.getNavigationTarget());
        if (!route.equals("")) {
            event.forwardTo(route, movieImdbId);
        }
    }
}
