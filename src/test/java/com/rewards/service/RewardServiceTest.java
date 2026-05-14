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

/**
 * The type Reward service test.
 */
class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardService rewardService;

    /**
     * Sets .
     */
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test calculate rewards.
     */
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

    /**
     * Test no transactions found.
     */
    @Test
    void testNoTransactionsFound() {

        when(transactionRepository.findAll())
                .thenReturn(List.of());

        assertThrows(
                ResourceNotFoundException.class,
                () -> rewardService.calculateRewards()
        );
    }

    @Test
    void testRewardCalculationForAmountLessThan50() {

        List<Transaction> transactions = List.of(
                new Transaction(
                        1L,
                        101L,
                        "John",
                        40,
                        LocalDate.of(2026, 1, 10)
                )
        );

        when(transactionRepository.findAll())
                .thenReturn(transactions);

        List<RewardResponse> responses =
                rewardService.calculateRewards();

        assertEquals(
                0,
                responses.get(0).getTotalRewards()
        );
    }

    @Test
    void testRewardCalculationForAmountBetween50And100() {

        List<Transaction> transactions = List.of(
                new Transaction(
                        1L,
                        101L,
                        "John",
                        75,
                        LocalDate.of(2026, 1, 10)
                )
        );

        when(transactionRepository.findAll())
                .thenReturn(transactions);

        List<RewardResponse> responses =
                rewardService.calculateRewards();

        assertEquals(
                25,
                responses.get(0).getTotalRewards()
        );
    }

    @Test
    void testRewardCalculationForAmountGreaterThan100() {

        List<Transaction> transactions = List.of(
                new Transaction(
                        1L,
                        101L,
                        "John",
                        120,
                        LocalDate.of(2026, 1, 10)
                )
        );

        when(transactionRepository.findAll())
                .thenReturn(transactions);

        List<RewardResponse> responses =
                rewardService.calculateRewards();

        assertEquals(
                90,
                responses.get(0).getTotalRewards()
        );
    }

    @Test
    void testMultipleCustomersRewards() {

        List<Transaction> transactions = List.of(

                new Transaction(
                        1L,
                        101L,
                        "John",
                        120,
                        LocalDate.of(2026, 1, 10)
                ),

                new Transaction(
                        2L,
                        102L,
                        "Alice",
                        150,
                        LocalDate.of(2026, 1, 15)
                )
        );

        when(transactionRepository.findAll())
                .thenReturn(transactions);

        List<RewardResponse> responses =
                rewardService.calculateRewards();

        assertEquals(2, responses.size());
    }
}