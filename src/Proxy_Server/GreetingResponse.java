package Proxy_Server;

import java.util.Arrays;

/**
 * Created by epord on 25/03/17.
 */
public class GreetingResponse implements Constants{
    byte version = PROTO_VERSION;
    byte method;

    public GreetingResponse(byte method) {
        this.method = method;
    }

    public byte[] getResponse() {
        byte[] response = new byte[2];
        response[0] = version;
        response[1] = method;
        Logger.log("\nGreeting Response:" +
                    "\nversion: " + version +
                    "\nmethod: " + method);
        return response;
    }
}
