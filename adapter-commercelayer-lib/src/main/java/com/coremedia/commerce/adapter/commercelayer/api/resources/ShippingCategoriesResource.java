package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.PaginatedEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import com.coremedia.commerce.adapter.commercelayer.api.entities.ShippingCategory;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang3.StringUtils;
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
    private static final String SHIPPINGCATEGORIES_PATH = "/shipping_categories";
    private static final String SHIPPINGCATEGORIES_ID_PATH = SHIPPINGCATEGORIES_PATH + "/{id}";
    // private static final String SKUS_SHIPPING_CATEGORY_PATH = SKUS_ID_PATH + "/shipping_category";
    private static final String SHIPPINGCATEGORIE_SEARCH_FILTER = "filter[q][reference_eq]"; // see: https://docs.commercelayer.io/core/filtering-data
    
    
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

    public List<ShippingCategory> searchShippingCategories(@NonNull String searchTerm) {
        // TODO: Search for a match in attributes name or reference
        LOG.debug("Searching Shipping Categories for search term: {}", searchTerm);
        if (StringUtils.isBlank(searchTerm)) {
            return Collections.emptyList();
        }
        
        ListMultimap<String, String> queryParams = ImmutableListMultimap.of(SHIPPINGCATEGORIE_SEARCH_FILTER, searchTerm);
        ParameterizedTypeReference<PaginatedEntity<ShippingCategory>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<PaginatedEntity<ShippingCategory>> responseEntity = getConnector().getResource(SHIPPINGCATEGORIES_PATH, Collections.emptyMap(), queryParams, responseType);
        Optional<List<ShippingCategory>> shippingCategories = responseEntity.map(PaginatedEntity::getData);
        return shippingCategories.orElse(Collections.emptyList());
    }

}
