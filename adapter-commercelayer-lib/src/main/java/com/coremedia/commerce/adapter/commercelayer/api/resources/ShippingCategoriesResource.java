package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.PaginatedEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import com.coremedia.commerce.adapter.commercelayer.api.entities.ShippingCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShippingCategoriesResource extends CommerceLayerApiResource {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ShippingCategoriesResource(CommerceLayerApiConnector connector) {
        super(connector);
    }

    public List<ShippingCategory> listShippingCategories() {
        ParameterizedTypeReference<PaginatedEntity<ShippingCategory>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<PaginatedEntity<ShippingCategory>> responseEntity = getConnector().getResource("/shipping_categories", responseType);
        Optional<List<ShippingCategory>> shippingCategories = responseEntity.map(PaginatedEntity::getData);
        return shippingCategories.orElse(Collections.emptyList());
    }

    public Optional<ShippingCategory> getShippingCategory(String id) {
        ParameterizedTypeReference<DataEntity<ShippingCategory>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataEntity<ShippingCategory>> responseEntity = getConnector().getResource("/shipping_categories/{id}", Map.of(ID_PARAM, id), responseType);
        Optional<ShippingCategory> shippingCategory = responseEntity.map(DataEntity::getData);
        return shippingCategory;
    }

    public List<SKU> getAssociatedSkus(String id) {
        ParameterizedTypeReference<PaginatedEntity<SKU>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<PaginatedEntity<SKU>> responseEntity = getConnector().getResource("/shipping_categories/{id}/skus", Map.of(ID_PARAM, id), responseType);
        Optional<List<SKU>> skus = responseEntity.map(PaginatedEntity::getData);
        return skus.orElse(Collections.emptyList());
    }

}
