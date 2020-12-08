package com.maciej.checkflix.frontend.ui.common;

import com.vaadin.flow.component.html.Span;

public class Divider extends Span {

    public Divider() {
        getStyle().set("background-color", "#757575");
        getStyle().set("flex", "0 0 2px");
        getStyle().set("align-self", "stretch");
    }
}
