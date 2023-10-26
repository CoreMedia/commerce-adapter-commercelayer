package com.coremedia.commerce.adapter.commercelayer.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Market extends CommerceLayerApiEntity {
  
  @JsonProperty("attributes")
  private com.coremedia.commerce.adapter.commercelayer.api.entities.Market.MarketAttributes attributes;
  
  public com.coremedia.commerce.adapter.commercelayer.api.entities.Market.MarketAttributes getAttributes() {
    return attributes;
  }
  
  public void setAttributes(com.coremedia.commerce.adapter.commercelayer.api.entities.Market.MarketAttributes attributes) {
    this.attributes = attributes;
  }
  
  public static class MarketAttributes {
    @JsonProperty("number") private int number;
    @JsonProperty("name") private String name;
    @JsonProperty("private") private boolean isPrivate;
    @JsonProperty("reference") private String reference;
    
    public int getNumber() {
      return number;
    }
    
    public void setNumber(int number) {
      this.number = number;
    }
    
    public String getName() {
      return name;
    }
    
    public void setName(String name) {
      this.name = name;
    }
    
    public boolean isPrivate() {
      return isPrivate;
    }
    
    public void setPrivate(boolean aPrivate) {
      isPrivate = aPrivate;
    }
    
    public String getReference() {
      return reference;
    }
    
    public void setReference(String reference) {
      this.reference = reference;
    }
  }
  
}