package by.minsk.kes.pipeline.handler;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

import by.minsk.kes.pipeline.domain.User;
import by.minsk.kes.pipeline.service.ReactiveUsersService;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class UsersHandler {

  private static final Logger LOG = LoggerFactory.getLogger(UsersHandler.class);

  @Autowired
  private ReactiveUsersService usersService;

  public Mono<ServerResponse> getAllUsers(final ServerRequest request) {
    return ServerResponse.ok().contentType(APPLICATION_STREAM_JSON).body(usersService.getAllUsers(), User.class);
  }

}
