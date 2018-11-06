package by.minsk.kes.pipeline.handler;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.service.ReactiveEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@Component
public class EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    private ReactiveEventService eventService;

    public Mono<ServerResponse> ping(final ServerRequest request) {
        final Mono<String> pingBody = Mono.just("Hello postgres reactive liquibase jooq app!");
        final Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        return pingBody.flatMap(str ->
                ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(pingBody, String.class)).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getAllEvents(final ServerRequest request) {
        return ServerResponse.ok().contentType(APPLICATION_STREAM_JSON)
                .body(eventService.getAllEventsInf(), KesEvent.class);
    }

    public Mono<ServerResponse> getRandomNumbers(final ServerRequest request) {
        return ServerResponse.ok().contentType(TEXT_EVENT_STREAM)
                .body(eventService.getInfiniteNumbers().autoConnect(), Integer.class);
    }

    public Mono<ServerResponse> startStream(final ServerRequest request) {
        eventService.startStream();
        return ServerResponse.ok().contentType(TEXT_EVENT_STREAM)
                .body(Mono.empty(), Void.class);
    }

    public Mono<ServerResponse> infiniteTime(final ServerRequest request) {
        return ServerResponse.ok().contentType(TEXT_EVENT_STREAM)
                .body(eventService.infiniteFluxDate(), LocalDateTime.class);
    }
}
