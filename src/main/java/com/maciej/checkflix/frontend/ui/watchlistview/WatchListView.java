package com.maciej.checkflix.frontend.ui.watchlistview;

import com.maciej.checkflix.frontend.domain.MovieSearch.MovieDto;
import com.maciej.checkflix.frontend.domain.MovieSearch.MovieSearchFormDto;
import com.maciej.checkflix.frontend.domain.watchlist.ProvidersWatchlistDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.AbstractMovieView;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.maciej.checkflix.frontend.ui.moviedetailsview.MovieDetailsView;
import com.maciej.checkflix.frontend.ui.moviereviewsview.MovieReviewsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;

import java.util.List;

@Route(value = "watchlist", layout = MainLayout.class)
@PageTitle("WATCHLIST")
public class WatchListView extends AbstractMovieView {

    private TextField filterField = new TextField();
    private Grid watchlist = new Grid(ProvidersWatchlistDto.class);
    private List<String> availableCountryList;
    private ProviderWatchlistForm watchListForm;

    public WatchListView(BackEndService backEndService) {
        super(backEndService);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        super.setParameter(event, parameter);
        removeAll();

        H1 header = setUpHeader();
        configureFilter();
        setUpForm();
        setupGrid();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(watchlist, watchListForm);
        horizontalLayout.setWidthFull();
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        add(header, filterField, horizontalLayout);
    }

    private void setUpForm() {
        availableCountryList = backEndService.getSupportedCountryProvidersList();
        watchListForm = new ProviderWatchlistForm(backEndService);

        watchListForm.addListener(ProviderWatchlistForm.SaveEvent.class, e -> {
            updateExistingEntry(e.getProvidersWatchlistDto());
        });

        watchListForm.addListener(ProviderWatchlistForm.ClearEvent.class, e -> {
            watchListForm.clearForm();
            watchListForm.setSaveButtonToAdd();
            watchlist.deselectAll();
        });

        watchListForm.setImdbId(movieImdbId);
    }

    private void updateExistingEntry(ProvidersWatchlistDto providersWatchlistDto) {
        backEndService.addUpdateItemFromProviderWatchlist(providersWatchlistDto);
        updateWatchlist();
        watchListForm.clearForm();
    }

    private H1 setUpHeader() {
        return new H1("Watchlist");
    }

    private void configureFilter() {
        filterField.setPlaceholder("Filter by any property...");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(e -> updateWatchlist());
    }

    private void setupGrid() {
        watchlist.addClassName("providers-watchlist-grid");

        watchlist.removeAllColumns();
        addColumns();

        watchlist.setSelectionMode(Grid.SelectionMode.SINGLE);
        watchlist.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        watchListForm.setSaveButtonToAdd();
        watchlist.asSingleSelect().addValueChangeListener(evt -> {
            editEntry((ProvidersWatchlistDto) evt.getValue());
            watchListForm.setSaveButtonToUpdate();
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(watchlist);

        updateWatchlist();
    }

    private void editEntry(ProvidersWatchlistDto value) {
        if (value != null) {
            watchListForm.setWatchlistEntry(value);
            watchListForm.setSaveButtonToAdd();
        }
    }

    private void addColumns() {
        watchlist.addComponentColumn(v -> new Anchor(
                "details/" + ((ProvidersWatchlistDto)v).getImdbId(),
                ((ProvidersWatchlistDto)v).getMovieName())
        ).setHeader("Movie Name");

        watchlist.addColumn("username");
        watchlist.addColumn("email");
        watchlist.addColumn("country");
        watchlist.addColumn("providerType");

        watchlist.addComponentColumn(v -> {

            Button delete = new Button("Delete");
            delete.addClickListener(e -> {
                long id = (long)((ProvidersWatchlistDto)v).getId();
                backEndService.deleteItemFromProviderWatchlist(id);
                updateWatchlist();
                watchListForm.clearForm();
            });

            return delete;
        });
    }

    private void updateWatchlist() {
        List<ProvidersWatchlistDto> watchlistItems = backEndService.getProvidersWatchlistItems(filterField.getValue());
        watchlist.setItems(watchlistItems);
        watchlist.setVisible(true);
    }
}
