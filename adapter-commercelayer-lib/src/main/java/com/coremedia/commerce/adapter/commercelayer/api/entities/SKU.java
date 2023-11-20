package com.coremedia.commerce.adapter.commercelayer.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SKU extends CommerceLayerApiEntity {

  @JsonProperty("attributes")
  private Attributes attributes;

  @JsonProperty("relationships")
  private Relationships relationships;

  public Attributes getAttributes() {
    return attributes;
  }

  public void setAttributes(Attributes attributes) {
    this.attributes = attributes;
  }

  public Relationships getRelationships() {
    return relationships;
  }

  public void setRelationships(Relationships relationships) {
    this.relationships = relationships;
  }

  public static class Attributes {
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("do_not_ship")
    private boolean doNotShip;
    @JsonProperty("do_not_track")
    private boolean doNotTrack;
    @JsonProperty("reference")
    private String reference;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getImageUrl() {
      return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
    }

    public boolean isDoNotShip() {
      return doNotShip;
    }

    public void setDoNotShip(boolean doNotShip) {
      this.doNotShip = doNotShip;
    }

    public boolean isDoNotTrack() {
      return doNotTrack;
    }

    public void setDoNotTrack(boolean doNotTrack) {
      this.doNotTrack = doNotTrack;
    }

    public String getReference() {
      return reference;
    }

    public void setReference(String reference) {
      this.reference = reference;
    }
  }

  public static class Relationships {
    @JsonProperty("shipping_category")
    private RelationshipEntity shippingCategory;
    @JsonProperty("prices")
    private RelationshipEntity prices;
    @JsonProperty("stock_items")
    private RelationshipEntity stockItems;
    @JsonProperty("stock_reservations")
    private RelationshipEntity stockReservations;
    @JsonProperty("delivery_lead_times")
    private RelationshipEntity deliveryLeadTimes;
    @JsonProperty("sku_options")
    private RelationshipEntity skuOptions;
    @JsonProperty("attachments")
    private RelationshipEntity attachments;
    @JsonProperty("events")
    private RelationshipEntity events;
    @JsonProperty("tags")
    private RelationshipEntity tags;
    @JsonProperty("versions")
    private RelationshipEntity versions;

    public RelationshipEntity getShippingCategory() {
      return shippingCategory;
    }

    public void setShippingCategory(RelationshipEntity shippingCategory) {
      this.shippingCategory = shippingCategory;
    }

    public RelationshipEntity getPrices() {
      return prices;
    }

    public void setPrices(RelationshipEntity prices) {
      this.prices = prices;
    }

    public RelationshipEntity getStockItems() {
      return stockItems;
    }

    public void setStockItems(RelationshipEntity stockItems) {
      this.stockItems = stockItems;
    }

    public RelationshipEntity getStockReservations() {
      return stockReservations;
    }

    public void setStockReservations(RelationshipEntity stockReservations) {
      this.stockReservations = stockReservations;
    }

    public RelationshipEntity getDeliveryLeadTimes() {
      return deliveryLeadTimes;
    }

    public void setDeliveryLeadTimes(RelationshipEntity deliveryLeadTimes) {
      this.deliveryLeadTimes = deliveryLeadTimes;
    }

    public RelationshipEntity getSkuOptions() {
      return skuOptions;
    }

    public void setSkuOptions(RelationshipEntity skuOptions) {
      this.skuOptions = skuOptions;
    }

    public RelationshipEntity getAttachments() {
      return attachments;
    }

    public void setAttachments(RelationshipEntity attachments) {
      this.attachments = attachments;
    }

    public RelationshipEntity getEvents() {
      return events;
    }

    public void setEvents(RelationshipEntity events) {
      this.events = events;
    }

    public RelationshipEntity getTags() {
      return tags;
    }

    public void setTags(RelationshipEntity tags) {
      this.tags = tags;
    }

    public RelationshipEntity getVersions() {
      return versions;
    }

    public void setVersions(RelationshipEntity versions) {
      this.versions = versions;
    }
  }

}
