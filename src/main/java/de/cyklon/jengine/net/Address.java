package de.cyklon.jengine.net;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.net.*;
import java.util.regex.Pattern;

public class Address {

    public static @NotNull ServerAddress getServerAddress(String host, int port) throws UnknownHostException {
        return getServerAddress(InetAddress.getByName(host), port);
    }

    @SneakyThrows
    public static @NotNull ServerAddress getServerAddress(@NotNull final InetAddress host, final int port) {
        if (!isPortValid(port)) throw new IllegalArgumentException("port " + port + " is invalid!");
        final InetSocketAddress socketAddress = new InetSocketAddress(host, port);
        final URL url = new URL("https", host.getHostAddress(), port, "");
        final URI uri = url.toURI();
        return new ServerAddress() {
            @Override
            public InetAddress getHost() {
                return host;
            }

            @Override
            public String getHostName() {
                return host.getHostName();
            }

            @Override
            public String getIP() {
                return host.getHostAddress();
            }

            @Override
            public int getPort() {
                return port;
            }

            @Override
            public InetSocketAddress getSocketAddress() {
                return socketAddress;
            }

            @Override
            public URL getURL() {
                return url;
            }

            @Override
            public URI getURI() {
                return uri;
            }
        };
    }

    /**
     * @param port the port to check
     * @return true if the port is valid
     */
    public static boolean isPortValid(int port) {
        return port>=0 && port<=65535;
    }

    /**
     * @param ip the ip to check
     * @return true if the ip is valid
     */
    public static boolean isIPValid(String ip) {
        return Pattern.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$", ip);
    }


}
