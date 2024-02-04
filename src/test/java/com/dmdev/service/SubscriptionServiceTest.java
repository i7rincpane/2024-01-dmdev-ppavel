package com.dmdev.service;

import com.dmdev.dao.SubscriptionDao;
import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import com.dmdev.exception.SubscriptionException;
import com.dmdev.exception.ValidationException;
import com.dmdev.mapper.CreateSubscriptionMapper;
import com.dmdev.validator.CreateSubscriptionValidator;
import com.dmdev.validator.Error;
import com.dmdev.validator.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;

class SubscriptionServiceTest {

    private CreateSubscriptionValidator validator;
    private CreateSubscriptionMapper mapper;
    private SubscriptionDao subscriptionDao;
    private SubscriptionService subscriptionService;
    private Clock clock;

    @BeforeEach
    void prepare() {
        this.validator = Mockito.mock();
        this.mapper = Mockito.mock();
        this.subscriptionDao = Mockito.mock();
        this.clock = Clock.fixed(Clock.systemDefaultZone().instant(), Clock.systemUTC().getZone());
        this.subscriptionService = new SubscriptionService(subscriptionDao,
                mapper,
                validator,
                clock);
    }

    @Test
    void update() {
        var createSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("name")
                .provider(Provider.GOOGLE.name())
                .expirationDate(clock.instant().plus(Duration.ofDays(30)))
                .build();
        var subscriptionFinded = Subscription.builder()
                .userId(createSubscriptionDto.getUserId())
                .name(createSubscriptionDto.getName())
                .provider(Provider.findByName(createSubscriptionDto.getProvider()))
                .expirationDate(createSubscriptionDto.getExpirationDate())
                .build();
        var subscriptionExpected = Subscription.builder()
                .userId(subscriptionFinded.getUserId())
                .name(subscriptionFinded.getName())
                .provider(subscriptionFinded.getProvider())
                .expirationDate(subscriptionFinded.getExpirationDate())
                .status(Status.ACTIVE)
                .build();
        doReturn(new ValidationResult()).when(validator).validate(createSubscriptionDto);
        doReturn(Arrays.asList(subscriptionFinded)).when(subscriptionDao).findByUserId(createSubscriptionDto.getUserId());
        doReturn(subscriptionExpected).when(subscriptionDao).upsert(subscriptionFinded);
        assertThat("").isEqualTo("");

        var subscriptionActual = subscriptionService.upsert(createSubscriptionDto);

        assertThat(subscriptionActual).isEqualTo(subscriptionExpected);
        Mockito.verifyNoInteractions(mapper);
    }

    @MethodSource("getNameAndProviderArguments")
    @ParameterizedTest
    void insert(String nameExpected,
                Provider providerExpected,
                String nameFinted,
                Provider providerFinted) {
        var createSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(1)
                .name(nameExpected)
                .provider(providerExpected.name())
                .expirationDate(clock.instant().plus(Duration.ofDays(30)))
                .build();
        var subscriptionFinded = Subscription.builder()
                .userId(createSubscriptionDto.getUserId())
                .name(nameFinted)
                .provider(providerFinted)
                .build();
        var subscriptionProcessed = Subscription.builder()
                .userId(createSubscriptionDto.getUserId())
                .name(createSubscriptionDto.getName())
                .provider(Provider.findByName(createSubscriptionDto.getProvider()))
                .expirationDate(createSubscriptionDto.getExpirationDate())
                .status(Status.ACTIVE)
                .build();
        var subscriptionExpected = Subscription.builder()
                .userId(subscriptionProcessed.getUserId())
                .name(subscriptionProcessed.getName())
                .provider(subscriptionProcessed.getProvider())
                .expirationDate(subscriptionProcessed.getExpirationDate())
                .status(subscriptionProcessed.getStatus())
                .build();
        doReturn(new ValidationResult()).when(validator).validate(createSubscriptionDto);
        doReturn(Arrays.asList(subscriptionFinded)).when(subscriptionDao).findByUserId(createSubscriptionDto.getUserId());
        doReturn(subscriptionProcessed).when(mapper).map(createSubscriptionDto);
        doReturn(subscriptionExpected).when(subscriptionDao).upsert(subscriptionProcessed);

        var subscriptionActual = subscriptionService.upsert(createSubscriptionDto);

        assertThat(subscriptionActual).isEqualTo(subscriptionExpected);
    }

