package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.PaginatedEntity;
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
  protected static int DEFAULT_PAGE_SIZE = 20;
  protected static int MAX_PAGE = 100;
  protected static final String INCLUDE_PARAM = "include";

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

      Optional<PaginatedEntity<T>> responseEntity = getConnector().getResource(resourcePath, Collections.emptyMap(), queryParams, responseType);
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
