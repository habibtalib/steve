/*
 * SteVe - SteckdosenVerwaltung - https://github.com/steve-community/steve
 * Copyright (C) 2013-2024 SteVe Community Team
 * All Rights Reserved.
 */
package de.rwth.idsg.steve.ocpp;

/**
 * Generic callback interface for OCPP operations
 *
 * @param <T> The type of the success response
 */
public interface OcppCallback<T> {
    /**
     * Called when operation succeeds with normal response
     */
    void success(String chargeBoxId, T response);

    /**
     * Called when operation succeeds with JSON error response
     */
    void success(String chargeBoxId, OcppJsonError error);

    /**
     * Called when operation fails with exception
     */
    void failed(String chargeBoxId, Exception e);
}