package com.maciej.checkflix.frontend.ui.movieprovidersview.utils;

import static com.maciej.checkflix.frontend.ui.movieprovidersview.utils.SupportedProviders.NETFLIX;

public class ProviderLinkFactory {

    public static String getProviderLink(String providerName) {
        SupportedProviders provider = SupportedProviders.from(providerName);

        switch(provider) {
            case NETFLIX:
                return "https://www.netflix.com/";
            case DISNEY_PLUS:
            case DISNEY_LIFE:
                return "https://www.disneyplus.com/";
            case APPLE_ITUNES:
                return "https://www.apple.com/itunes/";
            case GOOGLE_PLAY_MOVIES:
                return "https://play.google.com/movies";
            case AMAZON_VIDEO:
                return "https://www.amazon.com/Amazon-Video/b?ie=UTF8&node=2858778011";
            case YOUTUBE:
                return "https://www.youtube.com";
            case FETCH_TV:
                return "https://www.fetchtv.com.au/";
            case MICROSOFT_STORE:
                return "https://www.microsoft.com/en-au/store/movies-and-tv";
            case QUICKFLIX_STORE:
                return "https://www.quickflix.com.au/";
            case AMAZON_PRIME_VIDEO:
                return "https://www.primevideo.com/";
            case FOXTEL_NOW:
                return "https://www.foxtel.com.au/now/index.html";
            case CHILI:
                return "https://pl.chili.com/filmy";
            case SKY_STORE:
                return "https://www.skystore.com/";
            case RAKUTEN_TV:
                return "https://www.rakuten.tv/";
            case HULU:
                return "https://www.hulu.com/";
            default:
                return "https://not-yet-implemented";
        }
    }
}
