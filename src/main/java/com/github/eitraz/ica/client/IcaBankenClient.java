package com.github.eitraz.ica.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class IcaBankenClient {
    private static final Logger logger = LoggerFactory.getLogger(IcaBankenClient.class);

    private static final String BASE_URL = "https://handla.api.ica.se/api/";
    private static final String HTTP_GET = "GET";

    private final Authentication authentication;

    public IcaBankenClient(Authentication authentication) {
        this.authentication = authentication;
    }

    public <T> T get(String path, Class<T> responseType) {
        return get(path, authentication.getHeaders(this), responseType).getBody();
    }

    <T> HttpResponse<T> get(String path, Map<String, String> headers, Class<T> responseType) {
        HttpURLConnection connection = openConnection(getUrl(path));

        headers = Optional.ofNullable(headers).orElseGet(HashMap::new);

        try {
            setRequestMethod(connection, HTTP_GET);

            // Set headers
            headers.forEach(connection::setRequestProperty);

            logRequest(connection);

            int responseCode = getResponseCode(connection);
            String responseBody = getResponseBody(connection, responseCode);

            logResponse(connection, responseCode, responseBody);

            // Valid response code
            if (responseCode < 400) {
                return new HttpResponse<>(
                        getResponseHeaders(connection.getHeaderFields()),
                        stringToJsonObject(responseBody, responseType));
            }
            // Invalid response code
            else {
                if (responseCode == 401) {
                    authentication.invalidateSession();
                }

                throw new RuntimeException(String.format("Request for %s failed with status %d and body: %s",
                        connection.getURL().toString(),
                        responseCode,
                        responseBody));
            }
        } finally {
            connection.disconnect();
        }
    }

    private Map<String, String> getResponseHeaders(Map<String, List<String>> headerFields) {
        return headerFields.entrySet().stream()
                           .collect(Collectors.toMap(
                                   Map.Entry::getKey,
                                   o -> o.getValue().stream().collect(joining(","))
                           ));
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    private <T> T stringToJsonObject(String string, Class<T> type) {
        if (string == null)
            return null;

        try {
            return getObjectMapper().readValue(string, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create " + type.getName() + " object from JSON string", e);
        }
    }

    @SuppressWarnings("Duplicates")
    private URL getUrl(String path) {
        try {
            // Remove leading '/'
            while (path.startsWith("/") && path.length() > 1) {
                path = path.substring(1);
            }

            return new URL(BASE_URL + path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("Duplicates")
    private HttpURLConnection openConnection(URL url) {
        try {
            logger.debug("Opening connection for {}", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            return connection;
        } catch (IOException e) {
            throw new RuntimeException("Failed to open HTTP connection", e);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void setRequestMethod(HttpURLConnection connection, String method) {
        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException e) {
            throw new RuntimeException(String.format("Failed to set HTTP request method '%s'", method), e);
        }
    }

    private int getResponseCode(HttpURLConnection connection) {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get HTTP response code", e);
        }
    }

    private String getResponseBody(HttpURLConnection connection, int responseCode) {
        try (InputStream inputStream = getResponseInputStream(connection, responseCode)) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read response input stream", e);
        }
    }

    private InputStream getResponseInputStream(HttpURLConnection connection, int responseCode) {
        try {
            return responseCode < 400 ? connection.getInputStream() : connection.getErrorStream();
        } catch (IOException e) {
            throw new RuntimeException("Failed to obtain response input stream", e);
        }
    }

    private void logRequest(HttpURLConnection connection) {
        logger.info(">> Request {} {}",
                connection.getRequestMethod(),
                connection.getURL().toString());

        connection.getRequestProperties()
                  .forEach((key, value) -> logger.debug(">> Request header: {} = {}", key, value));
    }

    private void logResponse(HttpURLConnection connection, int responseCode, String responseBody) {
        logger.info("<< Response for {} {} with status code {} and body: {}",
                connection.getRequestMethod(),
                connection.getURL().toString(),
                responseCode,
                responseBody);

        connection.getHeaderFields()
                  .forEach((key, value) -> logger.debug("<< Response header: {} = {}", key, value));
    }
}
