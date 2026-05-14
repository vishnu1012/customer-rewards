package com.rewards.controller;

import com.rewards.model.RewardResponse;
import com.rewards.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for reward related APIs.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rewards")
@Tag(name = "Rewards API",
        description = "Customer Rewards Calculation APIs")
public class RewardController {

    private final RewardService rewardService;

    /**
     * Returns reward points of all customers.
     *
     * @return list of rewards
     */
    @GetMapping
    @Operation(summary = "Get rewards for all customers")
    public List<RewardResponse> getRewards() {

        return rewardService.calculateRewards();
    }
}