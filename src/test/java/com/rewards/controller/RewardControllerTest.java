package com.rewards.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.model.RewardResponse;
import com.rewards.service.RewardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The type Reward controller test.
 */
@WebMvcTest(RewardController.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test get rewards.
     *
     * @throws Exception the exception
     */
    @Test
    void testGetRewards() throws Exception {

        RewardResponse response =
                RewardResponse.builder()
                        .customerId(101L)
                        .customerName("John")
                        .monthlyRewards(
                                Map.of("JANUARY", 115)
                        )
                        .totalRewards(115)
                        .build();

        Mockito.when(rewardService.calculateRewards())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/rewards")
                        .contentType(
                                MediaType.APPLICATION_JSON
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                        "$[0].customerName")
                        .value("John"))
                .andExpect(jsonPath(
                        "$[0].totalRewards")
                        .value(115));
    }
}