package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.*;
import com.coremedia.commerce.adapter.base.repositories.CategoryRepository;
import com.coremedia.commerce.adapter.commercelayer.api.entities.CommerceLayerApiEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKUList;
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

    public static ExternalId ROOT_CATEGORY_ID = ExternalId.of("root");
    public static ExternalId SHIPPING_CATEGORIES_CATEGORY_ID = ExternalId.of("shipping-categories");
    public static ExternalId SKU_LISTS_CATEGORY_ID = ExternalId.of("sku-lists");

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ShippingCategoriesResource shippingCategoriesResource;
    private SKUListsResource skuListsResource;
    private SKUResource skuResource;

    /** Virtual categories **/
    private Category rootCategory;
    private Category shippingCategoriesCategory;
    private Category skuListsCategory;

    public CategoryRepositoryImpl(@NonNull ShippingCategoriesResource shippingCategoriesResource,
                                  @NonNull SKUListsResource skuListsResource,
                                  @NonNull SKUResource skuResource) {
        this.shippingCategoriesResource = shippingCategoriesResource;
        this.skuListsResource = skuListsResource;
        this.skuResource = skuResource;

        initVirtualCategories();
    }

    @Override
    public Iterable<Category> getCategories(EntityParams entityParams) {
        LOG.debug("Fetching categories for entity params: {}.", entityParams);
        List<ShippingCategory> shippingCategories = shippingCategoriesResource.listShippingCategories();
        return shippingCategories.stream().map(this::toCategory).collect(Collectors.toList());
    }

    @Override
    public Optional<Category> getCategoryById(IdQuery idQuery) {
        LOG.debug("Fetching category for id query: {}.", idQuery);

        Id categoryId = idQuery.getId();

        Optional<Category> category;
        if (ROOT_CATEGORY_ID.equals(categoryId)) {
            category = Optional.of(rootCategory);
        } else if (SHIPPING_CATEGORIES_CATEGORY_ID.equals(categoryId)) {
            category = Optional.of(shippingCategoriesCategory);
        } else if (SKU_LISTS_CATEGORY_ID.equals(categoryId)) {
            category = Optional.of(skuListsCategory);
        } else {
            // fetch category via API
            category = shippingCategoriesResource.getShippingCategory(categoryId.getValue()).map(this::toCategory);
        }

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

        // Set parent link to virtual "shipping categories" category
        categoryBuilder.setParentId(SHIPPING_CATEGORIES_CATEGORY_ID);

        // Add assigned products
        List<Id> assignedProducts = shippingCategoriesResource.getAssociatedSkus(shippingCategory.getId()).stream()
                .map(SKU::getId)
                .map(ExternalId::of)
                .collect(Collectors.toList());
        categoryBuilder.setProductIds(assignedProducts);

        return categoryBuilder.build();
    }

    private Category toCategory(SKUList skuList) {
        // TODO: Implement (see above #toCategory)
        return null;
    }

    private void initVirtualCategories() {
        rootCategory = createRootCategory();
        shippingCategoriesCategory = createShippingCategoriesCategory();
        skuListsCategory = createSkuListsCategory();
    }

    private Category createRootCategory() {
        CategoryBuilder categoryBuilder = Category.builder(ROOT_CATEGORY_ID, "Root");
        categoryBuilder.setChildIds(List.of(SHIPPING_CATEGORIES_CATEGORY_ID, SKU_LISTS_CATEGORY_ID));
        return categoryBuilder.build();
    }

    /**
     * Create a virtual category as an entry point
     * for all <a href="https://docs.commercelayer.io/core/v/api-reference/shipping_categories">Shipping Categories</a>.
     *
     * @return
     */
    private Category createShippingCategoriesCategory() {
        CategoryBuilder categoryBuilder = Category.builder(SHIPPING_CATEGORIES_CATEGORY_ID, "Shipping Categories");
        categoryBuilder.setParentId(rootCategory.getExternalId());

        // Add all direct shipping categories
        List<Id> shippingCategoryIds = shippingCategoriesResource.listShippingCategories().stream()
                .map(CommerceLayerApiEntity::getId)
                .map(ExternalId::of)
                .collect(Collectors.toList());
        categoryBuilder.setChildIds(shippingCategoryIds);

        return categoryBuilder.build();
    }

    private Category createSkuListsCategory() {
        CategoryBuilder categoryBuilder = Category.builder(SKU_LISTS_CATEGORY_ID, "SKU Lists");
        categoryBuilder.setParentId(rootCategory.getExternalId());

        // TODO: Fetch all SKU lists and add them like we do with shipping categories (see above)

        return categoryBuilder.build();
    }
}
