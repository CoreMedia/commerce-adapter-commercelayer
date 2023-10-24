package com.coremedia.commerce.adapter.commercelayer.oauth;

import com.coremedia.commerce.adapter.commercelayer.AbstractCommerceLayerIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {
        AbstractCommerceLayerIT.TestConfig.class
})
public class CommerceLayerAuthenticatorIT extends AbstractCommerceLayerIT {

    @Autowired
    private CommerceLayerAuthenticator commerceLayerAuthenticator;

    @Test
    public void testRequestNewToken() {
        AccessToken token = commerceLayerAuthenticator.requestNewToken();
        assertNotNull(token);
        assertNotNull(token.getValue());
        assertEquals("Bearer", token.getTokenType());
        assertEquals("market:all", token.getScope());
    }

}
