package com.maciej.checkflix.frontend.ui;

import com.maciej.checkflix.frontend.ui.moviedetailsview.MovieDetailsView;
import com.maciej.checkflix.frontend.ui.movieprovidersview.MovieProvidersView;
import com.maciej.checkflix.frontend.ui.moviereviewsview.MovieReviewsView;
import com.maciej.checkflix.frontend.ui.moviesearchview.MovieSearchView;
import com.maciej.checkflix.frontend.ui.reviewsanalysisview.ReviewsAnalysisView;
import com.maciej.checkflix.frontend.ui.watchlistview.WatchListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
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
        Image checkflixLogo = new Image("img/logo.png", "Checkflix Logo");
        checkflixLogo.addClassName("checkflix-logo-image");

        HorizontalLayout header = new HorizontalLayout(checkflixLogo);
        header.addClassName("header");

        addToNavbar(header);
    }

    private void createDrawer() {
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.addClassName("left-drawer");

        RouterLink movieSearchLink = new RouterLink("Return to Movie search", MovieSearchView.class);
        movieSearchLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink detailsLink = new RouterLink("Details", MovieDetailsView.class);
        detailsLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink providersLink = new RouterLink("Providers", MovieProvidersView.class);
        providersLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink reviewsLink = new RouterLink("Reviews", MovieReviewsView.class);
        reviewsLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink reviewsAnalysisLink = new RouterLink("Reviews analysis (SLOW!)", ReviewsAnalysisView.class);
        reviewsAnalysisLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink watchListLink = new RouterLink("Watchlist", WatchListView.class);
        watchListLink.setHighlightCondition(HighlightConditions.sameLocation());

        vLayout.add(movieSearchLink, detailsLink, reviewsLink, reviewsAnalysisLink, providersLink, watchListLink);

        addToDrawer(
                vLayout
        );
    }
}
