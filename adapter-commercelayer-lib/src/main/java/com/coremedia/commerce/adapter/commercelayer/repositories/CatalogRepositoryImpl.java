package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.*;
import com.coremedia.commerce.adapter.base.repositories.CatalogRepository;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@DefaultAnnotation(NonNull.class)
public class CatalogRepositoryImpl implements CatalogRepository {

//    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Catalog masterCatalog;

    public CatalogRepositoryImpl() {
        ExternalId id = ExternalId.of("master_catalog");
        Id rootCategoryId = ExternalId.of("master");
        masterCatalog = Catalog.builder(id,"Master Catalog", rootCategoryId).build();
    }

    @Override
    public Optional<Catalog> getCatalogById(IdQuery idQuery) {
        return Optional.of(masterCatalog);
    }

    @Override
    public Iterable<Catalog> getCatalogs(EntityParams entityParams) {
        return List.of(masterCatalog);
    }

}
