package com.maciej.checkflix.frontend.ui.reviewsanalysisview;

import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.AbstractMovieView;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.LoadingIndicatorConfiguration;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

@Route(value = "reviews_analysis", layout = MainLayout.class)
public class ReviewsAnalysisView extends AbstractMovieView {

    public ReviewsAnalysisView(BackEndService backEndService) {
        super(backEndService);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        super.setParameter(event, parameter);

        setUpHeader();

        addWordCloud();
    }

    private void setUpHeader() {
        add(new H1("Reviews analysis - " + movieTitle));
    }

    private void addWordCloud() {
        StreamResource resource = new StreamResource(
                "wordcloud.png",
                () -> new ByteArrayInputStream(backEndService.getWordCloudForReviewsOf(movieImdbId)));
        Image image = new Image(resource, "wordcloud");

        add(image);
    }
}
