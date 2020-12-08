package com.maciej.checkflix.frontend.client;

import com.maciej.checkflix.frontend.domain.ClientLocation.IpApiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Component
public class LocationClient {

    @Autowired
    private RestTemplate restTemplate;

    public IpApiDto checkCountryName() {
        Optional<IpApiDto> countryResponse = Optional.ofNullable(restTemplate.getForObject(
                URI.create("http://ip-api.com/json/"), IpApiDto.class));

        return countryResponse.orElse(new IpApiDto());
    }
}
