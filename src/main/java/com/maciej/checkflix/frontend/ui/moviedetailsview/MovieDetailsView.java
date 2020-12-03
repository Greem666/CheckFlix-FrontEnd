package com.maciej.checkflix.frontend.ui.moviedetailsview;

import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.AbstractMovieView;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.vaadin.flow.router.*;

@Route(value = "details", layout = MainLayout.class)
@PageTitle("Movie details")
public class MovieDetailsView extends AbstractMovieView {

    public MovieDetailsView(BackEndService backEndService) {
        super(backEndService);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        super.setParameter(event, parameter);

        manageMovieDetailsDisplay();
    }

    private void manageMovieDetailsDisplay() {
        MovieDetailsDisplay movieDetailsDisplay = new MovieDetailsDisplay(this.movieDetailsDto);
        add(movieDetailsDisplay);
    }
}
