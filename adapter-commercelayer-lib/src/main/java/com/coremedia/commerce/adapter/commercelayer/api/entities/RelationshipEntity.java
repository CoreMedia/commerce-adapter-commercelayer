package com.coremedia.commerce.adapter.commercelayer.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipEntity {

  @JsonProperty("links")
  private RelationshipLinkEntry link;

  @JsonProperty("data")
  private Object data;

  public RelationshipLinkEntry getLink() {
    return link;
  }

  public void setLink(RelationshipLinkEntry link) {
    this.link = link;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public static class RelationshipLinkEntry {
    @JsonProperty("self")
    private String self;
    @JsonProperty("related")
    private String related;

    public String getSelf() {
      return self;
    }

    public void setSelf(String self) {
      this.self = self;
    }

    public String getRelated() {
      return related;
    }

    public void setRelated(String related) {
      this.related = related;
    }
  }

}
