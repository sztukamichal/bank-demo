package com.example.bankdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Bank demo application tests")
class BankDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("")
    void mockMvcInitilized() {
        assertThat(mockMvc).isNotNull();
    }

}

