package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.*;
import com.coremedia.commerce.adapter.base.repositories.ProductRepository;
import com.coremedia.commerce.adapter.commercelayer.api.entities.CommerceLayerApiEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.RelationshipEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import com.coremedia.commerce.adapter.commercelayer.api.resources.SKUResource;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@DefaultAnnotation(NonNull.class)
public class ProductRepositoryImpl implements ProductRepository {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private SKUResource skuResource;

  public ProductRepositoryImpl(SKUResource skuResource) {
    this.skuResource = skuResource;
  }

  @Override
  public Optional<Product> getProductById(IdQuery idQuery) {
    LOG.debug("Fetching product for id query: {}.", idQuery);
    return skuResource.getSkuWithInclude(idQuery.getId().getValue()).map(this::toProduct);
  }

  @Override
  public Optional<Product> getProductBySeoSegment(SeoSegmentQuery seoSegmentQuery) {
    LOG.debug("Fetching product for seo segment query: {}.", seoSegmentQuery);
    return ProductRepository.super.getProductBySeoSegment(seoSegmentQuery);
  }

  @Override
  public Iterable<Product> getProducts(EntityParams entityParams) {
    LOG.debug("Fetching all products.");
    List<SKU> skus = skuResource.listSKUs();
    List<Product> products = skus.stream().map(this::toProduct).collect(Collectors.toList());
    LOG.debug("Fetched products: {}.", products.size());
    return products;
  }

  @Override
  public SearchResult search(SearchQuery searchQuery) {
    List<SKU> results = skuResource.searchSkus(searchQuery.getSearchTerm().get());
    List<Id> skuIds = results.stream()
            .map(CommerceLayerApiEntity::getId)
            .map(ExternalId::of)
            .collect(Collectors.toList());
    return SearchResult.builder().setTotalCount(results.size()).setEntityIds(skuIds).build();
  }


  // --- private stuff ---

  private Product toProduct(@NonNull SKU sku) {
    String shippingCategoryId = getShippingCategoryFromRelationship(sku);
    return buildProductInternal(sku.getId(), shippingCategoryId, sku.getAttributes());
  }

  private Product toProduct(@NonNull DataEntity<SKU> dataEntity) {
    SKU sku = dataEntity.getData();
    String shippingCategoryId = dataEntity.getIncluded().stream().filter(include -> include.getType().equals("shipping_categories")).findFirst().map(DataEntity.Include::getId).orElse(null);
    return buildProductInternal(sku.getId(), shippingCategoryId, sku.getAttributes());
  }

  private Product buildProductInternal(@NonNull String productId, @Nullable String categoryId, @NonNull SKU.Attributes skuAttributes) {
    ExternalId externalId = ExternalId.of(productId);
    Id externalCategoryId = ExternalId.of(StringUtils.isNotBlank(categoryId) ? categoryId : "_no-category");
    String name = skuAttributes.getName();
    if (StringUtils.isBlank(name)) {
      name = productId;
    }

    ProductBuilder productBuilder = Product.builder(externalId, name, externalCategoryId);
    productBuilder.setTitle(name);
    productBuilder.setLongDescription(skuAttributes.getDescription());
    productBuilder.setShortDescription(skuAttributes.getDescription());
    productBuilder.setTechId(TechId.of(skuAttributes.getCode()));
    productBuilder.setDefaultImageUrl(skuAttributes.getImageUrl());
    productBuilder.setThumbnailImageUrl(skuAttributes.getImageUrl());

    return productBuilder.build();
  }

  private static String getShippingCategoryFromRelationship(SKU sku) {
    return Optional.ofNullable(sku.getRelationships().getShippingCategory())
            .map(RelationshipEntity::getData)
            .filter(Map.class::isInstance)
            .map(Map.class::cast)
            .filter(m -> m.containsKey("id") && m.containsKey("type") && m.get("type").equals("shipping_categories"))
            .map(m -> m.get("id"))
            .filter(String.class::isInstance)
            .map(String.class::cast)
            .orElse(null);
  }

}
