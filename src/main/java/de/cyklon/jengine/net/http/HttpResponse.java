package de.cyklon.jengine.net.http;

import de.cyklon.jengine.util.BufferedStreamReader;
import de.cyklon.jengine.util.TryCatch;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class HttpResponse {
    private final int type;
    private final HttpRequest request;
    private final HttpURLConnection connection;
    private final java.net.http.HttpResponse<InputStream> hr;
    private final ResponseCode code;
    private final String message;

    public HttpResponse(HttpRequest request, HttpURLConnection connection) throws IOException {
        this.type = 0;
        this.request = request;
        this.connection = connection;
        this.hr = null;
        this.code = ResponseCode.getByCode(connection.getResponseCode());
        this.message = connection.getResponseMessage();
    }

    public HttpResponse(HttpRequest request, java.net.http.HttpResponse<InputStream> response) {
        this.type=1;
        this.request = request;
        this.connection = null;
        this.hr = response;
        this.code = ResponseCode.getByCode(response.statusCode());
        this.message = response.toString().split("\\R", 2)[0];
    }

    public ResponseCode getResponseCode() {
        return code;
    }

    public String getResponseMessage() {
        return message;
    }

    public InputStream getInputStream() {
        switch (type) {
            case 0 -> {
                try {
                    return code.getCode()>299 ? connection.getErrorStream() : connection.getInputStream();
                } catch (IOException ignored) {}
                return connection.getErrorStream();
            }
            case 1 -> {
                return hr.body();
            }
        }
        return null;
    }


    public HttpRequest getRequest() {
        return request;
    }

    public enum ResponseCode {
        /**
         * HTTP Status-Code 200: OK.
         */
        HTTP_OK(200),

        /**
         * HTTP Status-Code 201: Created.
         */
        HTTP_CREATED(201),

        /**
         * HTTP Status-Code 202: Accepted.
         */
        HTTP_ACCEPTED(202),

        /**
         * HTTP Status-Code 203: Non-Authoritative Information.
         */
        HTTP_NOT_AUTHORITATIVE(203),

        /**
         * HTTP Status-Code 204: No Content.
         */
        HTTP_NO_CONTENT(204),

        /**
         * HTTP Status-Code 205: Reset Content.
         */
        HTTP_RESET(205),

        /**
         * HTTP Status-Code 206: Partial Content.
         */
        HTTP_PARTIAL(206),

        /* 3XX: relocation/redirect */

        /**
         * HTTP Status-Code 300: Multiple Choices.
         */
        HTTP_MULT_CHOICE(300),

        /**
         * HTTP Status-Code 301: Moved Permanently.
         */
        HTTP_MOVED_PERM(301),

        /**
         * HTTP Status-Code 302: Temporary Redirect.
         */
        HTTP_MOVED_TEMP(302),

        /**
         * HTTP Status-Code 303: See Other.
         */
        HTTP_SEE_OTHER(303),

        /**
         * HTTP Status-Code 304: Not Modified.
         */
        HTTP_NOT_MODIFIED(304),

        /**
         * HTTP Status-Code 305: Use Proxy.
         */
        HTTP_USE_PROXY(305),

        /* 4XX: client error */

        /**
         * HTTP Status-Code 400: Bad Request.
         */
        HTTP_BAD_REQUEST(400),

        /**
         * HTTP Status-Code 401: Unauthorized.
         */
        HTTP_UNAUTHORIZED(401),

        /**
         * HTTP Status-Code 402: Payment Required.
         */
        HTTP_PAYMENT_REQUIRED(402),

        /**
         * HTTP Status-Code 403: Forbidden.
         */
        HTTP_FORBIDDEN(403),

        /**
         * HTTP Status-Code 404: Not Found.
         */
        HTTP_NOT_FOUND(404),

        /**
         * HTTP Status-Code 405: Method Not Allowed.
         */
        HTTP_BAD_METHOD(405),

        /**
         * HTTP Status-Code 406: Not Acceptable.
         */
        HTTP_NOT_ACCEPTABLE(406),

        /**
         * HTTP Status-Code 407: Proxy Authentication Required.
         */
        HTTP_PROXY_AUTH(407),

        /**
         * HTTP Status-Code 408: Request Time-Out.
         */
        HTTP_CLIENT_TIMEOUT(408),

        /**
         * HTTP Status-Code 409: Conflict.
         */
        HTTP_CONFLICT(409),

        /**
         * HTTP Status-Code 410: Gone.
         */
        HTTP_GONE(410),

        /**
         * HTTP Status-Code 411: Length Required.
         */
        HTTP_LENGTH_REQUIRED(411),

        /**
         * HTTP Status-Code 412: Precondition Failed.
         */
        HTTP_PRECON_FAILED(412),

        /**
         * HTTP Status-Code 413: Request Entity Too Large.
         */
        HTTP_ENTITY_TOO_LARGE(413),

        /**
         * HTTP Status-Code 414: Request-URI Too Large.
         */
        HTTP_REQ_TOO_LONG(414),

        /**
         * HTTP Status-Code 415: Unsupported Media Type.
         */
        HTTP_UNSUPPORTED_TYPE(415),

        /* 5XX: server error */

        /**
         * HTTP Status-Code 500: Internal Server Error.
         */
        HTTP_INTERNAL_ERROR(500),

        /**
         * HTTP Status-Code 501: Not Implemented.
         */
        HTTP_NOT_IMPLEMENTED(501),

        /**
         * HTTP Status-Code 502: Bad Gateway.
         */
        HTTP_BAD_GATEWAY(502),

        /**
         * HTTP Status-Code 503: Service Unavailable.
         */
        HTTP_UNAVAILABLE(503),

        /**
         * HTTP Status-Code 504: Gateway Timeout.
         */
        HTTP_GATEWAY_TIMEOUT(504),

        /**
         * HTTP Status-Code 505: HTTP Version Not Supported.
         */
        HTTP_VERSION(505);

        private final int code;

        ResponseCode(int code) {
            this.code = code;
        }

        /**
         * @return the int code
         */
        public int getCode() {
            return code;
        }

        public static ResponseCode getByCode(int code) {
            for (ResponseCode rc : values()) if (rc.getCode()==code) return rc;
            return null;
        }

        @Override
        public String toString() {
            return "%s (%s)".formatted(name(), getCode());
        }
    }


    @Override
    public String toString() {
        String out;
        try {
            InputStream in = getInputStream();
            out = in==null ? "" : new BufferedStreamReader(in).read();
        } catch (IOException e) {
            out = "";
        }
        return String.format("""
                    code: %s
                    message: %s
                    out: %s
                    """, getResponseCode(), getResponseMessage(), out);
    }
}
