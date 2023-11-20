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
  private long expiresIn; // seconds

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("created_at")
    private long createdAt;

  private long fetchedAt;

  public AccessToken() {
    fetchedAt = System.currentTimeMillis() / 1000;
  }

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

  public long getExpiresAt() {
    return fetchedAt + expiresIn;
  }

  public boolean isExpired() {
    long now = System.currentTimeMillis() / 1000;
    return now > getExpiresAt();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
            "value=" + value +
            ", tokenType=" + tokenType +
            ", createdAt=" + createdAt +
            ", expiresIn=" + expiresIn +
            ", expiresAt=" + getExpiresAt() +
            ", fetchedAt=" + fetchedAt +
            ", scope=" + scope +
            ", expired=" + isExpired() +
            "}";
  }
}
