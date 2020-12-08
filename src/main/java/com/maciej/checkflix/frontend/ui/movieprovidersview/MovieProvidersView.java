package com.maciej.checkflix.frontend.ui.movieprovidersview;

import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.maciej.checkflix.frontend.domain.MovieProviders.CountryResultDto;
import com.maciej.checkflix.frontend.domain.MovieProviders.ProviderDetailsDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.AbstractMovieView;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.maciej.checkflix.frontend.ui.moviedetailsview.MovieDetailsView;
import com.maciej.checkflix.frontend.ui.movieprovidersview.utils.Divider;
import com.maciej.checkflix.frontend.ui.movieprovidersview.utils.ProviderLinkFactory;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(value = "providers", layout = MainLayout.class)
@CssImport("./styles/movie-providers-view.css")
public class MovieProvidersView extends AbstractMovieView {

    private ComboBox<String> availableCountries;
    private List<Details> detailsPanels = new ArrayList<>();

    public MovieProvidersView(BackEndService backEndService) {
        super(backEndService);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        super.setParameter(event, parameter);
        removeAll();

        setUpHeader();
        addSectionDescription();
        addCountrySelector();

        addDividingLine();

        addProviderPanels(backEndService.getLocalProviders(movieImdbId, availableCountries.getValue()));
    }

    private void setUpHeader() {
        H1 header = new H1("Providers - " + movieTitle);
        add(header);
    }

    private void addSectionDescription() {
        Div description = new Div(
                new Span("Click below to check availability of " + movieTitle + " in a country of your choice.")
        );
        add(description);
    }

    private void addDividingLine() {
        add(new Divider());
    }

    private void addCountrySelector() {
        List<String> availableCountryList = backEndService.getSupportedCountryProvidersList();
        availableCountries = new ComboBox<>();
        availableCountries.setItems(availableCountryList);

        String countryName = backEndService.checkUserCountryName();

        if (availableCountryList.contains(countryName)) {
            availableCountries.setValue(countryName);
        } else {
            availableCountries.setValue(availableCountryList.get(0));
        }

        availableCountries.setLabel("Country");

        availableCountries.addValueChangeListener(e -> {
            addProviderPanels(backEndService.getLocalProviders(movieImdbId, availableCountries.getValue()));
        });

        add(availableCountries);
    }

    private void addProviderPanels(CountryResultDto availableProviders) {
        cleanViewOfDetailsPanels();
        addProviderPanel(availableProviders.getFlatrate(), "flatrate");
        addProviderPanel(availableProviders.getBuy(), "buy");
        addProviderPanel(availableProviders.getRent(), "rent");
    }

    private void cleanViewOfDetailsPanels() {
        for (Details detailsPanel : detailsPanels) {
            remove(detailsPanel);
        }
    }

    private void addProviderPanel(List<ProviderDetailsDto> providersList, String providerType) {
        Details providerPanel = new Details();
        providerPanel.setSummaryText(providerType);

        if (providersList != null) {
            HorizontalLayout providersLogoBelt = new HorizontalLayout();
            providersLogoBelt.addClassName("providers-logo-belt");
            for (ProviderDetailsDto provider: providersList) {
                VerticalLayout verticalLayout = new VerticalLayout();

                Image providerLogoImage = new Image(provider.getLogoPath(), provider.getProviderName());
                providerLogoImage.addClassName("provider-logo-image");

                Button providerLink = new Button(
                        provider.getProviderName()
                );
                providerLink.addClassName("provider-link-button");
                providerLink.addClickListener(e -> {
                    UI.getCurrent().getPage().open(
                            ProviderLinkFactory.getProviderLink(provider.getProviderName()),
                            "_blank");
                });

                verticalLayout.add(
                        providerLogoImage,
                        providerLink
                );
                verticalLayout.setAlignItems(Alignment.CENTER);

                providersLogoBelt.add(verticalLayout);
                providersLogoBelt.setDefaultVerticalComponentAlignment(Alignment.CENTER);
            }
            providerPanel.addContent(providersLogoBelt);
        } else {
            providerPanel.addContent(new Span("Not available in chosen country."));
        }

        providerPanel.setOpened(true);
        providerPanel.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);

        add(providerPanel);

        detailsPanels.add(providerPanel);
    }
}
