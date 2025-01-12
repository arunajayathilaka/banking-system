package com.arunadj.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ServiceFactoryTest {

    @Test
    void testInterestServiceInitialization() {
        InterestService interestService = ServiceFactory.getInterestService();
        assertNotNull(interestService);
    }

    @Test
    void testAccountServiceInitialization() {
        AccountService accountService = ServiceFactory.getAccountService();
        assertNotNull(accountService);
    }

    @Test
    void testInterestServiceSingleton() {
        InterestService service1 = ServiceFactory.getInterestService();
        InterestService service2 = ServiceFactory.getInterestService();
        assertSame(service1, service2);
    }

    @Test
    void testAccountServiceSingleton() {
        AccountService service1 = ServiceFactory.getAccountService();
        AccountService service2 = ServiceFactory.getAccountService();
        assertSame(service1, service2);
    }
}


