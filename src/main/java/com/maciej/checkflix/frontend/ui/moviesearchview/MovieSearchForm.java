package com.maciej.checkflix.frontend.ui.moviesearchview;

import com.maciej.checkflix.frontend.domain.MovieSearch.MovieSearchFormDto;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class MovieSearchForm extends FormLayout {
    TextField movieNameField = new TextField();
    TextField movieYearField = new TextField();
    ComboBox<String> movieTypeField = new ComboBox<>();

    Button searchButton = new Button("Search");
    Button clearButton = new Button("Clear");

    Binder<MovieSearchFormDto> movieSearchFormDtoBinder = new Binder<>(MovieSearchFormDto.class);

    public MovieSearchForm(List<String> typeOptions) {
        addClassName("movie-search-form");
        bindFields();
        searchButton.setEnabled(false);

        HorizontalLayout searchFieldsHBar = new HorizontalLayout();
        searchFieldsHBar.add(
                createMovieNameSearchField(),
                createMovieYearSearchField(),
                createMovieTypeComboBox(typeOptions)
        );

        HorizontalLayout buttonsHBar = new HorizontalLayout();
        buttonsHBar.add(
                searchButton,
                clearButton
        );
        buttonsHBar.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        buttonsHBar.setWidth("600px");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(searchFieldsHBar, buttonsHBar);
        verticalLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        add(verticalLayout);
        this.setSizeUndefined();

        movieNameField.addKeyPressListener(Key.ENTER, event -> {
            fireEvent(new SearchEvent(this, movieSearchFormDtoBinder.getBean()));
        });

        movieYearField.addKeyPressListener(Key.ENTER, event -> {
            fireEvent(new SearchEvent(this, movieSearchFormDtoBinder.getBean()));
        });

        searchButton.addClickListener(e -> {
            fireEvent(new SearchEvent(this, movieSearchFormDtoBinder.getBean()));
        });

        clearButton.addClickListener(e -> {
            fireEvent(new ClearEvent(this, movieSearchFormDtoBinder.getBean()));
        });

        movieNameField.addValueChangeListener(e -> {
            searchButton.setEnabled(movieNameField.getValue().length() > 0);
        });
    }

    private TextField createMovieNameSearchField() {
        movieNameField.setPlaceholder("movie name");

        return movieNameField;
    }

    private TextField createMovieYearSearchField() {
        movieYearField.setPlaceholder("year");

        return movieYearField;
    }

    private ComboBox<String> createMovieTypeComboBox(List<String> typeOptions) {
        movieTypeField.setPlaceholder("movie type");
        movieTypeField.setItems(typeOptions);
        movieTypeField.setValue(typeOptions.get(0));

        return movieTypeField;
    }

    private void bindFields() {
        movieSearchFormDtoBinder.forField(movieNameField)
                .bind("title");
        movieSearchFormDtoBinder.forField(movieYearField)
                .bind("year");
        movieSearchFormDtoBinder.forField(movieTypeField)
                .bind("type");
        movieSearchFormDtoBinder.setBean(new MovieSearchFormDto());
    }

    public void clearForm() {
        this.movieNameField.setValue("");
        this.movieYearField.setValue("");
        this.movieTypeField.setValue("");
    }

    public static abstract class MovieSearchFormEvent extends ComponentEvent<MovieSearchForm> {
        private MovieSearchFormDto movieSearchFormDto;

        protected MovieSearchFormEvent(MovieSearchForm source, MovieSearchFormDto movieSearchFormDto) {
            super(source, false);
            this.movieSearchFormDto = movieSearchFormDto;
        }

        public MovieSearchFormDto getMovieSearchFormDto() {
            return movieSearchFormDto;
        }
    }

    public static class SearchEvent extends MovieSearchFormEvent {
        public SearchEvent(MovieSearchForm source, MovieSearchFormDto movieSearchFormDto) {
            super(source, movieSearchFormDto);
        }
    }

    public static class ClearEvent extends MovieSearchFormEvent {
        public ClearEvent(MovieSearchForm source, MovieSearchFormDto movieSearchFormDto) {
            super(source, movieSearchFormDto);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
