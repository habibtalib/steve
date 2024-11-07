package de.rwth.idsg.steve.service;
import de.rwth.idsg.steve.web.dto.ocpp.RemoteStartTransactionParams;
import ocpp.cp._2015._10.RemoteStartStopStatus;
import java.util.concurrent.ExecutionException;
public interface ChargePointService {
    RemoteStartStopStatus remoteStartTransaction(String chargeBoxId, RemoteStartTransactionParams params) 
            throws ExecutionException, InterruptedException;
            
    RemoteStartStopStatus remoteStopTransaction(String chargeBoxId, Integer transactionId) 
            throws ExecutionException, InterruptedException;
}