package com.maciej.checkflix.frontend.ui.watchlistview;

import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.AbstractMovieView;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "watchlist", layout = MainLayout.class)
public class WatchListView extends AbstractMovieView {

    public WatchListView(BackEndService backEndService) {
        super(backEndService);

        add(new H1("Watchlist"));
    }

}
