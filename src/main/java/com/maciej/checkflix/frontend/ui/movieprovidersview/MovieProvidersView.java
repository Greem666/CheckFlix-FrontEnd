package com.maciej.checkflix.frontend.ui.movieprovidersview;

import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.maciej.checkflix.frontend.ui.moviedetailsview.MovieDetailsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route(value = "providers", layout = MainLayout.class)
public class MovieProvidersView extends VerticalLayout implements HasUrlParameter<String>, BeforeLeaveObserver {

    private BackEndService backEndService;
    private String movieImdbId;

    public MovieProvidersView(BackEndService backEndService) {
        this.backEndService = backEndService;

        add(new H1("PROVIDERS"));
    }

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
