package com.maciej.checkflix.frontend.ui.moviereviewsview;

import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.Arrays;

@Route(value = "reviews", layout = MainLayout.class)
public class MovieReviewsView extends VerticalLayout implements HasUrlParameter<String>, BeforeLeaveObserver {

    private BackEndService backEndService;
    private String movieImdbId;

    public MovieReviewsView(BackEndService backEndService) {
        this.backEndService = backEndService;

        add(new H1("REVIEWS"));
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
            event.forwardTo(route, Arrays.asList(movieImdbId));
        }
    }
}
