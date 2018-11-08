package by.minsk.kes.pipeline.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import by.minsk.kes.pipeline.handler.EventHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MainRouter {

  @Bean
  public RouterFunction<ServerResponse> routes(final EventHandler eventHandler) {
    return RouterFunctions.route(GET("/api/ping"), eventHandler::ping)
        .andRoute(GET("/api/event/all"), eventHandler::getAllEvents)
//                .andRoute(GET("/api/numbers"), eventHandler::getRandomNumbers)
//                .andRoute(GET("/api/start"), eventHandler::startStream)
//                .andRoute(GET("/api/date"), eventHandler::infiniteTime)
//                .andRoute(POST("/api/test"), eventHandler::justTest);
        .andRoute(POST("/api/event"), eventHandler::insertEvent)
        .andRoute(POST("/api/events"), eventHandler::insertEvents);
  }
}
