package com.maciej.checkflix.frontend.ui.moviedetailsview;

import com.maciej.checkflix.frontend.domain.MovieDetails.MovieDetailsDto;
import com.maciej.checkflix.frontend.ui.common.Divider;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MovieDetailsDisplay extends VerticalLayout {

    public MovieDetailsDisplay(MovieDetailsDto movieDetailsDto) {
        H1 title = new H1(movieDetailsDto.getTitle());

        Divider divider = new Divider();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        VerticalLayout verticalLayout = new VerticalLayout();
        Html year = new Html("<text>" + "<b>Year:</b> " + movieDetailsDto.getYear() + "</text>");
        Html rated = new Html("<text>" + "<b>Rated:</b> " + movieDetailsDto.getRated() + "</text>");
        Html releaseDate = new Html("<text>" + "<b>Release date:</b> " + movieDetailsDto.getReleased() + "</text>");
        Html runtime = new Html("<text>" + "<b>Runtime:</b> " + movieDetailsDto.getRuntime() + "</text>");
        Html genre = new Html("<text>" + "<b>Genre:</b> " + movieDetailsDto.getGenre() + "</text>");
        Html director = new Html("<text>" + "<b>Director:</b> " + movieDetailsDto.getDirector() + "</text>");
        Html writer = new Html("<text>" + "<b>Writer:</b> " + movieDetailsDto.getWriter() + "</text>");
        Html actors = new Html("<text>" + "<b>Actors:</b> " + movieDetailsDto.getActors() + "</text>");
        Html plot = new Html("<text>" + "<b>Plot:</b> " + movieDetailsDto.getPlot() + "</text>");
        Html country = new Html("<text>" + "<b>Country:</b> " + movieDetailsDto.getCountry() + "</text>");
        Html awards = new Html("<text>" + "<b>Awards:</b> " + movieDetailsDto.getAwards() + "</text>");
        verticalLayout.add(year, rated, releaseDate, runtime, genre, director, writer, actors, plot, country, awards);

        Image poster = new Image(movieDetailsDto.getPoster(), "N/A");
        poster.setWidth("400px");

        horizontalLayout.add(
                poster,
                verticalLayout
        );

        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        add(title, divider, horizontalLayout);
    }
}
