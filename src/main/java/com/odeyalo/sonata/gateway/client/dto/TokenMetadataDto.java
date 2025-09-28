package com.odeyalo.sonata.gateway.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenMetadataDto {
    @JsonProperty("user_id")
    private String userId;

    public TokenMetadataDto(final String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public TokenMetadataDto setUserId(final String userId) {
        this.userId = userId;
        return this;
    }

    public TokenMetadataDto() {

    }
}
