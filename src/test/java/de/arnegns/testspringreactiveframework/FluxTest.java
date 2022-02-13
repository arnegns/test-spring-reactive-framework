package de.arnegns.testspringreactiveframework;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    void flux() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                //.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("After error"))
                .log(); // enabling info logging of events

        stringFlux
                .subscribe(System.out::println, // handling events
                        (e) -> System.out.println("Exception is " + e), // handling error
                        () -> System.out.println("Completed"));  // handling completion
    }

    @Test
    void fluxElements_withoutError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log(); // enabling info logging of events

        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete(); // starts the flow of the element of the flux eq. to subscribe
    }

    @Test
    void fluxElements_withError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectError(RuntimeException.class)
                .verify(); // start the hole flow of subscribe to the flux
    }

    @Test
    void fluxElementsCount_withError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")
                .verify(); // start the hole flow of subscribe to the flux
    }
}
