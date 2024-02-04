package com.dmdev.service;

import com.dmdev.dao.SubscriptionDao;
import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.mapper.CreateSubscriptionMapper;
import com.dmdev.validator.CreateSubscriptionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionServiceIT extends IntegrationTestBase {

    private CreateSubscriptionValidator validator;
    private CreateSubscriptionMapper mapper;
    private SubscriptionDao subscriptionDao;
    private SubscriptionService subscriptionService;
    private Clock clock;

    @BeforeEach
    void prepare() {
        this.validator = CreateSubscriptionValidator.getInstance();
        this.mapper = CreateSubscriptionMapper.getInstance();
        this.subscriptionDao = SubscriptionDao.getInstance();
        this.clock = Clock.fixed(Clock.systemDefaultZone().instant(), Clock.systemUTC().getZone());
        this.subscriptionService = new SubscriptionService(subscriptionDao,
                mapper,
                validator,
                clock);
    }

    @Test
    void upsert() {
        var subscription = subscriptionService.upsert(getCreateSubscriptionDto());

        assertThat(subscription.getId()).isNotNull();
    }

    private CreateSubscriptionDto getCreateSubscriptionDto() {
        return CreateSubscriptionDto.builder()
                .userId(1)
                .name("name")
                .provider(Provider.GOOGLE.name())
                .expirationDate(clock.instant().plus(Duration.ofDays(30)))
                .build();
    }
}