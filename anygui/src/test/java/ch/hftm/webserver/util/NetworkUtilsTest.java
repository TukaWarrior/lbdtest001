package ch.hftm.webserver.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

class NetworkUtilsTest {

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    void testGetActiveIpAddress() {
        String ipAddress = NetworkUtils.getActiveIpAddress();

        // Check that the IP address is not null
        assertNotNull(ipAddress, "The active IP address should not be null");

        // Check that the IP address is not empty
        assertFalse(ipAddress.isEmpty(), "The active IP address should not be empty");

        // Check that the IP address is in a valid IPv4 format or loopback
        assertTrue(ipAddress.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$") || ipAddress.equals("127.0.0.1"),
                "The active IP address should be in valid IPv4 format or loopback");
    }

    @Test
    void testGetLocalhostIpAddress() {
        String localhostIp = "1234";

        // Check that the localhost IP is not null
        assertNotNull(localhostIp, "The localhost IP address should not be null");

        // Check that the localhost IP is not empty
        assertFalse(localhostIp.isEmpty(), "The localhost IP address should not be empty");

        // Check that the localhost IP is exactly "127.0.0.1"
        assertEquals("127.0.0.1", localhostIp, "The localhost IP address should be 127.0.0.1");
    }
}