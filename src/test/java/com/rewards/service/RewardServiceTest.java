package com.rewards.service;

import com.rewards.exception.ResourceNotFoundException;
import com.rewards.model.RewardResponse;
import com.rewards.model.Transaction;
import com.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardService rewardService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateRewards() {

        List<Transaction> transactions = List.of(

                new Transaction(
                        1L,
                        101L,
                        "John",
                        120,
                        LocalDate.of(2026, 1, 15)
                ),

                new Transaction(
                        2L,
                        101L,
                        "John",
                        75,
                        LocalDate.of(2026, 1, 20)
                )
        );

        when(transactionRepository.findAll())
                .thenReturn(transactions);

        List<RewardResponse> response =
                rewardService.calculateRewards();

        assertEquals(1, response.size());

        RewardResponse rewardResponse = response.get(0);

        assertEquals(101L,
                rewardResponse.getCustomerId());

        assertEquals(115,
                rewardResponse.getTotalRewards());
    }

    @Test
    void testNoTransactionsFound() {

        when(transactionRepository.findAll())
                .thenReturn(List.of());

        assertThrows(
                ResourceNotFoundException.class,
                () -> rewardService.calculateRewards()
        );
    }
}