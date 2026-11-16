package com.example.securepass;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void infoEndpointReturnsServiceName() throws Exception {
        mockMvc.perform(get("/api/info"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("SecurePass API"));
    }

    @Test
    void checkEndpointReturnsRating() throws Exception {
        mockMvc.perform(post("/api/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"Passw0rd!2026\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.rating").value("Very Strong"));
    }

    @Test
    void generateRejectsInvalidLength() throws Exception {
        mockMvc.perform(get("/api/generate?length=3"))
               .andExpect(status().isBadRequest());
    }
}
