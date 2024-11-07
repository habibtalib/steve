/*
 * SteVe - SteckdosenVerwaltung - https://github.com/steve-community/steve
 * Copyright (C) 2013-2024 SteVe Community Team
 * All Rights Reserved.
 */
package de.rwth.idsg.steve.ocpp.task;

import de.rwth.idsg.steve.ocpp.OcppCallback;
import de.rwth.idsg.steve.ocpp.OcppJsonError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringOcppCallback implements OcppCallback<String> {

    @Override
    public void success(String chargeBoxId, String response) {
        log.debug("Received response for {}: {}", chargeBoxId, response);
    }

    @Override
    public void failed(String chargeBoxId, Exception e) {
        log.error("Failed to receive response for {}", chargeBoxId, e);
    }

    @Override
    public void success(String chargeBoxId, OcppJsonError response) {
        log.debug("Received JSON response for {}: {}", chargeBoxId, response);
    }
}