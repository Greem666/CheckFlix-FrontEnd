package com.maciej.checkflix.frontend.ui.watchlistview;

import com.maciej.checkflix.frontend.domain.MovieDetails.MovieDetailsDto;
import com.maciej.checkflix.frontend.domain.watchlist.ProvidersWatchlistDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.shared.Registration;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.DoubleToLongFunction;

public class ProviderWatchlistForm extends FormLayout {
    IntegerField id = new IntegerField();
    TextField username = new TextField();
    TextField email = new TextField();
    TextField imdbId = new TextField();
    TextField movieName = new TextField();
    ComboBox<String> country = new ComboBox<>();
    ComboBox<String> providerType = new ComboBox<>();

    Button saveButton = new Button("Save");
    Button clearButton = new Button("Clear");

    Binder<ProvidersWatchlistDto> providerWatchlistBinder = new Binder<>(ProvidersWatchlistDto.class);

    BackEndService backEndService;

    List<String> availableCountries;
    List<String> providerTypes;

    public ProviderWatchlistForm(BackEndService backEndService) {
        this.backEndService = backEndService;

        addClassName("provider-watchlist-form");

        VerticalLayout searchFieldsVBar = new VerticalLayout();
        searchFieldsVBar.add(
                createIdField(),
                createMovieNameField(),
                createImdbIdField(),
                createUsernameField(),
                createEmailField(),
                createCountryField(),
                createProviderTypesField()
        );

        bindFields();

        HorizontalLayout buttonsHBar = new HorizontalLayout();
        buttonsHBar.add(
                saveButton,
                clearButton
        );
        buttonsHBar.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(searchFieldsVBar, buttonsHBar);
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(verticalLayout);

        saveButton.addClickListener(e -> {
            fireEvent(new SaveEvent(this, providerWatchlistBinder.getBean()));
        });

        clearButton.addClickListener(e -> {
            fireEvent(new ClearEvent(this, providerWatchlistBinder.getBean()));
        });
    }

    private Component createIdField() {
        id.setVisible(false);
        return id;
    }

    private Component createMovieNameField() {
        movieName.setLabel("Movie name");
        movieName.setPlaceholder("movie name");
        movieName.setEnabled(false);

        return movieName;
    }

    private Component createImdbIdField() {
        imdbId.setLabel("Imdb ID");

        imdbId.addValueChangeListener(e -> {
            MovieDetailsDto movieSearchResult = backEndService.getMovieDetails(imdbId.getValue());
            if (movieSearchResult.getTitle() != null) {
                movieName.setValue(movieSearchResult.getTitle());
            } else {
                movieName.setValue("No match found!");
            }
        });
        return imdbId;
    }

    private Component createUsernameField() {
        username.setLabel("Username");
        username.setPlaceholder("username");

        return username;
    }

    private Component createEmailField() {
        email.setLabel("E-mail address");
        email.setPlaceholder("email");

        return email;
    }

    private Component createCountryField() {
        country.setLabel("Provider country");
        country.setPlaceholder("country");

        availableCountries = backEndService.getSupportedCountryProvidersList();

        country.setItems(availableCountries);

        return country;
    }

    private Component createProviderTypesField() {
        providerType.setLabel("Provider type");
        providerType.setPlaceholder("provider type");

        providerTypes = backEndService.getProviderTypes();

        providerType.setItems(providerTypes);

        return providerType;
    }

    private void bindFields() {
        providerWatchlistBinder.forField(id)
                .bind("id");
        providerWatchlistBinder.forField(username)
                .withValidator(username -> username.length() > 3, "Name must be over 3 letters long!")
                .bind("username");
        providerWatchlistBinder.forField(email)
                .withValidator(new EmailValidator("Enter a correct email address!"))
                .bind("email");
        providerWatchlistBinder.forField(imdbId)
                .withValidator(imdbId -> imdbId.length() > 8, "Imdb ID is expected to have 9 characters.")
                .bind("imdbId");
        providerWatchlistBinder.forField(movieName)
                .withValidator(
                        movieName -> movieName != null && !movieName.equals("No match found!"),
                        "Movie name is not correct!")
                .bind("movieName");
        providerWatchlistBinder.forField(country)
                .withValidator(
                        country -> country != null && availableCountries.contains(country),
                        "Country selection incorrect!")
                .bind("country");
        providerWatchlistBinder.setBean(new ProvidersWatchlistDto());
        providerWatchlistBinder.forField(providerType)
                .withValidator(providerType -> providerType != null && providerTypes.contains(providerType),
                        "Provider type not correct!")
                .bind("providerType");

        providerWatchlistBinder.addStatusChangeListener(e -> saveButton.setEnabled(providerWatchlistBinder.isValid()));
    }

    public void clearForm() {
        this.id.setValue(null);
        this.username.setValue("");
        this.email.setValue("");
        this.imdbId.setValue("");
        this.movieName.setValue("");
        this.country.setValue("");

        bindFields();

//        binder.valid
    }

    public void setSaveButtonToAdd() {
        saveButton.setText("Add");
    }

    public void setSaveButtonToUpdate() {
        saveButton.setText("Update");
    }

    public void setWatchlistEntry(ProvidersWatchlistDto watchlistEntry) {
        providerWatchlistBinder.setBean(watchlistEntry);
    }

    public void setImdbId(String imdbId) {
        this.imdbId.setValue(imdbId);
    }

    public static abstract class ProviderWatchlistFormEvent extends ComponentEvent<ProviderWatchlistForm> {
        private ProvidersWatchlistDto providersWatchlistDto;

        protected ProviderWatchlistFormEvent(ProviderWatchlistForm source, ProvidersWatchlistDto providersWatchlistDto) {
            super(source, false);
            this.providersWatchlistDto = providersWatchlistDto;
        }

        public ProvidersWatchlistDto getProvidersWatchlistDto() {
            return providersWatchlistDto;
        }
    }

    public static class SaveEvent extends ProviderWatchlistFormEvent {
        public SaveEvent(ProviderWatchlistForm source, ProvidersWatchlistDto providersWatchlistDto) {
            super(source, providersWatchlistDto);
        }
    }

    public static class ClearEvent extends ProviderWatchlistFormEvent {
        public ClearEvent(ProviderWatchlistForm source, ProvidersWatchlistDto providersWatchlistDto) {
            super(source, providersWatchlistDto);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
