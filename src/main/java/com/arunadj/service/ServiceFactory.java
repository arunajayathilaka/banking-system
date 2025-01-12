package com.arunadj.service;

import com.arunadj.repository.AccountRepository;
import lombok.Getter;

/**
 * This is Service Factory for Interest Service and Account Service.
 */
public class ServiceFactory {
    @Getter
    private static final InterestService interestService = new InterestService(new SimpleInterestCalculator());
    @Getter
    private static final AccountService accountService = new AccountService(new AccountRepository());
}
