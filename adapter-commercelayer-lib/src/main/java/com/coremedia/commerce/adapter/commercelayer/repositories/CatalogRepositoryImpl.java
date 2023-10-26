package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.*;
import com.coremedia.commerce.adapter.base.repositories.CatalogRepository;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import static com.coremedia.commerce.adapter.commercelayer.repositories.CategoryRepositoryImpl.ROOT_CATEGORY_ID;

@Repository
@DefaultAnnotation(NonNull.class)
public class CatalogRepositoryImpl implements CatalogRepository {

    public static final ExternalId MASTER_CATALOG_ID = ExternalId.of("master_catalog");
    public static final String MASTER_CATALOG_NAME = "Master Catalog";

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Catalog masterCatalog;

    public CatalogRepositoryImpl() {
        masterCatalog = Catalog.builder(MASTER_CATALOG_ID, MASTER_CATALOG_NAME, ROOT_CATEGORY_ID).build();
    }

    @Override
    public Optional<Catalog> getCatalogById(IdQuery idQuery) {
        LOG.debug("Fetching catalog for id query: {}.", idQuery);
        return Optional.of(masterCatalog);
    }

    @Override
    public Iterable<Catalog> getCatalogs(EntityParams entityParams) {
        LOG.debug("Fetching catalogs for entity params: {}.", entityParams);
        return List.of(masterCatalog);
    }

}
