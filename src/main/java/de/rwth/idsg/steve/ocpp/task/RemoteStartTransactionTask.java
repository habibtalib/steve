/*
 * SteVe - SteckdosenVerwaltung - https://github.com/steve-community/steve
 * Copyright (C) 2013-2024 SteVe Community Team
 * All Rights Reserved.
 */
package de.rwth.idsg.steve.ocpp.task;

import de.rwth.idsg.steve.ocpp.CommunicationTask;
import de.rwth.idsg.steve.ocpp.OcppCallback;
import de.rwth.idsg.steve.ocpp.OcppVersion;
import de.rwth.idsg.steve.web.dto.ocpp.RemoteStartTransactionParams;
import lombok.extern.slf4j.Slf4j;
import ocpp.cp._2015._10.ChargingProfile;
import ocpp.cp._2015._10.ChargingProfileKindType;
import ocpp.cp._2015._10.ChargingProfilePurposeType;
import ocpp.cp._2015._10.ChargingRateUnitType;
import ocpp.cp._2015._10.ChargingSchedule;
import ocpp.cp._2015._10.ChargingSchedulePeriod;

import jakarta.xml.ws.AsyncHandler;
import java.math.BigDecimal;
import java.util.Collections;

@Slf4j
public class RemoteStartTransactionTask extends CommunicationTask<RemoteStartTransactionParams, String> {

    public RemoteStartTransactionTask(OcppVersion ocppVersion, RemoteStartTransactionParams params) {
        super(ocppVersion, params);
    }

    @Override
    public OcppCallback<String> defaultCallback() {
        return new StringOcppCallback();
    }

    @Override
    public ocpp.cp._2010._08.RemoteStartTransactionRequest getOcpp12Request() {
        return new ocpp.cp._2010._08.RemoteStartTransactionRequest()
                .withIdTag(params.getIdTag())
                .withConnectorId(params.getConnectorId());
    }

    @Override
    public ocpp.cp._2012._06.RemoteStartTransactionRequest getOcpp15Request() {
        return new ocpp.cp._2012._06.RemoteStartTransactionRequest()
                .withIdTag(params.getIdTag())
                .withConnectorId(params.getConnectorId());
    }

    @Override
    public ocpp.cp._2015._10.RemoteStartTransactionRequest getOcpp16Request() {
        ocpp.cp._2015._10.RemoteStartTransactionRequest request = new ocpp.cp._2015._10.RemoteStartTransactionRequest()
                .withIdTag(params.getIdTag())
                .withConnectorId(params.getConnectorId());

        if (params.getChargingDuration() != null) {
            ChargingSchedulePeriod period = new ChargingSchedulePeriod()
                .withStartPeriod(0)
                .withLimit(new BigDecimal("32.0"));

            ChargingSchedule schedule = new ChargingSchedule()
                .withDuration((Integer) params.getChargingDuration())
                .withChargingRateUnit(ChargingRateUnitType.W)
                .withChargingSchedulePeriod(Collections.singletonList(period));

            ChargingProfile profile = new ChargingProfile()
                .withChargingProfileId(1)
                .withStackLevel(0)
                .withChargingProfilePurpose(ChargingProfilePurposeType.TX_PROFILE)
                .withChargingProfileKind(ChargingProfileKindType.ABSOLUTE)
                .withChargingSchedule(schedule);
            
            request.setChargingProfile(profile);
        }

        return request;
    }

    @Override
    public AsyncHandler<ocpp.cp._2010._08.RemoteStartTransactionResponse> getOcpp12Handler(String chargeBoxId) {
        return res -> {
            try {
                success(chargeBoxId, res.get().getStatus().value());
            } catch (Exception e) {
                failed(chargeBoxId, e);
            }
        };
    }

    @Override
    public AsyncHandler<ocpp.cp._2012._06.RemoteStartTransactionResponse> getOcpp15Handler(String chargeBoxId) {
        return res -> {
            try {
                success(chargeBoxId, res.get().getStatus().value());
            } catch (Exception e) {
                failed(chargeBoxId, e);
            }
        };
    }

    @Override
    public AsyncHandler<ocpp.cp._2015._10.RemoteStartTransactionResponse> getOcpp16Handler(String chargeBoxId) {
        return res -> {
            try {
                success(chargeBoxId, res.get().getStatus().value());
            } catch (Exception e) {
                failed(chargeBoxId, e);
            }
        };
    }
}