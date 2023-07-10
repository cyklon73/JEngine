package de.cyklon.jengine.net.http;

import de.cyklon.jengine.JEngine;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final URL url;
    private final Method method;
    private final String body;
    private final Map<String, String> properties;
    private final int connectTimeout, readTimeout;


    public HttpRequest(String url, Method method, String body, Map<String, String> properties) throws MalformedURLException {
        this(new URL(url), method, body, properties);
    }

    public HttpRequest(URL url, Method method, String body, Map<String, String> properties) {
        this.url = url;
        this.method = method;
        this.body = body==null ? "" : body;
        this.properties = properties==null ? new HashMap<>() : properties;
        this.connectTimeout = 50000;
        this.readTimeout = 60000;
    }

    private String method() {
        properties();
        return this.method.name();
    }

    private Map<String, String> properties() {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("User-Agent", "JEngine/%s".formatted(JEngine.VERSION));
        map.putAll(properties);
        return map;
    }

    public HttpResponse send() throws IOException {
        return switch (method) {
            case PATCH, CONNECT -> {
                try {
                    yield sendHttpClient();
                } catch (URISyntaxException | InterruptedException e) {
                    throw new HttpException(e);
                }
            }
            default -> sendURLConnection();
        };
    }

    private HttpResponse sendURLConnection() throws IOException {
        HttpURLConnection con = (HttpURLConnection) this.url.openConnection();

        con.setRequestMethod(method());
        con.setConnectTimeout(connectTimeout);
        con.setReadTimeout(readTimeout);

        properties().forEach(con::setRequestProperty);


        con.setDoOutput(true);
        OutputStream out = con.getOutputStream();
        out.write(this.body.getBytes());
        out.flush();
        out.close();

        return new HttpResponse(this, con);
    }
    private HttpResponse sendHttpClient() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        java.net.http.HttpRequest.Builder builder = java.net.http.HttpRequest.newBuilder(this.url.toURI())
                .method(method(), java.net.http.HttpRequest.BodyPublishers.ofString(this.body));
        properties().forEach(builder::header);
        return new HttpResponse(this, client.send(builder.build(), java.net.http.HttpResponse.BodyHandlers.ofInputStream()));
    }

    public URL getUrl() {
        return url;
    }

    public Method getMethod() {
        return method;
    }

    public enum Method {
        GET,
        POST,
        PUT,
        HEAD,
        DELETE,
        PATCH,
        OPTIONS,
        CONNECT,
        TRACE
    }

}
