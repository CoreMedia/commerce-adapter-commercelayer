package com.coremedia.commerce.adapter.commercelayer.repositories;

import com.coremedia.commerce.adapter.base.entities.Category;
import com.coremedia.commerce.adapter.base.entities.EntityParams;
import com.coremedia.commerce.adapter.base.entities.IdQuery;
import com.coremedia.commerce.adapter.base.entities.SeoSegmentQuery;
import com.coremedia.commerce.adapter.base.repositories.CategoryRepository;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Repository
@DefaultAnnotation(NonNull.class)
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Optional<Category> getCategoryById(IdQuery idQuery) {
        return Optional.empty();
    }

    @Override
    public Optional<Category> getCategoryBySeoSegment(SeoSegmentQuery seoSegmentQuery) {
        return CategoryRepository.super.getCategoryBySeoSegment(seoSegmentQuery);
    }

    @Override
    public Iterable<Category> getCategories(EntityParams entityParams) {
        return CategoryRepository.super.getCategories(entityParams);
    }

}
