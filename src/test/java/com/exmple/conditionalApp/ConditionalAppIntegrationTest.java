package com.exmple.conditionalApp;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.wait.strategy.Wait;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

// @SpringBootTest НЕ нужен — мы тестируем внешние контейнеры, не контекст приложения
@Testcontainers
class ConditionalAppIntegrationTest {

    // Контейнер для dev-образа (порт 8080 внутри)
    @Container
    static GenericContainer<?> devContainer = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/profile").forStatusCode(200));

    // Контейнер для prod-образа (порт 8081 внутри)
    @Container
    static GenericContainer<?> prodContainer = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081)
            .waitingFor(Wait.forHttp("/profile").forStatusCode(200));

    // Стандартный HTTP-клиент из Java 11+
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    void testDevProfile_ReturnsDevMessage() throws Exception {
        // Получаем реальный порт на хосте
        int port = devContainer.getMappedPort(8080);
        String url = "http://localhost:" + port + "/profile";

        // Создаём и отправляем запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Проверяем ответ
        assertEquals(200, response.statusCode());
        assertEquals("Current profile is dev", response.body());
    }

    @Test
    void testProdProfile_ReturnsProdMessage() throws Exception {
        int port = prodContainer.getMappedPort(8081);
        String url = "http://localhost:" + port + "/profile";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals("Current profile is production", response.body());
    }
}