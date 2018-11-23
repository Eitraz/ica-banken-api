package com.github.eitraz.ica.client;

import java.util.Map;

class HttpResponse<T> {
    private final Map<String, String> headers;
    private final T body;

    HttpResponse(Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
    }

    Map<String, String> getHeaders() {
        return headers;
    }

    T getBody() {
        return body;
    }
}
