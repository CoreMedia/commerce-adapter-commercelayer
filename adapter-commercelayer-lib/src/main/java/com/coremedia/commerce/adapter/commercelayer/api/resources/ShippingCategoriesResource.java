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
 * REST resource for <a href="https://docs.commercelayer.io/core/v/api-reference/shipping_categories/object">Shipping Categories</a> retrieval.
 */
public class ShippingCategoriesResource extends CommerceLayerApiResource {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static final String SHIPPING_CATEGORIES_PATH = "/shipping_categories";
  private static final String SHIPPING_CATEGORIES_ID_PATH = SHIPPING_CATEGORIES_PATH + "/{id}";
  private static final String SHIPPING_CATEGORIES_SKUS_PATH = SHIPPING_CATEGORIES_ID_PATH + "/skus";

  private static final String SHIPPING_CATEGORY_SEARCH_FILTER = "filter[q][reference_eq]"; // see: https://docs.commercelayer.io/core/filtering-data


  public ShippingCategoriesResource(CommerceLayerApiConnector connector) {
    super(connector);
  }

  /**
   * Fetch all shipping categories.
   *
   * @return list of all shipping categories.
   */
  public List<ShippingCategory> listShippingCategories() {
    LOG.debug("Fetching all shipping categories.");
    List<ShippingCategory> shippingCategories = fetchAllPages(SHIPPING_CATEGORIES_PATH, RESPONSE_TYPE_PAGINATED_ENTITY_SHIPPING_CATEGORY);
    LOG.debug("Fetched {} shipping categories.", shippingCategories.size());
    return shippingCategories;
  }

  /**
   * Retrieve a shipping category by its id.
   *
   * @param id the shipping category id.
   * @return {@link Optional} with the resulting {@link ShippingCategory} or an {@link Optional#empty()}.
   */
  public Optional<ShippingCategory> getShippingCategory(String id) {
    LOG.debug("Fetching shipping category with id {}.", id);
    Optional<DataEntity<ShippingCategory>> responseEntity = getConnector().getResource("/shipping_categories/{id}", Map.of(ID_PARAM, id), RESPONSE_TYPE_DATA_ENTITY_SHIPPING_CATEGORY);
    return responseEntity.map(DataEntity::getData);
  }

  /**
   * Get a list of associated {@link SKU}s for the given shipping category id.
   *
   * @param id the shipping category id.
   * @return list of associated {@link SKU}s.
   */
  public List<SKU> getAssociatedSkus(String id) {
    LOG.debug("Fetching associated SKUs for category {}.", id);
    Map<String, String> additionalQueryParams = Map.of(INCLUDE_PARAM, SKU_DEFAULT_INCLUDES_VALUE);
    List<SKU> skus = fetchAllPages(SHIPPING_CATEGORIES_SKUS_PATH, Map.of(ID_PARAM, id), RESPONSE_TYPE_PAGINATED_ENTITY_SKU, additionalQueryParams);
    LOG.debug("Fetched {} associated SKUs for category {}.", skus.size(), id);
    return skus;
  }

  /**
   * Search shipping categories by the given search term.
   *
   * @param searchTerm search term
   * @return a list of matching {@link ShippingCategory}s or an empty list.
   */
  public List<ShippingCategory> searchShippingCategories(@NonNull String searchTerm) {
    if (StringUtils.isBlank(searchTerm)) {
      return Collections.emptyList();
    }

    // TODO: Search for a match in attributes name or reference
    LOG.debug("Searching Shipping Categories for search term '{}'.", searchTerm);
    Map<String, String> additionalQueryParams = Map.of(SHIPPING_CATEGORY_SEARCH_FILTER, searchTerm);
    List<ShippingCategory> hits = fetchAllPages(SHIPPING_CATEGORIES_PATH, RESPONSE_TYPE_PAGINATED_ENTITY_SHIPPING_CATEGORY, additionalQueryParams);
    LOG.debug("Found {} shipping categories for search term '{}'.", hits.size(), searchTerm);
    return hits;
  }

}
