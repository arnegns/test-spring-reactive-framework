# test-spring-reactive-framework
Experimenting with [spring reactive framework](https://spring.io/reactive).
It comes with Spring Webflux.

- asynchronous and non blocking
- handling massive numbers of concurrent connections
- subscriber and publisher model
- actually it _does not_ support jdbc, jpa and nosql


## reactor-core
Two classes [Flux](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html) 
and [Mono](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html).
- Flux 0 to n element
- Mono 0 to 1 element