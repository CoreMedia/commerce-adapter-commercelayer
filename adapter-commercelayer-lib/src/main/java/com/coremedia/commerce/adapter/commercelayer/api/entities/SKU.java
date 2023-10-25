package com.coremedia.commerce.adapter.commercelayer.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SKU {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("attributes")
    private Attributes attributes;

    @JsonProperty("relationships")
    private Relationships relationships;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
        private RelationshipLinks shippingCategory;
        @JsonProperty("prices")
        private RelationshipLinks prices;
        @JsonProperty("stock_items")
        private RelationshipLinks stockItems;
        @JsonProperty("stock_reservations")
        private RelationshipLinks stockReservations;
        @JsonProperty("delivery_lead_times")
        private RelationshipLinks deliveryLeadTimes;
        @JsonProperty("sku_options")
        private RelationshipLinks skuOptions;
        @JsonProperty("attachments")
        private RelationshipLinks attachments;
        @JsonProperty("events")
        private RelationshipLinks events;
        @JsonProperty("tags")
        private RelationshipLinks tags;
        @JsonProperty("versions")
        private RelationshipLinks versions;

        public RelationshipLinks getShippingCategory() {
            return shippingCategory;
        }

        public void setShippingCategory(RelationshipLinks shippingCategory) {
            this.shippingCategory = shippingCategory;
        }

        public RelationshipLinks getPrices() {
            return prices;
        }

        public void setPrices(RelationshipLinks prices) {
            this.prices = prices;
        }

        public RelationshipLinks getStockItems() {
            return stockItems;
        }

        public void setStockItems(RelationshipLinks stockItems) {
            this.stockItems = stockItems;
        }

        public RelationshipLinks getStockReservations() {
            return stockReservations;
        }

        public void setStockReservations(RelationshipLinks stockReservations) {
            this.stockReservations = stockReservations;
        }

        public RelationshipLinks getDeliveryLeadTimes() {
            return deliveryLeadTimes;
        }

        public void setDeliveryLeadTimes(RelationshipLinks deliveryLeadTimes) {
            this.deliveryLeadTimes = deliveryLeadTimes;
        }

        public RelationshipLinks getSkuOptions() {
            return skuOptions;
        }

        public void setSkuOptions(RelationshipLinks skuOptions) {
            this.skuOptions = skuOptions;
        }

        public RelationshipLinks getAttachments() {
            return attachments;
        }

        public void setAttachments(RelationshipLinks attachments) {
            this.attachments = attachments;
        }

        public RelationshipLinks getEvents() {
            return events;
        }

        public void setEvents(RelationshipLinks events) {
            this.events = events;
        }

        public RelationshipLinks getTags() {
            return tags;
        }

        public void setTags(RelationshipLinks tags) {
            this.tags = tags;
        }

        public RelationshipLinks getVersions() {
            return versions;
        }

        public void setVersions(RelationshipLinks versions) {
            this.versions = versions;
        }
    }

}
