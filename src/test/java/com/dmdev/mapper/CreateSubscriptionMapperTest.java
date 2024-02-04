package com.dmdev.mapper;

import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

class CreateSubscriptionMapperTest {

    private final CreateSubscriptionMapper mapper = CreateSubscriptionMapper.getInstance();

    @Test
    void map() {
        var createSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("name")
                .provider(Provider.GOOGLE.name())
                .expirationDate(Instant.now())
                .build();
        var expected = Subscription.builder()
                .userId(createSubscriptionDto.getUserId())
                .name(createSubscriptionDto.getName())
                .provider(Provider.findByName(createSubscriptionDto.getProvider()))
                .expirationDate(createSubscriptionDto.getExpirationDate())
                .status(Status.ACTIVE)
                .build();

        var actual = mapper.map(createSubscriptionDto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}