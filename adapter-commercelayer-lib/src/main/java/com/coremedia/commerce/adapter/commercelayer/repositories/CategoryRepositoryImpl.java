package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.*;
import com.coremedia.commerce.adapter.base.repositories.CategoryRepository;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import com.coremedia.commerce.adapter.commercelayer.api.entities.ShippingCategory;
import com.coremedia.commerce.adapter.commercelayer.api.resources.SKUListsResource;
import com.coremedia.commerce.adapter.commercelayer.api.resources.SKUResource;
import com.coremedia.commerce.adapter.commercelayer.api.resources.ShippingCategoriesResource;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@DefaultAnnotation(NonNull.class)
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ShippingCategoriesResource shippingCategoriesResource;
    private SKUListsResource skuListsResource;
    private SKUResource skuResource;

    public CategoryRepositoryImpl(@NonNull ShippingCategoriesResource shippingCategoriesResource,
                                  @NonNull SKUListsResource skuListsResource,
                                  @NonNull SKUResource skuResource) {
        this.shippingCategoriesResource = shippingCategoriesResource;
        this.skuListsResource = skuListsResource;
        this.skuResource = skuResource;
    }

    @Override
    public Iterable<Category> getCategories(EntityParams entityParams) {
        LOG.debug("Fetching all categories.");
        List<ShippingCategory> shippingCategories = shippingCategoriesResource.listShippingCategories();
        return shippingCategories.stream().map(this::toCategory).collect(Collectors.toList());
    }

    @Override
    public Optional<Category> getCategoryById(IdQuery idQuery) {
        LOG.debug("Fetching category for id query: {}.", idQuery);
        Optional<Category> category = shippingCategoriesResource.getShippingCategory(idQuery.getId().getValue()).map(this::toCategory);
        return category;
    }

    @Override
    public Optional<Category> getCategoryBySeoSegment(SeoSegmentQuery seoSegmentQuery) {
        LOG.debug("Fetching category for seo segment query: {}.", seoSegmentQuery);
        String seoSegment = seoSegmentQuery.getSeoSegment();
        // TODO: Maybe use the "reference" field or "name" instead of the id to find the category
        Optional<Category> category = shippingCategoriesResource.getShippingCategory(seoSegment).map(this::toCategory);
        return category;
    }

    private Category toCategory(ShippingCategory shippingCategory) {
        ExternalId id = ExternalId.of(shippingCategory.getId());
        String name = shippingCategory.getAttributes().getName();
        CategoryBuilder categoryBuilder = Category.builder(id, name);
        categoryBuilder.setSeoSegment(shippingCategory.getAttributes().getReference());
        categoryBuilder.setTitle(name);

        // Add assigned products
        List<Id> assignedProducts = shippingCategoriesResource.getAssociatedSkus(shippingCategory.getId()).stream()
                .map(SKU::getId)
                .map(ExternalId::of)
                .collect(Collectors.toList());
        categoryBuilder.setProductIds(assignedProducts);

        return categoryBuilder.build();
    }

}
