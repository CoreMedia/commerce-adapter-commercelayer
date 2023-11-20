package com.coremedia.commerce.adapter.commercelayer.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SKUList extends CommerceLayerApiEntity {

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
    @JsonProperty("name")
    private String name;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("description")
    private String description;
    @JsonProperty("image_url")
    private String image_url;
    @JsonProperty("manual")
    private boolean manual;
    @JsonProperty("sku_code_regex")
    private String skuCodeRegex;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("reference_origin")
    private String referenceOrigin;
    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSlug() {
      return slug;
    }

    public void setSlug(String slug) {
      this.slug = slug;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getImage_url() {
      return image_url;
    }

    public void setImage_url(String image_url) {
      this.image_url = image_url;
    }

    public boolean isManual() {
      return manual;
    }

    public void setManual(boolean manual) {
      this.manual = manual;
    }

    public String getSkuCodeRegex() {
      return skuCodeRegex;
    }

    public void setSkuCodeRegex(String skuCodeRegex) {
      this.skuCodeRegex = skuCodeRegex;
    }

    public String getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
    }

    public String getReference() {
      return reference;
    }

    public void setReference(String reference) {
      this.reference = reference;
    }

    public String getReferenceOrigin() {
      return referenceOrigin;
    }

    public void setReferenceOrigin(String referenceOrigin) {
      this.referenceOrigin = referenceOrigin;
    }

    public Map<String, Object> getMetadata() {
      return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
      this.metadata = metadata;
    }
  }

  public static class Relationships {
    @JsonProperty("customer")
    private RelationshipEntity customer;
    @JsonProperty("skus")
    private RelationshipEntity skus;
    @JsonProperty("sku_list_items")
    private RelationshipEntity skuListItems;
    @JsonProperty("bundles")
    private RelationshipEntity bundles;
    @JsonProperty("attachments")
    private RelationshipEntity attachments;
    @JsonProperty("versions")
    private RelationshipEntity versions;

    public RelationshipEntity getCustomer() {
      return customer;
    }

    public void setCustomer(RelationshipEntity customer) {
      this.customer = customer;
    }

    public RelationshipEntity getSkus() {
      return skus;
    }

    public void setSkus(RelationshipEntity skus) {
      this.skus = skus;
    }

    public RelationshipEntity getSkuListItems() {
      return skuListItems;
    }

    public void setSkuListItems(RelationshipEntity skuListItems) {
      this.skuListItems = skuListItems;
    }

    public RelationshipEntity getBundles() {
      return bundles;
    }

    public void setBundles(RelationshipEntity bundles) {
      this.bundles = bundles;
    }

    public RelationshipEntity getAttachments() {
      return attachments;
    }

    public void setAttachments(RelationshipEntity attachments) {
      this.attachments = attachments;
    }

    public RelationshipEntity getVersions() {
      return versions;
    }

    public void setVersions(RelationshipEntity versions) {
      this.versions = versions;
    }
  }

}
