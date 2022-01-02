package com.example.bankdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Bank demo application tests")
class BankDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    @Nested
    @DisplayName("Account controller should")
    class AccountControllerTest {
        @Test
        @DisplayName("given get call should return ok status")
        void shouldReturnOKResponseOnGetCall() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/account"))
                    .andExpect(status().isOk());
        }

    }

}

