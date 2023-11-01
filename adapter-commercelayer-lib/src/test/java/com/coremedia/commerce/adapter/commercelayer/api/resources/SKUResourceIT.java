package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.AbstractCommerceLayerIT;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {
        AbstractCommerceLayerIT.TestConfig.class
})
public class SKUResourceIT extends AbstractCommerceLayerIT {
  @Autowired
  private SKUResource testling;
  
  @Test
  public void testSearchSKUs() {
    List<SKU> sku = testling.searchSkus("Black Men T-Shirt with White Logo (L)");
    assertTrue(!(sku.isEmpty()));
  
  }
  
}