    public static Stream<Arguments> getNameAndProviderArguments() {
        return Stream.of(
                Arguments.of("dummy", Provider.APPLE, "name", Provider.APPLE),
                Arguments.of("name", Provider.GOOGLE, "name", Provider.APPLE)
        );
    }

    @Test
    void throwValidationExceptionIfHasErrors() {
        var сreateSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(null)
                .name("name")
                .provider(Provider.GOOGLE.name())
                .expirationDate(LocalDate
                        .now()
                        .plusMonths(1)
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC))
                .build();
        var validationResult = new ValidationResult();
        var error = Error.of(100, "userId is invalid");
        validationResult.add(error);
        doReturn(validationResult).when(validator).validate(сreateSubscriptionDto);

        assertAll(
                () -> {
                    var exception = assertThrows(ValidationException.class, () -> subscriptionService.upsert(сreateSubscriptionDto));
                    assertThat(exception.getErrors()).isNotEmpty();
                    assertThat(exception.getErrors().get(0)).isEqualTo(error);
                }
        );
        Mockito.verifyNoInteractions(subscriptionDao, mapper);
    }

    @Test
    void cancelSucsseful() {
        var subscriptionFinded = Subscription.builder()
                .id(1)
                .status(Status.ACTIVE)
                .build();
        doReturn(Optional.of(subscriptionFinded)).when(subscriptionDao).findById(subscriptionFinded.getId());

        subscriptionService.cancel(subscriptionFinded.getId());

        assertThat(subscriptionFinded.getStatus()).isEqualTo(Status.CANCELED);
        Mockito.verify(subscriptionDao).update(subscriptionFinded);
    }

    @Test
    void cancelThrowIllegalArgumentExceptionIfNotFoundSubscription() {
        var subscriptionId = 1;
        doReturn(Optional.empty()).when(subscriptionDao).findById(subscriptionId);

        assertThrows(IllegalArgumentException.class, () -> subscriptionService.cancel(subscriptionId));
        Mockito.verify(subscriptionDao, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    void cancelThrowSubscriptionExceptionIfSubscriptionStatusNotActive() {
        var subscriptionId = 1;
        var subscriptionFinded = Subscription.builder()
                .id(subscriptionId)
                .status(Status.EXPIRED)
                .build();
        doReturn(Optional.of(subscriptionFinded)).when(subscriptionDao).findById(subscriptionId);

        assertAll(
                () -> {
                    var exception = assertThrows(SubscriptionException.class, () -> subscriptionService.cancel(subscriptionId));
                    assertThat(exception.getMessage()).isEqualTo(String.format("Only active subscription %d can be canceled", subscriptionId));
                }
        );
        Mockito.verify(subscriptionDao, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    void expireSucsseful() {
        Integer subscriptionId = 1;
        var subscriptionFinded = Subscription.builder()
                .id(subscriptionId)
                .status(Status.ACTIVE)
                .build();
        doReturn(Optional.of(subscriptionFinded)).when(subscriptionDao).findById(subscriptionId);

        subscriptionService.expire(subscriptionId);

        assertThat(subscriptionFinded.getStatus()).isEqualTo(Status.EXPIRED);
        assertThat(subscriptionFinded.getExpirationDate()).isEqualTo(clock.instant());
    }

    @Test
    void expireThrowIllegalArgumentExceptionIfNotFoundSubscription() {
        var subscriptionId = 1;
        doReturn(Optional.empty()).when(subscriptionDao).findById(subscriptionId);

        assertThrows(IllegalArgumentException.class, () -> subscriptionService.expire(subscriptionId));
        Mockito.verify(subscriptionDao, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    void expireThrowSubscriptionExceptionIfSubscriptionStatusExpired() {
        var subscriptionId = 1;
        var subscriptionFinded = Subscription.builder()
                .id(subscriptionId)
                .status(Status.EXPIRED)
                .build();
        doReturn(Optional.of(subscriptionFinded)).when(subscriptionDao).findById(subscriptionId);

        assertAll(
                () -> {
                    var exception = assertThrows(SubscriptionException.class, () -> subscriptionService.expire(subscriptionId));
                    assertThat(exception.getMessage()).isEqualTo(String.format("Subscription %d has already expired", subscriptionId));
                }
        );
        Mockito.verify(subscriptionDao, Mockito.times(0)).update(Mockito.any());
    }

}