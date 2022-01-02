package com.example.bankdemo.external.nbp;

import com.example.bankdemo.external.nbp.dto.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NBPService {

    private static final String EURO_RATE_URL = "http://api.nbp.pl/api/exchangerates/rates/A/EUR?format=json";

    private final RestTemplate restTemplate;

    @Cacheable("euro-rate")
    public double getEuroExchangeRate() {
        var rate = Optional.ofNullable(fetchEuroRate().getBody());
        return rate.map(r -> CollectionUtils.lastElement(r.rates())).orElseThrow(EuroRateUnavailable::new).mid();
    }

    private ResponseEntity<Rate> fetchEuroRate() {
        return restTemplate.getForEntity(EURO_RATE_URL, Rate.class);
    }
}
