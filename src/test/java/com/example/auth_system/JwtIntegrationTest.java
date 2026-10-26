
package com.example.auth_system;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class JwtIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registeredUserCanLoginAndAccessDashboard() throws Exception {
        String email = "test-" + UUID.randomUUID()
                + "@example.com";

        String password = "TestPass123!";

        String registrationJson = objectMapper.writeValueAsString(
                new RegistrationPayload(
                        "Integration Test User",
                        email,
                        password
                )
        );

        // 1. Register a new test user.
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email));

        String loginJson = objectMapper.writeValueAsString(
                new LoginPayload(email, password)
        );

        // 2. Login and receive the real signed JWT.
        String loginResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode response = objectMapper.readTree(loginResponse);
        String token = response.get("accessToken").asText();

        // 3. Use the token to access the protected dashboard.
        mockMvc.perform(get("/api/dashboard")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.message")
                        .value("Welcome to your dashboard!"));
    }

    @Test
    void invalidBearerTokenCannotAccessDashboard() throws Exception {
        mockMvc.perform(get("/api/dashboard")
                        .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }

    private record RegistrationPayload(
            String name,
            String email,
            String password
    ) {}

    private record LoginPayload(
            String email,
            String password
    ) {}
}
