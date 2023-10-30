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

/**
 * REST resource for <a href="https://docs.commercelayer.io/core/v/api-reference/skus/object">SKU</a> retrieval.
 */
public class SKUResource extends CommerceLayerApiResource {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String SKUS_PATH = "/skus";
    private static final String SKUS_ID_PATH = SKUS_PATH + "/{id}";
    private static final String SKUS_SHIPPING_CATEGORY_PATH = SKUS_ID_PATH + "/shipping_category";
    private static final String SKU_SEARCH_FILTER = "filter[q][name_or_code_cont]"; // see: https://docs.commercelayer.io/core/filtering-data

    public SKUResource(CommerceLayerApiConnector connector) {
        super(connector);
    }

    /**
     * Fetch all SKUs.
     *
     * @return list of all SKUs.
     */
    public List<SKU> listSKUs() {
        LOG.debug("Fetching SKUs.");
        ParameterizedTypeReference<PaginatedEntity<SKU>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<PaginatedEntity<SKU>> responseEntity = getConnector().getResource(SKUS_PATH, responseType);
        Optional<List<SKU>> skus = responseEntity.map(PaginatedEntity::getData);
        return skus.orElse(Collections.emptyList());
    }

    /**
     * Retrieve a SKU by its id.
     *
     * @param id the SKU id.
     * @return {@link Optional} with the resulting {@link SKU} or an {@link Optional#empty()}.
     */
    public Optional<SKU> getSku(String id) {
        LOG.debug("Fetching SKU with id {}.", id);
        ParameterizedTypeReference<DataEntity<SKU>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataEntity<SKU>> responseEntity = getConnector().getResource(SKUS_ID_PATH, Map.of(ID_PARAM, id), responseType);
        return responseEntity.map(DataEntity::getData);
    }

    /**
     * Retrieve the {@link ShippingCategory} for the given SKU id.
     *
     * @param id the SKU id.
     * @return {@link Optional} with the resulting {@link ShippingCategory} or an {@link Optional#empty()}.
     */
    public Optional<ShippingCategory> getShippingCategoryForSku(String id) {
        LOG.debug("Fetching shipping category for SKU '{}'.", id);
        ParameterizedTypeReference<DataEntity<ShippingCategory>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataEntity<ShippingCategory>> responseEntity = getConnector().getResource(SKUS_SHIPPING_CATEGORY_PATH, Map.of(ID_PARAM, id), responseType);
        return responseEntity.map(DataEntity::getData);
    }

    /**
     * Search SKUs by the given search term.
     * The provided search term is matched against the "name" and "code" attribute.
     *
     * @param searchTerm search term
     * @return a list of matching {@link SKU}s or an empty list.
     */
    public List<SKU> searchSkus(@NonNull String searchTerm) {
        LOG.debug("Searching SKUs for search term: {}", searchTerm);
        if (StringUtils.isBlank(searchTerm)) {
            return Collections.emptyList();
        }

        ListMultimap<String, String> queryParams = ImmutableListMultimap.of(SKU_SEARCH_FILTER, searchTerm);
        ParameterizedTypeReference<PaginatedEntity<SKU>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<PaginatedEntity<SKU>> responseEntity = getConnector().getResource(SKUS_PATH, Collections.emptyMap(), queryParams, responseType);
        Optional<List<SKU>> skus = responseEntity.map(PaginatedEntity::getData);
        return skus.orElse(Collections.emptyList());
    }

}
