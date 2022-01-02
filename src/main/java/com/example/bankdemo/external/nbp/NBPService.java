package com.example.bankdemo.external.nbp;

import com.example.bankdemo.external.nbp.dto.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NBPService {

    private static final String EURO_RATE_URL = "http://api.nbp.pl/api/exchangerates/rates/A/EUR?format=json";

    private final RestTemplate restTemplate;

    public double getEuroExchangeRate() {
        var rate = restTemplate.getForEntity(EURO_RATE_URL, Rate.class).getBody();
        var rateStamp = Optional.ofNullable(CollectionUtils.lastElement(rate.rates()));
        return rateStamp.orElseThrow(EuroRateUnavailable::new).mid();
    }
}
