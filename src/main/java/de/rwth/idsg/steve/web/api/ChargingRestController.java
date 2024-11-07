/*
 * SteVe - SteckdosenVerwaltung - https://github.com/steve-community/steve
 * Copyright (C) 2013-2024 SteVe Community Team
 * All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package de.rwth.idsg.steve.web.api;

import de.rwth.idsg.steve.repository.ChargePointRepository;
import de.rwth.idsg.steve.service.ChargePointService;
import de.rwth.idsg.steve.web.api.ApiControllerAdvice.ApiErrorResponse;
import de.rwth.idsg.steve.web.api.exception.BadRequestException;
import de.rwth.idsg.steve.web.dto.ocpp.RemoteStartTransactionParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ocpp.cp._2015._10.RemoteStartStopStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/charging", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class ChargingRestController {

    private final ChargePointService chargePointService;
    private final ChargePointRepository chargePointRepository;

    @Operation(summary = "Start a charging transaction with optional duration")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request", 
                    content = {@Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ApiErrorResponse.class))}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", 
                    content = {@Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ApiErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", 
                    content = {@Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping(value = "/start")
    @ResponseBody
    public StartTransactionResponse startTransaction(@Valid @RequestBody StartTransactionRequest request) 
            throws ExecutionException, InterruptedException {
        log.debug("Start transaction request: {}", request);

        if (!chargePointRepository.exists(request.getChargeBoxId())) {
            throw new BadRequestException("Charge point not found: " + request.getChargeBoxId());
        }

        RemoteStartTransactionParams params = new RemoteStartTransactionParams();
        params.setConnectorId(request.getConnectorId());
        params.setIdTag(request.getIdTag());
        // params.setDurationInSeconds(request.getDurationInSeconds());

        RemoteStartStopStatus status = chargePointService.remoteStartTransaction(request.getChargeBoxId(), params);
        
        return StartTransactionResponse.builder()
                .status(status.value())
                .build();
    }

    @Operation(summary = "Stop a charging transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request", 
                    content = {@Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ApiErrorResponse.class))}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", 
                    content = {@Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ApiErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", 
                    content = {@Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping(value = "/stop")
    @ResponseBody
    public StopTransactionResponse stopTransaction(@Valid @RequestBody StopTransactionRequest request) 
            throws ExecutionException, InterruptedException {
        log.debug("Stop transaction request: {}", request);

        if (!chargePointRepository.exists(request.getChargeBoxId())) {
            throw new BadRequestException("Charge point not found: " + request.getChargeBoxId());
        }

        RemoteStartStopStatus status = chargePointService.remoteStopTransaction(request.getChargeBoxId(), request.getTransactionId());
        
        return StopTransactionResponse.builder()
                .status(status.value())
                .build();
    }
}

@lombok.Data
@lombok.Builder
class StartTransactionRequest {
    @NotNull(message = "chargeBoxId is required")
    private String chargeBoxId;

    @NotNull(message = "connectorId is required")
    private Integer connectorId;

    @NotNull(message = "idTag is required")
    private String idTag;

    private Integer durationInSeconds;
}

@lombok.Data
@lombok.Builder
class StopTransactionRequest {
    @NotNull(message = "chargeBoxId is required")
    private String chargeBoxId;

    @NotNull(message = "transactionId is required")
    private Integer transactionId;
}

@lombok.Data
@lombok.Builder
class StartTransactionResponse {
    private String status;
}

@lombok.Data
@lombok.Builder
class StopTransactionResponse {
    private String status;
}