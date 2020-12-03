package com.maciej.checkflix.frontend.ui.moviesearchview;

import com.maciej.checkflix.frontend.domain.MovieSearch.MovieDto;
import com.maciej.checkflix.frontend.domain.MovieSearch.MovieSearchFormDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.moviedetailsview.MovieDetailsView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;

@Route("")
@PageTitle("CheckFlix movie search engine")
@CssImport("./styles/movie-search-view-styles.css")
public class MovieSearchView extends VerticalLayout {

    private BackEndService backEndService;
    private VerticalLayout searchResults;
    private Grid foundMoviesGrid = new Grid(MovieDto.class);

    public MovieSearchView(BackEndService backEndService) {
        this.backEndService = backEndService;

        Image checkflixLogo = new Image("img/logo.png", "Checkflix Logo");
        checkflixLogo.addClassName("checkflix-logo-image");
        checkflixLogo.setWidth("700px");

        MovieSearchForm movieSearchForm = new MovieSearchForm(backEndService.getMovieTypes());

        searchResults = new VerticalLayout();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(movieSearchForm);
        horizontalLayout.setWidthFull();
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        setupGrid();

        movieSearchForm.addListener(MovieSearchForm.SearchEvent.class, e -> {
            updateParagraphWithSearchResults(e.getMovieSearchFormDto());
        });

        movieSearchForm.addListener(MovieSearchForm.ClearEvent.class, e -> {
            clearParagraphAndForm(movieSearchForm);
        });

        add(checkflixLogo, horizontalLayout, foundMoviesGrid);
        setHeightFull();
        setWidthFull();
//        setAlignSelf(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
//        setHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void setupGrid() {
        foundMoviesGrid.removeAllColumns();

        foundMoviesGrid.addComponentColumn(v -> {
            Image poster = new Image(((MovieDto) v).getPoster(), "N/A");
            poster.setHeight("200px");
            return poster;
        }).setHeader("Poster");
        foundMoviesGrid.addColumn("title");
        foundMoviesGrid.addColumn("year");
        foundMoviesGrid.addColumn("type");
        foundMoviesGrid.setMinHeight("200px");

        foundMoviesGrid.addComponentColumn(v -> {
            RouterLink detailsLink = new RouterLink("Details", MovieDetailsView.class, ((MovieDto) v).getImdbID());
            return detailsLink;
        }).setHeader("Link");

        foundMoviesGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        SingleSelect<Grid<MovieDto>, MovieDto> movieSelect = foundMoviesGrid.asSingleSelect();
        movieSelect.addValueChangeListener(poster -> {
            try {
                Notification notification = new Notification(
                        "Clicked " + movieSelect.getValue().getTitle(),
                        3000,
                        Notification.Position.TOP_CENTER);
                notification.open();
            } catch (NullPointerException e) {
                // Do nothing
            }
        });

        foundMoviesGrid.setVisible(false);
    }

    private void updateParagraphWithSearchResults(MovieSearchFormDto searchCriteria) {
        List<MovieDto> movies = backEndService.getMoviesBy(
                searchCriteria.getTitle(),
                searchCriteria.getYear(),
                searchCriteria.getType());
        foundMoviesGrid.setItems(movies);
        foundMoviesGrid.setVisible(true);
    }

    private void clearParagraphAndForm(MovieSearchForm movieSearchForm) {
        movieSearchForm.clearForm();
        setupGrid();
        foundMoviesGrid.setItems(new ArrayList<>());
    }
}
