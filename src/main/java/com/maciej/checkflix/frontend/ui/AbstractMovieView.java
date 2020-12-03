package com.maciej.checkflix.frontend.ui.movieprovidersview;

import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.maciej.checkflix.frontend.ui.moviedetailsview.MovieDetailsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route(value = "providers", layout = MainLayout.class)
public class MovieProvidersView extends VerticalLayout implements HasUrlParameter<String>, BeforeLeaveObserver {

    private BackEndService backEndService;
    private String movieImdbId;

    public MovieProvidersView(BackEndService backEndService) {
        this.backEndService = backEndService;

        H1 header = new H1("PROVIDERS");

//        Accordion availableProviderTypes = new Accordion();

        Details streaming = new Details("Streaming", new Span("Test streaming"));
        streaming.setOpened(true);
        streaming.addThemeVariants(DetailsVariant.FILLED, DetailsVariant.REVERSE);

        Details rent = new Details("Rent", new Span("Test rent"));
        rent.setOpened(true);
        rent.addThemeVariants(DetailsVariant.FILLED, DetailsVariant.REVERSE);

        Details buy = new Details("Buy", new Span("Test buy"));
        buy.setOpened(true);
        buy.addThemeVariants(DetailsVariant.FILLED, DetailsVariant.REVERSE);

        add(header, streaming, rent, buy);
    }

//    private void addProviderPanel(ProvidersDto providersDto) {
//        Details providerPanel = new Details("Streaming", new Span("Test streaming"));
//        providerPanel.setOpened(true);
//        providerPanel.addThemeVariants(DetailsVariant.FILLED, DetailsVariant.REVERSE);
//        add(providerPanel);
//    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        this.movieImdbId = parameter;
        add(new H2(movieImdbId));
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        String route = RouteConfiguration.forApplicationScope().getUrl((Class<? extends Component>) event.getNavigationTarget());
        if (!route.equals("")) {
            event.forwardTo(route, movieImdbId);
        }
    }
}
