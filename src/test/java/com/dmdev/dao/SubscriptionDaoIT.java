package com.dmdev.dao;

import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import com.dmdev.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubscriptionDaoIT extends IntegrationTestBase {

    private final SubscriptionDao subscriptionDao = SubscriptionDao.getInstance();

    @Test
    void findAll() {
        var subscription1 = subscriptionDao.insert(this.getSubscription("name"));
        var subscription2 = subscriptionDao.insert(this.getSubscription("name1"));
        var subscription3 = subscriptionDao.insert(this.getSubscription("name2"));

        var actualResult = subscriptionDao.findAll();

        assertThat(actualResult).hasSize(3);
        var subscriptionIds = actualResult.stream()
                .map(Subscription::getId)
                .toList();
        assertThat(subscriptionIds).contains(subscription1.getId(),
                subscription2.getId(),
                subscription3.getId());
    }

    @Test
    void findById() {
        var expected = subscriptionDao.insert(this.getSubscription("name"));

        var actual = subscriptionDao.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    void deleteExistingEntity() {
        var expected = subscriptionDao.insert(this.getSubscription("name"));

        var actual = subscriptionDao.delete(expected.getId());

        assertTrue(actual);
    }

    @Test
    void deleteNotExistingEntity() {
        subscriptionDao.insert(this.getSubscription("name"));
        var subscriptionId = 999;

        var actual = subscriptionDao.delete(subscriptionId);

        assertFalse(actual);
    }

    @Test
    void update() {
        var subscription = subscriptionDao.insert(this.getSubscription("name"));
        subscription.setName("updatedName");

        subscriptionDao.update(subscription);

        var maybeSubscriptionActual = subscriptionDao.findById(subscription.getId());
        assertThat(maybeSubscriptionActual).isPresent();
        maybeSubscriptionActual.ifPresent(subscriptionActual -> assertThat(subscriptionActual.getName()).isEqualTo("updatedName"));
    }

    @Test
    void upsert() {
        var subscription = subscriptionDao.insert(this.getSubscription("name"));
        subscription.setName("upsertName");
        subscriptionDao.upsert(subscription);

        var maybeSubscriptionActual = subscriptionDao.findById(subscription.getId());

        assertThat(maybeSubscriptionActual).isPresent();
        maybeSubscriptionActual.ifPresent(subscriptionActual -> assertThat(subscriptionActual.getName()).isEqualTo("upsertName"));
    }

    @Test
    void insert() {
        var subscription = this.getSubscription("name");

        var actual = subscriptionDao.insert(subscription);

        assertNotNull(actual.getId());
    }

    @Test
    void findByUserId() {
        var subscription = subscriptionDao.insert(this.getSubscription("name"));

        var actual = subscriptionDao.findByUserId(subscription.getUserId());

        assertThat(actual).hasSize(1);
        assertThat(actual).contains(subscription);
    }

    @Test
    void shouldNotFindByUserIdIfNotSubscribers() {
        subscriptionDao.insert(this.getSubscription("name"));
        var userId = 999;

        var actual = subscriptionDao.findByUserId(userId);

        assertThat(actual).isEmpty();
    }

    private Subscription getSubscription(String name) {
        return Subscription.builder()
                .userId(1)
                .name(name)
                .provider(Provider.GOOGLE)
                .expirationDate(new Timestamp(System.currentTimeMillis()).toInstant())
                .status(Status.ACTIVE)
                .build();
    }

}