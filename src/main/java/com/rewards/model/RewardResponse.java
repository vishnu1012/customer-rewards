package com.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {

    private Long customerId;

    private String customerName;

    private Map<String, Integer> monthlyRewards;

    private Integer totalRewards;
}