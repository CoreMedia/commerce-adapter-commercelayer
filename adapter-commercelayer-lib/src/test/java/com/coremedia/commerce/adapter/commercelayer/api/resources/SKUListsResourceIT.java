package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.AbstractCommerceLayerIT;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKUList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        AbstractCommerceLayerIT.TestConfig.class
})
public class SKUListsResourceIT extends AbstractCommerceLayerIT {

    @Autowired
    SKUListsResource skuListsResource;

    @Test
    public void testListSkuLists() {
        List<SKUList> result = skuListsResource.listSkuLists();
        assertNotNull(result);
        assertEquals(1, result.size());
        validateSkuList(result.get(0));
    }

    @Test
    public void testRetrieveSkuList() {
        Optional<SKUList> skuList = skuListsResource.getSkuList("yRXZIeLBjn");
        assertTrue(skuList.isPresent());
        validateSkuList(skuList.get());
    }

    private void validateSkuList(SKUList skuList) {
        assertEquals("New Arrivals", skuList.getAttributes().getName());
    }


}
