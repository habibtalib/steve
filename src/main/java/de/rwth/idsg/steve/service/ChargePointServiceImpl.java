package de.rwth.idsg.steve.service;
import de.rwth.idsg.steve.handler.OcppResponseHandler;
import de.rwth.idsg.steve.ocpp.ChargePointService12_Client;
import de.rwth.idsg.steve.ocpp.ChargePointService15_Client;
import de.rwth.idsg.steve.ocpp.ChargePointService16_Client;
import de.rwth.idsg.steve.ocpp.OcppVersion;
import de.rwth.idsg.steve.ocpp.task.RemoteStartTransactionTask;
import de.rwth.idsg.steve.ocpp.task.RemoteStopTransactionTask;
import de.rwth.idsg.steve.repository.ChargePointRepository;
import de.rwth.idsg.steve.web.dto.ocpp.RemoteStartTransactionParams;
import lombok.extern.slf4j.Slf4j;
import ocpp.cp._2015._10.RemoteStartStopStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
@Slf4j
@Service
public class ChargePointServiceImpl implements ChargePointService {

    @Autowired private ChargePointRepository chargePointRepository;
    @Autowired private ChargePointService12_Client client12;
    @Autowired private ChargePointService15_Client client15;
    @Autowired private ChargePointService16_Client client16;

    @Override
    public RemoteStartStopStatus remoteStartTransaction(String chargeBoxId, RemoteStartTransactionParams params) 
            throws ExecutionException, InterruptedException {
        OcppVersion version = chargePointRepository.getOcppVersion(chargeBoxId);
        RemoteStartTransactionTask task = new RemoteStartTransactionTask(version, params);

        switch (version) {
            case V_12:
                client12.remoteStartTransaction(chargeBoxId, task);
                break;
            case V_15:
                client15.remoteStartTransaction(chargeBoxId, task);
                break;
            case V_16:
                client16.remoteStartTransaction(chargeBoxId, task);
                break;
            default:
                log.error("Unexpected OCPP version: {}", version);
                throw new IllegalArgumentException("Unexpected OCPP version: " + version);
        }

        String response = task.await();
        return RemoteStartStopStatus.fromValue(response);
    }

    @Override
    public RemoteStartStopStatus remoteStopTransaction(String chargeBoxId, Integer transactionId) 
            throws ExecutionException, InterruptedException {
        OcppVersion version = chargePointRepository.getOcppVersion(chargeBoxId);
        RemoteStopTransactionTask task = new RemoteStopTransactionTask(version, transactionId);

        switch (version) {
            case V_12:
                client12.remoteStopTransaction(chargeBoxId, task);
                break;
            case V_15:
                client15.remoteStopTransaction(chargeBoxId, task);
                break;
            case V_16:
                client16.remoteStopTransaction(chargeBoxId, task);
                break;
            default:
                log.error("Unexpected OCPP version: {}", version);
                throw new IllegalArgumentException("Unexpected OCPP version: " + version);
        }

        String response = task.await();
        return RemoteStartStopStatus.fromValue(response);
    }
}