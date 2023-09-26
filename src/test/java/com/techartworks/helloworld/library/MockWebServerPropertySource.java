package com.techartworks.helloworld.library;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.env.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

public class MockWebServerPropertySource extends PropertySource<MockWebServer> implements DisposableBean {

    private static final MockResponse JWKS_RESPONSE = response(
            "{\"keys\": [{\"kid\": \"70yDTlcsVYODpEMb2TUGF5WO79KzhxERbvHwdJR28Xw\", \"kty\": \"RSA\", \"alg\": \"RS256\", \"use\": \"sig\", \"n\": \"hhIoE6_561XzRbWSsS_kZvSHW3SsTNO1bkhKepoXjSZba94RIQ4XgugSnO9XA0EB4Mftfk2PWUtFr12cbOPu-DXXTFwuKBVpXn6Ja1p4xiZpDNf9L_ObUi7iz6D1MY9YM8xn1OxieuL2-qg8-y1yYl5R6dtwZS8fcpVNSp-f7e4qfgNBSp0RMdHyK_Bij_eevcOkv6oSpJCGp7W4UhTDfeiInaIWmwbaN4_3o_bH_S2RtEbGfNFg6lBFB3U_nxqcwQxqLEC9Pojnh6pugQqx_9S_3BKBkHpdu8WLfVmxH8XXCaV1FhVvpZmVn-qBD61dZ_n55gu1ZCyqh1MWLEohTw\", \"e\": \"AQAB\"}]}",
            200);

    private static final MockResponse NOT_FOUND_RESPONSE = response(
            "{ \"message\" : \"This mock authorization server responds to just one request: GET /.well-known/jwks.json.\" }",
            404);

    /**
     * Name of the random {@link PropertySource}.
     */
    public static final String MOCK_WEB_SERVER_PROPERTY_SOURCE_NAME = "mockwebserver";

    private static final String NAME = "mockwebserver.url";

    private boolean started;

    public MockWebServerPropertySource() {
        super(MOCK_WEB_SERVER_PROPERTY_SOURCE_NAME, new MockWebServer());
    }

    @Override
    public void destroy() throws Exception {
        getSource().shutdown();
    }

    @Override
    public Object getProperty(String name) {
        if (!name.equals(NAME)) {
            return null;
        }
        String url = getUrl();
        return url;
    }

    /**
     * Get's the URL (i.e. "http://localhost:123456")
     * @return the url with the dynamic port
     */
    private String getUrl() {
        MockWebServer mockWebServer = getSource();
        if (!this.started) {
            intializeMockWebServer(mockWebServer);
        }
        String url = mockWebServer.url("").url().toExternalForm();
        return url.substring(0, url.length() - 1);
    }

    private void intializeMockWebServer(final MockWebServer mockWebServer) {
        Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/.well-known/jwks.json".equals(request.getPath())) {
                    return JWKS_RESPONSE;
                }

                return NOT_FOUND_RESPONSE;
            }
        };

        mockWebServer.setDispatcher(dispatcher);
        try {
            mockWebServer.start();
            this.started = true;
        } catch (final IOException ex) {
            throw new RuntimeException("Could not start " + mockWebServer, ex);
        }
    }

    private static MockResponse response(final String body, final int status) {
        return new MockResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(status).setBody(body);
    }
}
