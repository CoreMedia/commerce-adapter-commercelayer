package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.*;
import com.coremedia.commerce.adapter.base.repositories.CategoryRepository;
import com.coremedia.commerce.adapter.commercelayer.AbstractCommerceLayerIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
        AbstractCommerceLayerIT.TestConfig.class,
        CategoryRepositoryImpl.class
})
public class CategoryRepositoryImplIT extends AbstractCommerceLayerIT {

    private static final EntityParams ENTITY_PARAMS = EntityParams.builder().setLocale(Locale.ENGLISH).build();
    private static String SHIPPING_CATEGORY_ID = "zwzQeFeeoN";
    
    private static String SHIPPING_CATEGORY_NAME = "zwzQeFeeoN";
    private static String SKU_LIST_ID = "yRXZIeLBjn";

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testGetCategories() {
        Iterable<Category> categories = categoryRepository.getCategories(ENTITY_PARAMS);
        assertThat(categories).hasSize(1);
    }

    @Test
    public void testGetShippingCategoryCategoryById() {
        IdQuery idQuery = IdQuery.from(ExternalId.of(SHIPPING_CATEGORY_ID), ENTITY_PARAMS);
        Optional<Category> category = categoryRepository.getCategoryById(idQuery);
        assertTrue(category.isPresent());
    }

    @Test
    public void testSKUListCategoryCategoryById() {
        IdQuery idQuery = IdQuery.from(ExternalId.of(SKU_LIST_ID), ENTITY_PARAMS);
        Optional<Category> category = categoryRepository.getCategoryById(idQuery);
        assertTrue(category.isPresent());
    }
    @Test
    public void testGetCategoryBySeoSegment() {
        SeoSegmentQuery seoSegmentQuery = SeoSegmentQuery.from("shipping_category_1", ENTITY_PARAMS);
        Optional<Category> category = categoryRepository.getCategoryBySeoSegment(seoSegmentQuery);
        assertTrue(category.isPresent());
    }

}
