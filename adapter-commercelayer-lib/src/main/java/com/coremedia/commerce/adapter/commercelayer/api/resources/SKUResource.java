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
    LOG.debug("Fetching all SKUs.");
    Map<String, String> additionalQueryParams = Map.of(INCLUDE_PARAM, SKU_DEFAULT_INCLUDES_VALUE);
    List<SKU> skus = fetchAllPages(SKUS_PATH, RESPONSE_TYPE_PAGINATED_ENTITY_SKU, additionalQueryParams);
    LOG.debug("Fetched {} skus.", skus.size());
    return skus;
  }

  /**
   * Retrieve a SKU by its id.
   *
   * @param id the SKU id.
   * @return {@link Optional} with the resulting {@link SKU} or an {@link Optional#empty()}.
   */
  public Optional<SKU> getSku(String id) {
    LOG.debug("Fetching SKU with id {}.", id);
    ListMultimap<String, String> queryParams = ImmutableListMultimap.of(INCLUDE_PARAM, SKU_DEFAULT_INCLUDES_VALUE);
    Optional<DataEntity<SKU>> responseEntity = getConnector().getResource(SKUS_ID_PATH, Map.of(ID_PARAM, id), queryParams, RESPONSE_TYPE_DATA_ENTITY_SKU);
    return responseEntity.map(DataEntity::getData);
  }

  public Optional<DataEntity<SKU>> getSkuWithInclude(String id) {
    LOG.debug("Fetching SKU with id {}.", id);
    ListMultimap<String, String> queryParams = ImmutableListMultimap.of(INCLUDE_PARAM, SKU_DEFAULT_INCLUDES_VALUE);
    return getConnector().getResource(SKUS_ID_PATH, Map.of(ID_PARAM, id), queryParams, RESPONSE_TYPE_DATA_ENTITY_SKU);
  }

  /**
   * Retrieve the {@link ShippingCategory} for the given SKU id.
   *
   * @param id the SKU id.
   * @return {@link Optional} with the resulting {@link ShippingCategory} or an {@link Optional#empty()}.
   */
  public Optional<ShippingCategory> getShippingCategoryForSku(String id) {
    LOG.debug("Fetching shipping category for SKU '{}'.", id);
    Optional<DataEntity<ShippingCategory>> responseEntity = getConnector().getResource(SKUS_SHIPPING_CATEGORY_PATH, Map.of(ID_PARAM, id), RESPONSE_TYPE_DATA_ENTITY_SHIPPING_CATEGORY);
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
    if (StringUtils.isBlank(searchTerm)) {
      return Collections.emptyList();
    }

    LOG.debug("Searching SKUs for search term '{}'.", searchTerm);
    Map<String, String> additionalQueryParams = Map.of(
            SKU_SEARCH_FILTER, searchTerm,
            INCLUDE_PARAM, SKU_DEFAULT_INCLUDES_VALUE);
    List<SKU> hits = fetchAllPages(SKUS_PATH, RESPONSE_TYPE_PAGINATED_ENTITY_SKU, additionalQueryParams);
    LOG.debug("Found {} SKUs for search term '{}'.", hits.size(), searchTerm);
    return hits;
  }

}
