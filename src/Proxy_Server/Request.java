package Proxy_Server;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by epord on 25/03/17.
 */
public class Request {
    boolean valid = true;

    byte[] bytes;
    byte cmd;
    byte addressType;
    int addressLength;
    InetAddress dstAddr;
    int dstPort;

    public Request(byte[] bytes) {
        try {
            this.bytes = bytes;
            cmd = bytes[1];
            addressType = bytes[3];
            addressLength = bytes[4];
            dstPort = ((bytes[5 + addressLength] & 0xff) << 8) | (bytes[6 + addressLength] & 0xff);
            if (addressType == Constants.DOMAINNAME) {
                dstAddr = Inet4Address.getByName(retrieveAddress(bytes, 5, addressLength));
            } else if (addressType == Constants.IP_V4) {
                dstAddr = Inet4Address.getByAddress(Arrays.copyOfRange(bytes, 5, 5 + addressLength));
            } else {
                valid = false;
            }
        } catch(Exception e) {
            System.out.println("EXCEPTION!! - Unknown host - " + e.getMessage());
            valid = false;
        }
    }

    private String retrieveAddress(byte[] bytes, int start, int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0 ; i < length ; i++) {
            builder.append((char) (bytes[start + i] ));
        }
        return builder.toString();
    }

    public InetAddress getAddress() {
        return dstAddr;
    }

    public int getLength() {
        return 7 + addressLength;
    }

    public int getPort() {
        return dstPort;
    }

    public boolean isValid() {
        return valid && bytes[0] == Constants.PROTO_VERSION && cmd == Constants.CMD_CONNECT;
    }
}
