package com.example.bankdemo.external.nbp;

import com.example.bankdemo.external.nbp.dto.Rate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@DisplayName("NBP Service")
@ExtendWith(MockitoExtension.class)
class NBPServiceTest {

    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/EUR?format=json";

    @Mock
    private RestTemplate restTemplate;
    private NBPService sut;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        sut = new NBPService(restTemplate);
    }

    @Test
    @DisplayName("given successful response should return euro rate")
    void givenOkResponseShouldReturnEuroExchangeRate() throws IOException {
        //given
        var rate = objectMapper.reader().readValue("{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"254/A/NBP/2021\",\"effectiveDate\":\"2021-12-31\",\"mid\":4.5994}]}", Rate.class);
        mockRateResponse(rate);

        //when
        var euroExchangeRate = sut.getEuroExchangeRate();

        //then
        assertThat(euroExchangeRate).isEqualTo(4.5994);
    }

    @Test
    @DisplayName("given empty rates should throw RateUnavailableException")
    void givenEmptyRatesShouldThrowEuroRateUnavailableException() throws IOException {
        //given
        var rate = objectMapper.reader().readValue("{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[]}", Rate.class);
        mockRateResponse(rate);

        //then
        assertThatThrownBy(() -> sut.getEuroExchangeRate()).isInstanceOf(EuroRateUnavailable.class);
    }

    private BDDMockito.BDDMyOngoingStubbing<ResponseEntity<Rate>> mockRateResponse(Rate rate) {
        return given(restTemplate.getForEntity(NBP_API_URL, Rate.class)).willReturn(ResponseEntity.ok(rate));
    }
}
