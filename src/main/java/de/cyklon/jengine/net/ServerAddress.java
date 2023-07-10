package de.cyklon.jengine.net;

import java.net.*;

public interface ServerAddress {

    InetAddress getHost();

    String getHostName();

    String getIP();

    int getPort();

    InetSocketAddress getSocketAddress();

    URL getURL();

    URI getURI();

}
