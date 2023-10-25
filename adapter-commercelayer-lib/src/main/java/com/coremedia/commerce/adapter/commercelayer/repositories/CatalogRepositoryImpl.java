package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.Catalog;
import com.coremedia.commerce.adapter.base.entities.EntityParams;
import com.coremedia.commerce.adapter.base.entities.IdQuery;
import com.coremedia.commerce.adapter.base.repositories.CatalogRepository;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Repository
@DefaultAnnotation(NonNull.class)
public class CatalogRepositoryImpl implements CatalogRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Optional<Catalog> getCatalogById(IdQuery idQuery) {
        return Optional.empty();
    }

    @Override
    public Iterable<Catalog> getCatalogs(EntityParams entityParams) {
        return CatalogRepository.super.getCatalogs(entityParams);
    }

}
