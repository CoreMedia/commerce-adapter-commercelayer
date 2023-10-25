package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.EntityParams;
import com.coremedia.commerce.adapter.base.entities.ExternalId;
import com.coremedia.commerce.adapter.base.entities.IdQuery;
import com.coremedia.commerce.adapter.base.entities.Product;
import com.coremedia.commerce.adapter.base.repositories.ProductRepository;
import com.coremedia.commerce.adapter.commercelayer.AbstractCommerceLayerIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
        AbstractCommerceLayerIT.TestConfig.class,
        ProductRepositoryImpl.class
})
public class ProductRepositoryImplIT extends AbstractCommerceLayerIT {

    private static final EntityParams ENTITY_PARAMS = EntityParams.builder().setLocale(Locale.ENGLISH).build();

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testGetProducts() {
        Iterable<Product> products = productRepository.getProducts(ENTITY_PARAMS);
        assertThat(products).hasSize(1);
    }

    @Test
    public void testGetProductById() {
        IdQuery idQuery = IdQuery.from(ExternalId.of("WPwySLNVdQ"), ENTITY_PARAMS);
        Optional<Product> product = productRepository.getProductById(idQuery);
        assertTrue(product.isPresent());
        assertEquals("WPwySLNVdQ", product.get().getExternalId().getValue());
        assertEquals("Black Men T-Shirt with White Logo (L)", product.get().getName());
        assertEquals("foo", product.get().getCategoryId().getValue());
    }

}
