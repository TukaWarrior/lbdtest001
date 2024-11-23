package ch.hftm.webserver.util;

import java.net.InetAddress;
import java.net.Socket;

public class NetworkUtils {

    final static private String localhostAdrerss = "127.0.0.1";

    /**
     * Retrieves the active IP address of the local machine.
     *
     * @return the active IP address as a String, or null if an error occurs
     */
    public static String getActiveIpAddress() {
        try (Socket socket = new Socket("8.8.8.8", 53)) { // Google DNS server (no real data is sent)
            InetAddress localAddress = socket.getLocalAddress();
            return localAddress.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLocalhostIpAddress() {
        return localhostAdrerss;
        // Test
    }
}