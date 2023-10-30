package com.coremedia.commerce.adapter.commercelayer.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for access token API entity.
 */
public class AccessToken {

    @JsonProperty("access_token")
    private String value;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private long expiresIn;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("created_at")
    private long createdAt;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - createdAt > (expiresIn - 10) * 1000; // expires 10 seconds earlier
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "value=" + value +
                ", tokenType=" + tokenType +
                ", scope=" + scope +
                ", expired=" + isExpired() +
                "}";
    }
}
