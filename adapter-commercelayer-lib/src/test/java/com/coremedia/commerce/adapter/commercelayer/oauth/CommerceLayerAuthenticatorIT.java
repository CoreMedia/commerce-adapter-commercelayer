package com.coremedia.commerce.adapter.commercelayer.oauth;

import com.coremedia.commerce.adapter.commercelayer.AbstractCommerceLayerIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {
        AbstractCommerceLayerIT.TestConfig.class
})
public class CommerceLayerAuthenticatorIT extends AbstractCommerceLayerIT {

    @Autowired
    private CommerceLayerAuthenticator testling;

    @Test
    public void testGetAccessToken() {
        AccessToken token = testling.getAccessToken();
        validateToken(token);
    }
    @Test
    public void testRequestNewToken() {
        AccessToken token = testling.requestNewToken();
        validateToken(token);
    }

    @Test
    public void testRefreshToken() {
        AccessToken token = testling.refreshAccessToken();
        validateToken(token);
    }

    private static void validateToken(AccessToken token) {
        assertNotNull(token);
        assertNotNull(token.getValue());
        assertEquals("Bearer", token.getTokenType());
        assertEquals("market:all", token.getScope());
        assertThat(token.getExpiresIn()).isNotZero();
        assertThat(token.getCreatedAt()).isNotZero();
    }

}
