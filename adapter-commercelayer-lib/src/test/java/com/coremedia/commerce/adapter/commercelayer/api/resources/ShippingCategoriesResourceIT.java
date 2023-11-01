package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.AbstractCommerceLayerIT;
import com.coremedia.commerce.adapter.commercelayer.api.entities.ShippingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {
        AbstractCommerceLayerIT.TestConfig.class
})
public class ShippingCategoriesResourceIT extends AbstractCommerceLayerIT {
  @Autowired
  private ShippingCategoriesResource testling;
  
  @Test
  public void testSearchShippingCategories() {
    Optional<ShippingCategory> shippingCategory = testling.searchShippingCategories("shipping_category_1").stream().findFirst();
    validateShippingCategory(shippingCategory);
  }
  
  private static void validateShippingCategory(Optional<ShippingCategory> shippingCategory) {
    assertTrue(shippingCategory.isPresent());
  }
}
