package Proxy_Server;

/**
 * Created by epord on 25/03/17.
 */
public interface Constants {
    byte PROTO_VERSION = 0x05;

    byte NO_AUTHENTICATION_REQUIRED = 0x00;
    byte NO_ACCEPTABLE_METHODS = (byte)0xFF;

    byte CMD_CONNECT = 0x01;

    byte IP_V4 = 0x01;
    byte DOMAINNAME = 0x03;

    byte RESPONSE_SUCCEED = 0x00;
    byte RESPONSE_CONNECTION_REFUSED = 0x05;
}