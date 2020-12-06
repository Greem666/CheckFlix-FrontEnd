package com.maciej.checkflix.frontend.ui.reviewsanalysisview;

import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.AbstractMovieView;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

@Route(value = "reviews_analysis", layout = MainLayout.class)
@CssImport("./styles/movie-review-analysis-view-styles.css")
@PageTitle("IMDB REVIEW ANALYSIS")
public class ReviewsAnalysisView extends AbstractMovieView {

    public ReviewsAnalysisView(BackEndService backEndService) {
        super(backEndService);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        super.setParameter(event, parameter);
        removeAll();

        setUpHeader();

        H2 quantitativeSection = new H2("1. Quantitative analysis");

        Div firstDiv = new Div();
        firstDiv.setText(String.format("Quantitative analysis of the most recent reviews reviews for %s.", movieTitle));

        HorizontalLayout firstRow = new HorizontalLayout(
                addReviewRatingsPieChart(),
                addReviewRatingsDistribution()
        );

        H2 wordCloudSection = new H2("2. Word cloud analysis");

        Div secondDiv = new Div();
        secondDiv.setText(
                String.format(
                        "Word clouds below show most frequently occurring 1-word tokens in the most " +
                                "recent reviews available for the %s.", movieTitle
                )
        );

        firstDiv.setText(String.format("Quantitative analysis of the most recent reviews reviews for %s.", movieTitle));

        HorizontalLayout secondRow = new HorizontalLayout(
                addPositiveReviewsWordCloud(),
                addNeutralReviewsWordCloud(),
                addNegativeReviewsWordCloud());

        add(quantitativeSection, firstDiv, firstRow, wordCloudSection, secondDiv, secondRow);
    }

    private void setUpHeader() {
        add(new H1("Reviews analysis - " + movieTitle));
    }

    private VerticalLayout addReviewRatingsPieChart() {
        return addChartFrame(
                backEndService.getReviewRatingsPieChartOf(movieImdbId),
                "Review ratings composition",
                "60%"
        );
    }

    private VerticalLayout addReviewRatingsDistribution() {
        return addChartFrame(
                backEndService.getReviewRatingsDistributionOf(movieImdbId),
                "Review ratings distribution",
                "60%");
    }

    private VerticalLayout addPositiveReviewsWordCloud() {
        return addChartFrame(
                backEndService.getWordCloudForPositiveReviewsOf(movieImdbId),
                "Positive reviews",
                "100%");
    }

    private VerticalLayout addNeutralReviewsWordCloud() {
        return addChartFrame(
                backEndService.getWordCloudForNeutralReviewsOf(movieImdbId),
                "Neutral reviews",
                "100%");
    }

    private VerticalLayout addNegativeReviewsWordCloud() {
        return addChartFrame(
                backEndService.getWordCloudForNegativeReviewsOf(movieImdbId),
                "Negative reviews",
                "100%");
    }

    private VerticalLayout addChartFrame(byte[] imageByteArray, String description, String maxWidth) {
        StreamResource resource = new StreamResource(
                description + ".png",
                () -> new ByteArrayInputStream(imageByteArray));
        Image image = new Image(resource, description);
        image.setMaxWidth(maxWidth);

        Span desc = new Span(description);
        desc.addClassName("image-description-text");

        VerticalLayout imageFrame = new VerticalLayout(image, desc);
        imageFrame.setAlignItems(Alignment.CENTER);

        return imageFrame;
    }
}
