package Proxy_Server;

import java.util.Arrays;

/**
 * Created by epord on 25/03/17.
 */
public class GreetingRequest implements Constants{
    private byte[] bytes;
    private byte version;
    private byte nmethods;
    private byte[] methods;

    public GreetingRequest(byte[] bytes) {
        this.bytes = bytes;
        version = bytes[0];
        nmethods = bytes[1];
        methods = new byte[nmethods];
        System.arraycopy(bytes, 2, methods, 0, nmethods);
        Logger.log("\nGreeting request:" +
                     "\nversion: " + version +
                     "\nnmethods: " + nmethods +
                     "\nmethods: " + Arrays.toString(methods));
    }

    public GreetingResponse process() {
        byte ret = NO_ACCEPTABLE_METHODS;
        if (version == PROTO_VERSION) {
            for (int i = 0 ; i < nmethods ; i++) {
                if (methods[i] == NO_AUTHENTICATION_REQUIRED) {
                    ret = (byte) NO_AUTHENTICATION_REQUIRED;
                }
            }
        }
        return new GreetingResponse(ret);
    }
}
