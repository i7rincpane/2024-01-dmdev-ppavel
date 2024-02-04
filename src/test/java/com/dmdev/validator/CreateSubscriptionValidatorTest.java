package com.dmdev.validator;

import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;


import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.ZoneOffset;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CreateSubscriptionValidatorTest {
    
    private final CreateSubscriptionValidator validator = CreateSubscriptionValidator.getInstance();
    
    @Test
    void shouldPassValidation() {
        var createSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("name")
                .provider(Provider.GOOGLE.name())
                .expirationDate(LocalDate
                        .now()
                        .plusMonths(1)
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC))
                .build();

        var actual = validator.validate(createSubscriptionDto);
        
        assertFalse(actual.hasErrors());
    }

    @Test
    void invalidUserId() {
        var createSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(null)
                .name("name")
                .provider(Provider.GOOGLE.name())
                .expirationDate(LocalDate
                        .now()
                        .plusMonths(1)
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC))
                .build();
        var expectedError = Error.of(100, "userId is invalid");

        var actualResult = validator.validate(createSubscriptionDto);
        
        var actualErorr = actualResult.getErrors().get(0);
        assertTrue(actualResult.hasErrors());
        assertThat(actualResult.getErrors()).hasSize(1);
        assertThat(actualErorr).isEqualTo(expectedError);
        assertThat(actualErorr.getMessage()).isEqualTo(expectedError.getMessage());
        assertThat(actualErorr.getCode()).isEqualTo(expectedError.getCode());
    }

    @Test
    void invalidName() {
        var createSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(1)
                .name(null)
                .provider(Provider.GOOGLE.name())
                .expirationDate(LocalDate
                        .now()
                        .plusMonths(1)
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC))
                .build();
        
        var actual = validator.validate(createSubscriptionDto);
        
        assertThat(actual.getErrors()).hasSize(1);
        assertThat(actual.getErrors().get(0).getMessage()).isEqualTo("name is invalid");
        assertThat(actual.getErrors().get(0).getCode()).isEqualTo(101);
    }

    @Test
    void invalidProvider() {
        var createSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("name")
                .provider("fake")
                .expirationDate(LocalDate
                        .now()
                        .plusMonths(1)
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC))
                .build();

        var actual = validator.validate(createSubscriptionDto);

        assertThat(actual.getErrors()).hasSize(1);
        assertThat(actual.getErrors().get(0).getMessage()).isEqualTo("provider is invalid");
        assertThat(actual.getErrors().get(0).getCode()).isEqualTo(102);
    }

    @Test
    void invalidExpirationDate() {
        var createSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("name")
                .provider(Provider.GOOGLE.name())
                .expirationDate(LocalDate
                        .now()
                        .minusMonths(1)
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC))
                .build();
        
        var actual = validator.validate(createSubscriptionDto);
        
        assertThat(actual.getErrors()).hasSize(1);
        assertThat(actual.getErrors().get(0).getMessage()).isEqualTo("expirationDate is invalid");
        assertThat(actual.getErrors().get(0).getCode()).isEqualTo(103);
    }

    @Test
    void invalidExpirationDateAndProviderAndNameAndUserId() {
        var createSubscriptionDto = CreateSubscriptionDto.builder()
                .userId(null)
                .name(null)
                .provider("fake")
                .expirationDate(null)
                .build();
        
        var actual = validator.validate(createSubscriptionDto);
        
        assertThat(actual.getErrors()).hasSize(4);
        var errorMessages = actual.getErrors().stream()
                .map(Error::getMessage)
                .toList();
        var errorCodes = actual.getErrors().stream()
                .map(Error::getCode)
                .toList();
        assertThat(errorCodes).contains(100, 101, 102, 103);
        assertThat(errorMessages).contains("expirationDate is invalid", "provider is invalid", "name is invalid", "userId is invalid");
    }
    
}