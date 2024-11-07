package de.rwth.idsg.steve.handler;
import lombok.extern.slf4j.Slf4j;
import de.rwth.idsg.steve.ocpp.OcppCallback;
import de.rwth.idsg.steve.ocpp.ws.data.OcppJsonError;
@Slf4j
public class StringOcppCallback implements OcppCallback<String> {

    @Override
    public void success(String chargeBoxId, String response) {
        log.debug("Received string response for {}: {}", chargeBoxId, response);
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