package de.arnegns.testspringreactiveframework;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    void mono() {
        Mono<String> stringMono = Mono.just("Spring");

        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    void mono_withError() {
        StepVerifier.create(Mono.error(new RuntimeException("Error occurred")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
