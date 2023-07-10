package de.cyklon.jengine.net;

import lombok.SneakyThrows;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;
import java.util.regex.Pattern;

public class NetworkClient {

    private Socket socket;

    public NetworkClient(ServerAddress address) throws IOException {
        this.socket = new ClientSocket(address);
    }




    private static class ClientSocket extends Socket {


        public ClientSocket(ServerAddress address) throws IOException {
            this(address.getSocketAddress());
        }

        public ClientSocket(InetSocketAddress address) throws IOException {
            super(address.getAddress(), address.getPort());
        }

    }

}
