package com.maciej.checkflix.frontend.ui.movieprovidersview.utils;

public enum SupportedProviders {
    NETFLIX("Netflix"), DISNEY_PLUS("Disney Plus"),
    APPLE_ITUNES("Apple iTunes"), GOOGLE_PLAY_MOVIES("Google Play Movies"),
    AMAZON_VIDEO("Amazon Video"), YOUTUBE("YouTube"),
    FETCH_TV("Fetch TV"), MICROSOFT_STORE("Microsoft Store"),
    QUICKFLIX_STORE("Quickflix Store"), AMAZON_PRIME_VIDEO("Amazon Prime Video"),
    FOXTEL_NOW("Foxtel Now"), CHILI("Chili"),
    SKY_STORE("Sky Store"), RAKUTEN_TV("Rakuten TV"),
    HULU("Hulu"), DISNEY_LIFE("DisneyLife"), DEFAULT("default");

    private String providerName;

    SupportedProviders(String providerName) {
        this.providerName = providerName;
    }

    public static SupportedProviders from(String text) {
        for (SupportedProviders code : SupportedProviders.values()) {
            if (code.providerName.equalsIgnoreCase(text)) {
                return code;
            }
        }
        return DEFAULT;
    }
}
