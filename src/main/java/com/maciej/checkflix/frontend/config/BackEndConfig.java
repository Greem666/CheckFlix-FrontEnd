package com.maciej.checkflix.frontend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BackEndConfig {
    @Value("${backend.api.url}")
    private String backEndUrl;

    @Value("${backend.api.port}")
    private String port;
}
