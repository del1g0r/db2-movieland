package com.study.movieland.dao.rest;

import com.study.movieland.dao.CurrencyDao;
import com.study.movieland.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collection;

@Repository
public class RestCurrencyDao implements CurrencyDao {

    private static final HttpEntity<?> REQUEST_ENTITY = new HttpEntity<>(
            new HttpHeaders() {{
                setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            }});

    private RestTemplate restTemplate;
    private String url;

    @Override
    public Currency get(String code) {
        return restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("valcode", code)
                        .queryParam("json")
                        .toUriString(),
                HttpMethod.GET,
                REQUEST_ENTITY,
                Currency.class
        ).getBody();
    }

    @Override
    public Collection<Currency> getAll() {
        return restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("json")
                        .toUriString(),
                HttpMethod.GET,
                REQUEST_ENTITY,
                new ParameterizedTypeReference<Collection<Currency>>() {
                }
        ).getBody();
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${currency.serviceUrl:https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange}")
    public void setUrl(String url) {
        this.url = url;
    }
}