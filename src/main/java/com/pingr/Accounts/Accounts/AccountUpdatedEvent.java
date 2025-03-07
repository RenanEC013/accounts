package com.pingr.Accounts.Accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize
public class AccountUpdatedEvent {
    @JsonProperty
    private final String eventType;

    @JsonProperty
    private final Long accountId;

    @JsonProperty
    private final Map<String, Object> payload;

    private AccountUpdatedEvent(String eventType, Long accountId, Map<String, Object> payload) {
        this.eventType = eventType;
        this.accountId = accountId;
        this.payload = payload;
    }

    public static AccountUpdatedEvent of(Account account) {
        Map<String, Object> accountMapView = new HashMap<>();

        accountMapView.put("id", account.getId());
        accountMapView.put("username", account.getUsername());
        accountMapView.put("email", account.getEmail());

        return new AccountUpdatedEvent(
                "AccountUpdatedEvent",
                account.getId(),
                accountMapView
        );
    }
}
