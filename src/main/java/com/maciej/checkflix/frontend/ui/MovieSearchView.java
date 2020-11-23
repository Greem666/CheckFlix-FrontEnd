package com.maciej.checkflix.frontend.ui;

import com.maciej.checkflix.frontend.domain.MovieDto;
import com.maciej.checkflix.frontend.domain.MovieSearchFormDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@Route("")
@CssImport("./styles/movie-search-view-styles.css")
public class MovieSearchView extends VerticalLayout {

    private Paragraph paragraph;
    private BackEndService backEndService;

    public MovieSearchView(BackEndService backEndService) {
        this.backEndService = backEndService;

        Image checkflixLogo = new Image("img/logo.png", "Checkflix Logo");
        checkflixLogo.addClassName("checkflix-logo-image");
        checkflixLogo.setWidth("700px");

        MovieSearchForm movieSearchForm = new MovieSearchForm(backEndService.getMovieTypes());

        paragraph = new Paragraph();

        movieSearchForm.addListener(MovieSearchForm.SearchEvent.class, e -> {
            updateParagraphWithSearchResults(e.getMovieSearchFormDto());
        });

        movieSearchForm.addListener(MovieSearchForm.ClearEvent.class, e -> {
            clearParagraphAndForm(movieSearchForm);
        });

        add(checkflixLogo, movieSearchForm, paragraph);
        setHeightFull();
        setWidthFull();
//        setAlignSelf(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
//        setHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void updateParagraphWithSearchResults(MovieSearchFormDto searchCriteria) {
        List<MovieDto> movies = backEndService.getMoviesBy(
                searchCriteria.getTitle(),
                searchCriteria.getYear(),
                searchCriteria.getType());
        String results = movies.stream().map(MovieDto::toString).collect(Collectors.joining());
        paragraph.setText(results.isEmpty() ? "No movies found." : results);
    }

    private void clearParagraphAndForm(MovieSearchForm movieSearchForm) {
        movieSearchForm.clearForm();
        paragraph.setText("");
    }
}
