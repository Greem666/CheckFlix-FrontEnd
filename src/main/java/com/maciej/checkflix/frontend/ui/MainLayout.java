package com.maciej.checkflix.frontend.ui;

import com.maciej.checkflix.frontend.ui.movieprovidersview.MovieProvidersView;
import com.maciej.checkflix.frontend.ui.moviereviewsview.MovieReviewsView;
import com.maciej.checkflix.frontend.ui.moviesearchview.MovieSearchView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/main-layout-style.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Checkflix");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.addClassName("header");
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.addClassName("left-drawer");

        RouterLink movieSearchLink = new RouterLink("Return to Movie search", MovieSearchView.class);
        movieSearchLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink providersLink = new RouterLink("Providers", MovieProvidersView.class);
        providersLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink reviewsLink = new RouterLink("Reviews", MovieReviewsView.class);
        reviewsLink.setHighlightCondition(HighlightConditions.sameLocation());

        vLayout.add(movieSearchLink, providersLink, reviewsLink);

        addToDrawer(
                vLayout
        );
    }
}
