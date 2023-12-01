package com.webclient.pr02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient client() {
            return WebClient.builder()
            //.baseUrl("http://localhost:8080") // 웹클라이언트가 보낼 주소
            .defaultCookie("key","value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // 보내는 형식은 JSON
            //.defaultUriVariables(Collections.singletonMap("path", UriUtils.encodePath("/api/v1", StandardCharsets.UTF_8))) // uri변수 기본값 설정  i.g) .uri("/{path}", "api/resource") => "http://localhost:8080/api/resource"로 전송
            .build();
            }
}
