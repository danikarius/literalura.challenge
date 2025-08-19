package com.danicodes.literalura.service;

import com.danicodes.literalura.service.dto.BookResult;
import com.danicodes.literalura.service.dto.SearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

@Component
public class GutendexClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public GutendexClient(@Value("${literalura.http.timeout-ms:10000}") int timeoutMs,
                          @Value("${literalura.gutendex.base-url}") String baseUrl) {
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout(timeoutMs);
        f.setReadTimeout(timeoutMs);
        this.restTemplate = new RestTemplate(f);
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    public Optional<BookResult> searchFirstByTitle(String title) {
        try {
            String q = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String url = baseUrl + "?search=" + q;
            SearchResponse resp = restTemplate.getForObject(url, SearchResponse.class);
            if (resp != null && resp.results != null && !resp.results.isEmpty()) {
                return Optional.of(resp.results.get(0));
            }
            return Optional.empty();
        } catch (RestClientException e) {
            System.err.println("Erro ao consultar Gutendex: " + e.getMessage());
            return Optional.empty();
        }
    }
}
