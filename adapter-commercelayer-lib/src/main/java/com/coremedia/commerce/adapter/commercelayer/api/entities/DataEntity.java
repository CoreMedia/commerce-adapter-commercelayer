package com.coremedia.commerce.adapter.commercelayer.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class DataEntity<T> {

    @JsonProperty("data")
    private T data;

    @JsonProperty("included")
    private List<Include> included;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Include> getIncluded() {
        return included;
    }

    public void setIncluded(List<Include> included) {
        this.included = included;
    }

    public static class Include {

        @JsonProperty("id")
        private String id;
        @JsonProperty("type")
        private String type;
        @JsonProperty("self")
        private String self;
        @JsonProperty("attributes")
        private Map<String, Object> attributes;
        @JsonProperty("relationships")
        private Map<String, RelationshipEntity.RelationshipLinkEntry> relationships;

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

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        public Map<String, RelationshipEntity.RelationshipLinkEntry> getRelationships() {
            return relationships;
        }

        public void setRelationships(Map<String, RelationshipEntity.RelationshipLinkEntry> relationships) {
            this.relationships = relationships;
        }
    }
}
