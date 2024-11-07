package de.rwth.idsg.steve.ocpp;
import de.rwth.idsg.steve.ocpp.task.RemoteStartTransactionTask;
import de.rwth.idsg.steve.ocpp.task.RemoteStopTransactionTask;

public interface ChargePointService15_Client {
    void remoteStartTransaction(String chargeBoxId, RemoteStartTransactionTask task);
    void remoteStopTransaction(String chargeBoxId, RemoteStopTransactionTask task);
}
