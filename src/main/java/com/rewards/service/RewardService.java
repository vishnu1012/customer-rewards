package com.rewards.service;

import com.rewards.model.RewardResponse;
import com.rewards.model.Transaction;
import com.rewards.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.rewards.exception.ResourceNotFoundException;

/**
 * The type Reward service.
 */
@Service
@RequiredArgsConstructor
public class RewardService {

    private final TransactionRepository transactionRepository;

    /**
     * Calculate rewards list.
     *
     * @return the list
     */
    public List<RewardResponse> calculateRewards() {

        List<Transaction> transactions =
                transactionRepository.findAll();

        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No transactions found"
            );
        }

        Map<Long, List<Transaction>> customerTransactions =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getCustomerId
                        ));

        return customerTransactions.values()
                .stream()
                .map(this::buildRewardResponse)
                .toList();
    }

    private RewardResponse buildRewardResponse(
            List<Transaction> transactions) {

        Transaction transaction = transactions.get(0);

        Map<String, Integer> monthlyRewards =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                t -> t.getTransactionDate()
                                        .getMonth()
                                        .name(),
                                Collectors.summingInt(
                                        t -> calculatePoints(
                                                t.getAmount()
                                        )
                                )
                        ));

        int totalRewards =
                monthlyRewards.values()
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum();

        return RewardResponse.builder()
                .customerId(transaction.getCustomerId())
                .customerName(transaction.getCustomerName())
                .monthlyRewards(monthlyRewards)
                .totalRewards(totalRewards)
                .build();
    }

    private int calculatePoints(double amount) {

        int points = 0;

        if (amount > 100) {
            points += (int) ((amount - 100) * 2);
            points += 50;
        } else if (amount > 50) {
            points += (int) (amount - 50);
        }

        return points;
    }
}