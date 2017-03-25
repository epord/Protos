package Proxy_Server;

import java.util.Arrays;

/**
 * Created by epord on 25/03/17.
 */
public class Response {
    byte[] bytes;
    Request request;

    public Response(Request request) {
        this.request = request;
        bytes = request.bytes;

        if (request.isValid()) {
            bytes[1] = Constants.RESPONSE_SUCCEED;
            bytes[3] = 0x01; // why??? Why can't it be 0x03 (DOMAINNAME)?? ;(
        } else {
            bytes[1] = Constants.RESPONSE_CONNECTION_REFUSED;
        }
    }

    public byte[] getResponse() {
        Logger.log("\nTrying to connect to:" +
                "\ncmd: " + request.cmd +
                "\naddress: " + request.dstAddr +
                "\nport: " + request.dstPort);
        return bytes;
    }

    public boolean connectionAccepted() {
        return bytes[1] == Constants.RESPONSE_SUCCEED;
    }
}
