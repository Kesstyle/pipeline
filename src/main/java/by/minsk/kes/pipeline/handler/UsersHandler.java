package by.minsk.kes.pipeline.handler;

import by.minsk.kes.pipeline.domain.UiUser;
import by.minsk.kes.pipeline.service.ReactiveUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@Component
public class UsersHandler {

    private static final Logger LOG = LoggerFactory.getLogger(UsersHandler.class);

    @Autowired
    private ReactiveUsersService usersService;

    public Mono<ServerResponse> getAllUsers(final ServerRequest request) {
        return ServerResponse.ok().contentType(APPLICATION_STREAM_JSON).body(usersService.getAllUsers(), UiUser.class);
    }

    public Mono<ServerResponse> getUserByToken(final ServerRequest request) {
        return Mono.just(request.headers().header("utoken"))
                .map(l -> l.get(0))
                .flatMap(s -> usersService.getUserByToken(Mono.just(s)))
                .flatMap(this::successResponse)
                .switchIfEmpty(emptyResponse())
                .onErrorResume(e -> errorResponse("An error occured in attempt to retrieve user! ", e));
    }

    public Mono<ServerResponse> insertUser(final ServerRequest request) {
        return request.bodyToMono(UiUser.class)
                .flatMap(u -> usersService.insertUser(Mono.just(u)))
                .flatMap(this::successResponse)
                .onErrorResume(e -> errorResponse("Cannot insert user: ", e));
    }

    public Mono<ServerResponse> updateLastRead(final ServerRequest request) {
        return request.bodyToMono(UiUser.class)
                .flatMap(u -> usersService.updateLastReadId(Mono.just(u)))
                .flatMap(this::successResponse)
                .onErrorResume(e -> errorResponse("Cannot update last read message for user: ", e));
    }

    private Mono<ServerResponse> successResponse(final Object body) {
        return ServerResponse.ok().contentType(APPLICATION_STREAM_JSON).syncBody(body);
    }

    private Mono<ServerResponse> errorResponse(final String message, final Throwable e) {
        return ServerResponse.badRequest().contentType(TEXT_EVENT_STREAM)
                .body(Mono.just(message + e), String.class);
    }

    private Mono<ServerResponse> emptyResponse() {
        return ServerResponse.badRequest().contentType(TEXT_EVENT_STREAM)
                .body(Mono.just("Unfortunately we cannot get any data for your request"), String.class);
    }
}
