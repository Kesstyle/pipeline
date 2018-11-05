package by.minsk.kes.pipeline.router;

import by.minsk.kes.pipeline.handler.EventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class MainRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(final EventHandler eventHandler) {
        return RouterFunctions.route(GET("/api/ping"), eventHandler::ping)
                .andRoute(GET("/api/event/all"), eventHandler::getAllEvents);
    }
}
