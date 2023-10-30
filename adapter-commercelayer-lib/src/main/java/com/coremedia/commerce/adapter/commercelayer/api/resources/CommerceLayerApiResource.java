package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;

/**
 * Abstract base class for all Commerce Layer API resources.
 */
public abstract class CommerceLayerApiResource {

    protected static final String ID_PARAM = "id";

    private CommerceLayerApiConnector connector;

    public CommerceLayerApiResource(CommerceLayerApiConnector connector) {
        this.connector = connector;
    }

    public CommerceLayerApiConnector getConnector() {
        return connector;
    }
}
