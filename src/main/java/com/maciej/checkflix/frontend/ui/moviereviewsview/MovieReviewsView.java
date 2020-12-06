package com.maciej.checkflix.frontend.ui.moviereviewsview;

import com.maciej.checkflix.frontend.domain.MovieProviders.CountryResultDto;
import com.maciej.checkflix.frontend.domain.MovieProviders.ProviderDetailsDto;
import com.maciej.checkflix.frontend.domain.MovieReviews.ReviewResultDto;
import com.maciej.checkflix.frontend.service.BackEndService;
import com.maciej.checkflix.frontend.ui.AbstractMovieView;
import com.maciej.checkflix.frontend.ui.MainLayout;
import com.maciej.checkflix.frontend.ui.movieprovidersview.utils.ProviderLinkFactory;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.List;
import java.util.Optional;

@Route(value = "reviews", layout = MainLayout.class)
@PageTitle("TMDB REVIEWS")
public class MovieReviewsView extends AbstractMovieView {

    public MovieReviewsView(BackEndService backEndService) {
        super(backEndService);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        super.setParameter(event, parameter);
        removeAll();

        setUpHeader();
        addReviewPanels(backEndService.getReviewsFor(movieImdbId));
    }

    private void setUpHeader() {
        add(new H1("TMDB Reviews - " + movieTitle));
    }

    private void addReviewPanels(List<ReviewResultDto> reviewList) {
        for (ReviewResultDto review: reviewList) {
            addReviewPanel(review);
        }
    }

    private void addReviewPanel(ReviewResultDto review) {
        Details reviewPanel = new Details();
        reviewPanel.setSummaryText("Review by " + review.getAuthor());

        VerticalLayout reviewColumn = new VerticalLayout();
        reviewColumn.addClassName("review-details-block");

        reviewColumn.add(new Html(
                "<text>" +
                        "<b>Date:</b> " +
                        Optional.ofNullable(review.getCreatedAt()).map(e -> e.split("T")[0]).orElse("-") +
                        "</text>"));
        reviewColumn.add(new Html(
                "<text>" +
                        "<b>Rating:</b> " +
                        Optional.ofNullable(review.getAuthorDetails().getRating()).orElse("-") +
                        "</text>"));
        reviewColumn.add(new Html(
                "<text>" +
                        "<b>Review:</b> " +
                        Optional.ofNullable(review.getContent()).orElse("-") +
                        "</text>"));

        reviewPanel.addContent(reviewColumn);

        reviewPanel.setOpened(true);
        reviewPanel.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);

        add(reviewPanel);
    }
}
