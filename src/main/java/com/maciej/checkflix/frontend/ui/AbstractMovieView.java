package com.maciej.checkflix.frontend.ui;

import com.maciej.checkflix.frontend.domain.MovieDetails.MovieDetailsDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

public abstract class AbstractMovieView extends VerticalLayout implements HasUrlParameter<String>, BeforeLeaveObserver {

    protected BackEndService backEndService;
    protected String movieImdbId;
    protected MovieDetailsDto movieDetailsDto;
    protected String movieTitle;

    public AbstractMovieView(BackEndService backEndService) {
        this.backEndService = backEndService;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        this.movieImdbId = parameter;

        this.movieDetailsDto = this.backEndService.getMovieDetails(this.movieImdbId);
        this.movieTitle = this.movieDetailsDto.getTitle() + " (" + this.movieDetailsDto.getYear() + ")";
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        String route = RouteConfiguration.forApplicationScope().getUrl((Class<? extends Component>) event.getNavigationTarget());
        if (!route.equals("")) {
            event.forwardTo(route, movieImdbId);
        }
    }
}
