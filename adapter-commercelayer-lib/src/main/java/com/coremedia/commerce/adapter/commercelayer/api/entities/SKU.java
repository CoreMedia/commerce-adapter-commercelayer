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
    private SKUAttributes attributes;

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

    public SKUAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(SKUAttributes attributes) {
        this.attributes = attributes;
    }

    public static class SKUAttributes {
        @JsonProperty("code") private String code;
        @JsonProperty("name") private String name;
        @JsonProperty("description") private String description;
        @JsonProperty("image_url") private String imageUrl;
        @JsonProperty("do_not_ship") private boolean doNotShip;
        @JsonProperty("do_not_track") private boolean doNotTrack;
        @JsonProperty("reference") private String reference;
    }

}
