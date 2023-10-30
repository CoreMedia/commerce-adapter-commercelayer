package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.*;
import com.coremedia.commerce.adapter.base.repositories.ProductRepository;
import com.coremedia.commerce.adapter.commercelayer.api.entities.CommerceLayerApiEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import com.coremedia.commerce.adapter.commercelayer.api.entities.ShippingCategory;
import com.coremedia.commerce.adapter.commercelayer.api.resources.SKUResource;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.util.List;
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
        return skuResource.getSku(idQuery.getId().getValue()).map(this::toProduct);
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
        ExternalId externalId = ExternalId.of(sku.getId());
        Optional<ShippingCategory> shippingCategory = skuResource.getShippingCategoryForSku(sku.getId());
        Id categoryId = ExternalId.of(shippingCategory.map(ShippingCategory::getId).orElse("_no-category"));
        SKU.Attributes skuAttributes = sku.getAttributes();
        String name = skuAttributes.getName();

        if (StringUtils.isBlank(name)) {
            name = sku.getId();
        }

        ProductBuilder productBuilder = Product.builder(externalId, name, categoryId);
        productBuilder.setTitle(name);
        productBuilder.setLongDescription(skuAttributes.getDescription());
        productBuilder.setShortDescription(skuAttributes.getDescription());
        productBuilder.setTechId(TechId.of(skuAttributes.getCode()));
        productBuilder.setDefaultImageUrl(skuAttributes.getImageUrl());
        productBuilder.setThumbnailImageUrl(skuAttributes.getImageUrl());

        return productBuilder.build();
    }


}
