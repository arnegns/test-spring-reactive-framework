package de.arnegns.testspringreactiveframework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest
class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void flux_approach1() {
        // Arrange & Act
        Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange() // acting like a subscriber
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        // Assert
        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    void flux_approach2() {
        // Arrange & Act & Assert
        webTestClient.get().uri("/fluxstream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange() // acting like a subscriber
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON)
                .expectBodyList(Integer.class)
                .hasSize(4);
    }

    @Test
    void flux_approach3() {
        // Arrange
        List<Integer> expected = List.of(1, 2, 3, 4);

        // Act
        EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange() // acting like a subscriber
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        // Assert
        assertEquals(expected, entityExchangeResult.getResponseBody());
    }

    @Test
    void flux_approach4() {
        // Arrange
        List<Integer> expected = List.of(1, 2, 3, 4);

        // Act & Assert
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange() // acting like a subscriber
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith(response -> {
                    assertEquals(expected, response.getResponseBody());
                });
    }

    @Test
    void fluxStream() {
        // Arrange & Act
        Flux<Long> longStreamFlux = webTestClient.get().uri("/fluxstream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange() // acting like a subscriber
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        // Assert
        StepVerifier.create(longStreamFlux)
                .expectNext(0l, 1l, 2l)
                .thenCancel()
                .verify();
    }

    @Test
    void mono() {
        // Arrange
        Integer expected = 1;

        // Act & Assert
        webTestClient.get().uri("/mono")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .consumeWith((response) -> {
                    assertEquals(expected, response.getResponseBody());
                });
    }
}