package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.PaginatedEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import com.coremedia.commerce.adapter.commercelayer.api.entities.ShippingCategory;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract base class for all Commerce Layer API resources.
 */
public abstract class CommerceLayerApiResource {

  protected static final String ID_PARAM = "id";
  protected static final String PAGE_NUMBER_PARAM = "page[number]";
  protected static final String PAGE_SIZE_PARAM = "page[size]";
  protected static int DEFAULT_PAGE_SIZE = 25;
  protected static int MAX_PAGE = 100;
  protected static final String INCLUDE_PARAM = "include";
  protected static final List<String> SKU_DEFAULT_INCLUDES = List.of("shipping_category", "prices");
  protected static final String SKU_DEFAULT_INCLUDES_VALUE = String.join(",", SKU_DEFAULT_INCLUDES);


  // --- response types --
  protected static final ParameterizedTypeReference<DataEntity<ShippingCategory>> RESPONSE_TYPE_DATA_ENTITY_SHIPPING_CATEGORY = new ParameterizedTypeReference<>() {};
  protected static final ParameterizedTypeReference<DataEntity<SKU>> RESPONSE_TYPE_DATA_ENTITY_SKU = new ParameterizedTypeReference<>() {};
  protected static final ParameterizedTypeReference<PaginatedEntity<ShippingCategory>> RESPONSE_TYPE_PAGINATED_ENTITY_SHIPPING_CATEGORY = new ParameterizedTypeReference<>() {};
  protected static final ParameterizedTypeReference<PaginatedEntity<SKU>> RESPONSE_TYPE_PAGINATED_ENTITY_SKU = new ParameterizedTypeReference<>() {};


  private CommerceLayerApiConnector connector;

  public CommerceLayerApiResource(CommerceLayerApiConnector connector) {
    this.connector = connector;
  }

  public CommerceLayerApiConnector getConnector() {
    return connector;
  }

  protected <T> List<T> fetchAllPages(@NonNull String resourcePath,
                                      @NonNull ParameterizedTypeReference<PaginatedEntity<T>> responseType,
                                      @Nullable Map<String, String> additionalQueryParams) {
    return fetchAllPages(resourcePath, Collections.emptyMap(), responseType, additionalQueryParams);
  }
  protected <T> List<T> fetchAllPages(@NonNull String resourcePath,
                                      @NonNull Map<String, String> pathParams,
                                      @NonNull ParameterizedTypeReference<PaginatedEntity<T>> responseType,
                                      @Nullable Map<String, String> additionalQueryParams) {
    List<T> result = new ArrayList<>();

    int page = 1;
    boolean done = false;
    while (!done) {
      ListMultimap<String, String> queryParams = ArrayListMultimap.create();
      queryParams.put(PAGE_NUMBER_PARAM, String.valueOf(page));
      queryParams.put(PAGE_SIZE_PARAM, String.valueOf(DEFAULT_PAGE_SIZE));
      if (MapUtils.isNotEmpty(additionalQueryParams)) {
        additionalQueryParams.forEach(queryParams::put);
      }

      Optional<PaginatedEntity<T>> responseEntity = getConnector().getResource(resourcePath, pathParams, queryParams, responseType);
      responseEntity.map(PaginatedEntity::getData).ifPresent(result::addAll);
      done = responseEntity.isEmpty() || !responseEntity.get().hasNext() || page > MAX_PAGE;
      page++;
    }
    return result;
  }

  protected <T> List<T> fetchAllPages(@NonNull String resourcePath,
                                      @NonNull ParameterizedTypeReference<PaginatedEntity<T>> responseType) {
    return fetchAllPages(resourcePath, responseType, Collections.emptyMap());
  }

}
