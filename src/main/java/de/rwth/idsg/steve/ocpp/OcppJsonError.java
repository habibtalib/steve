/*
 * SteVe - SteckdosenVerwaltung - https://github.com/steve-community/steve
 * Copyright (C) 2013-2024 SteVe Community Team
 * All Rights Reserved.
 */
package de.rwth.idsg.steve.ocpp;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OcppJsonError {
    private final String errorCode;
    private final String errorDescription;
    private final String errorDetails;

    public OcppJsonError(String errorCode, String errorDescription, String errorDetails) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorDetails = errorDetails;
    }

    public static OcppJsonError fromValues(String errorCode, String errorDescription, String errorDetails) {
        return new OcppJsonError(errorCode, errorDescription, errorDetails);
    }
}