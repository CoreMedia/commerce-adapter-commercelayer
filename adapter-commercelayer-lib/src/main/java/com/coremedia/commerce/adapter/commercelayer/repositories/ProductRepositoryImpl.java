package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.*;
import com.coremedia.commerce.adapter.base.repositories.ProductRepository;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Repository
@DefaultAnnotation(NonNull.class)
public class ProductRepositoryImpl implements ProductRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Optional<Product> getProductById(IdQuery idQuery) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> getProductBySeoSegment(SeoSegmentQuery seoSegmentQuery) {
        return ProductRepository.super.getProductBySeoSegment(seoSegmentQuery);
    }

    @Override
    public Iterable<Product> getProducts(EntityParams entityParams) {
        return ProductRepository.super.getProducts(entityParams);
    }

    @Override
    public SearchResult search(SearchQuery searchQuery) {
        return null;
    }
}
