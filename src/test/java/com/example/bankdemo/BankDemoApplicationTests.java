package com.example.bankdemo;

import com.example.bankdemo.external.nbp.EuroRateUnavailable;
import com.example.bankdemo.external.nbp.NBPService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Bank demo application tests")
class BankDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NBPService nbpService;

    @Nested
    @DisplayName("Account controller should")
    class AccountControllerTest {
        @Test
        @DisplayName("given get call should return 404 if account with id does not exist")
        void givenNotExistingAccountIdShouldReturn404() throws Exception {
            //when
            var perform = mockMvc.perform(MockMvcRequestBuilders.get("/account/123/balance/EURO"));

            //then
            perform
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason("Account does not exist for id 123"));
        }

        @Test
        @DisplayName("given existing account id should return current balance")
        void givenExistingAccountIdShouldReturnCurrentBalance() throws Exception {
            //given
            given(nbpService.getEuroExchangeRate()).willReturn(4.5994);

            //when
            var perform = mockMvc.perform(MockMvcRequestBuilders.get("/account/1/balance/EURO"));

            //then
            perform
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"balance\":\"108,71 EUR\"}"));
        }

        @Test
        @DisplayName("given EuroUnavailableException should return message")
        void givenEuroUnavailableExceptionShouldReturnMessage() throws Exception {
            //given
            given(nbpService.getEuroExchangeRate()).willThrow(EuroRateUnavailable.class);

            //when
            var perform = mockMvc.perform(MockMvcRequestBuilders.get("/account/1/balance/EURO"));

            //then
            perform
                    .andExpect(status().isServiceUnavailable())
                    .andExpect(status().reason("NBP Service currently not available, please try later"));
        }

    }

}

