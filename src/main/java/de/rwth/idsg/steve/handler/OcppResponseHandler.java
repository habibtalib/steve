/*
 * SteVe - SteckdosenVerwaltung - https://github.com/steve-community/steve
 * Copyright (C) 2013-2024 SteVe Community Team
 * All Rights Reserved.
 */
package de.rwth.idsg.steve.handler;

import de.rwth.idsg.steve.ocpp.OcppCallback;
import de.rwth.idsg.steve.ocpp.ws.data.OcppJsonError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OcppResponseHandler<T> implements OcppCallback<T> {

    private final String chargeBoxId;
    private final String response;

    public OcppResponseHandler(String chargeBoxId, String response) {
        this.chargeBoxId = chargeBoxId;
        this.response = response;
    }

    @Override
    public void success(String chargeBoxId, T response) {
        log.debug("Received response for {}: {}", chargeBoxId, response);
    }

    @Override
    public void failed(String chargeBoxId, Exception e) {
        log.error("Failed to receive response for {}", chargeBoxId, e);
    }

    @Override
    public void success(String chargeBoxId, OcppJsonError error) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'success'");
    }
}