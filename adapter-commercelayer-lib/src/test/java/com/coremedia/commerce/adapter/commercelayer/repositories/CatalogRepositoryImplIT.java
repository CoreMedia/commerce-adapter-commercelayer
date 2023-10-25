package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.Catalog;
import com.coremedia.commerce.adapter.base.entities.EntityParams;
import com.coremedia.commerce.adapter.base.entities.ExternalId;
import com.coremedia.commerce.adapter.base.entities.IdQuery;
import com.coremedia.commerce.adapter.base.repositories.CatalogRepository;
import com.coremedia.commerce.adapter.commercelayer.AbstractCommerceLayerIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        AbstractCommerceLayerIT.TestConfig.class,
        CatalogRepositoryImpl.class
})
public class CatalogRepositoryImplIT extends AbstractCommerceLayerIT {

    private static final EntityParams ENTITY_PARAMS = EntityParams.builder().setLocale(Locale.ENGLISH).build();

    @Autowired
    private CatalogRepository catalogRepository;

    @Test
    public void testGetCatalogs() {
        Iterable<Catalog> catalogs = catalogRepository.getCatalogs(ENTITY_PARAMS);
        assertThat(catalogs).hasSize(1);
    }

    @Test
    public void testGetCatalogById() {
        IdQuery idQuery = IdQuery.from(ExternalId.of("commercelayer"), ENTITY_PARAMS);
        Optional<Catalog> catalog = catalogRepository.getCatalogById(idQuery);
        assertTrue(catalog.isPresent());
    }

}
