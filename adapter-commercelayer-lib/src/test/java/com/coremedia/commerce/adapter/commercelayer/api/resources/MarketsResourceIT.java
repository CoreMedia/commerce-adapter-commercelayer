package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.AbstractCommerceLayerIT;
import com.coremedia.commerce.adapter.commercelayer.api.entities.Market;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {
        AbstractCommerceLayerIT.TestConfig.class
})
public class MarketsResourceIT extends AbstractCommerceLayerIT {
  
  @Autowired
  private MarketsResource testling;
  
  @Test
  public void getMarketId() {
    Optional<Market> market = testling.getMarket("BgwdGhdPKl");
    validateMarket(market);
  }

  private static void validateMarket(Optional<Market> market) {
    assertTrue(market.isPresent());
    assertEquals("BgwdGhdPKl", market.get().getId());
    assertEquals("USA", market.get().getAttributes().getName());
  }

}
