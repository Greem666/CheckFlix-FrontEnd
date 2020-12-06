package com.maciej.checkflix.frontend.ui.moviedetailsview;

import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.AbstractMovieView;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.Registration;
import org.springframework.web.client.HttpServerErrorException;

@Route(value = "details", layout = MainLayout.class)
@PageTitle("Movie details")
public class MovieDetailsView extends AbstractMovieView {

    public MovieDetailsView(BackEndService backEndService) {
        super(backEndService);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        super.setParameter(event, parameter);
        removeAll();

        manageMovieDetailsDisplay();
        runBackgroundTaskForImdbReviewCache();
    }

    private void manageMovieDetailsDisplay() {
        MovieDetailsDisplay movieDetailsDisplay = new MovieDetailsDisplay(this.movieDetailsDto);
        add(movieDetailsDisplay);
    }

    private void runBackgroundTaskForImdbReviewCache() {
        //TODO: Not ideal, but head-starts the task of web scraping reviews from IMDB when the user is not looking at the
        // review analysis section just yet. Does not seem to be resistant to user hitting "Review analysis" anchor
        // before the process is done on the backend.
        Thread cacheImdbReviews = new Thread(() -> backEndService.prepareCachedImdbReviewsFor(this.movieImdbId));
        try {
            cacheImdbReviews.start();
        } catch (HttpServerErrorException e) {

        }
    }
}
