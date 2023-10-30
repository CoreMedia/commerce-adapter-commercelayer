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

public class SKUResource extends CommerceLayerApiResource {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public SKUResource(CommerceLayerApiConnector connector) {
        super(connector);
    }

    public List<SKU> listSKUs() {
        ParameterizedTypeReference<PaginatedEntity<SKU>> responseType = new ParameterizedTypeReference<>() {};
        Optional<PaginatedEntity<SKU>> responseEntity = getConnector().getResource("/skus", responseType);
        Optional<List<SKU>> skus = responseEntity.map(PaginatedEntity::getData);
        return  skus.orElse(Collections.emptyList());
    }

    public Optional<SKU> getSku(String id) {
        ParameterizedTypeReference<DataEntity<SKU>> responseType = new ParameterizedTypeReference<>() {};

        Optional<DataEntity<SKU>> responseEntity = getConnector().getResource("/skus/{id}", Map.of(ID_PARAM, id), responseType);
        return responseEntity.map(DataEntity::getData);
    }

    public Optional<ShippingCategory> getShippingCategoryForSku(String id) {
        ParameterizedTypeReference<DataEntity<ShippingCategory>> responseType = new ParameterizedTypeReference<>() {};
        Optional<DataEntity<ShippingCategory>> responseEntity = getConnector().getResource("/skus/{id}/shipping_category", Map.of(ID_PARAM, id), responseType);
        return responseEntity.map(DataEntity::getData);
    }

    public List<SKU> searchSkus(@NonNull String searchTerm) {
        if (StringUtils.isBlank(searchTerm)) {
            return Collections.emptyList();
        }

        ListMultimap<String, String> queryParams = ImmutableListMultimap.of("filter[q][name_or_code_cont]", searchTerm); // Searching for name or code attributes
        ParameterizedTypeReference<PaginatedEntity<SKU>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<PaginatedEntity<SKU>> responseEntity = getConnector().getResource("/skus", Collections.emptyMap(), queryParams, responseType);
        Optional<List<SKU>> skus = responseEntity.map(PaginatedEntity::getData);
        return skus.orElse(Collections.emptyList());
    }

}
