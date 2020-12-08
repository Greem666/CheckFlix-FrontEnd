package com.maciej.checkflix.frontend.ui.watchlistview;

import com.maciej.checkflix.frontend.domain.watchlist.ProvidersWatchlistDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.AbstractMovieView;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.maciej.checkflix.frontend.ui.common.Divider;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "watchlist", layout = MainLayout.class)
@PageTitle("WATCHLIST")
public class WatchListView extends AbstractMovieView {

    private TextField filterField = new TextField();
    private Grid watchlist = new Grid(ProvidersWatchlistDto.class);
    private List<String> availableCountryList;
    private ProviderWatchlistForm watchListForm;
    private Button openFormDialogButton;
    private Dialog formDialogWindow;

    public WatchListView(BackEndService backEndService) {
        super(backEndService);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        super.setParameter(event, parameter);
        removeAll();

        H1 header = setUpHeader();
        Div description = addViewDescription();
        configureFilter();
        setUpForm();
        addOpenFormButton();
        setupGrid();

        add(header, description, new Divider(), filterField, openFormDialogButton, watchlist);
    }

    private Div addViewDescription() {
        return new Div(
                new Span("You can add current, or any other movie or tv show to a watchlist. " +
                        "We will send you an email with notification about chosen title becoming available with a " +
                        "given provider type in selected country.")
        );
    }

    private void addOpenFormButton() {
        openFormDialogButton = new Button("Add");

        VerticalLayout vl = new VerticalLayout(new H3("Watchlist"), watchListForm);
        vl.setAlignItems(Alignment.CENTER);
        formDialogWindow = new Dialog(vl);

        openFormDialogButton.addClickListener(event -> {
            watchListForm.setImdbId(movieImdbId);
            watchListForm.setCountry(backEndService.checkUserCountryName());
            formDialogWindow.open();
        });
    }

    private void setUpForm() {
        availableCountryList = backEndService.getSupportedCountryProvidersList();
        watchListForm = new ProviderWatchlistForm(backEndService);

        watchListForm.addListener(ProviderWatchlistForm.SaveEvent.class, e -> {
            updateExistingEntry(e.getProvidersWatchlistDto());
            formDialogWindow.close();
        });

        watchListForm.addListener(ProviderWatchlistForm.ClearEvent.class, e -> {
            watchListForm.clearForm();
            watchListForm.setSaveButtonToAdd();
            watchlist.deselectAll();
        });

        watchListForm.addListener(ProviderWatchlistForm.CloseEvent.class, e -> {
            watchListForm.clearForm();
            watchListForm.setSaveButtonToAdd();
            watchlist.deselectAll();
            formDialogWindow.close();
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
            formDialogWindow.open();
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
