package by.minsk.kes.pipeline.router;

import by.minsk.kes.pipeline.handler.EventHandler;
import by.minsk.kes.pipeline.handler.UsersHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class MainRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(final EventHandler eventHandler, final UsersHandler usersHandler) {
        return RouterFunctions.route(GET("/api/ping"), eventHandler::ping)
                .andRoute(GET("/api/event/all"), eventHandler::getAllEvents)
//                .andRoute(GET("/api/numbers"), eventHandler::getRandomNumbers)
//                .andRoute(GET("/api/start"), eventHandler::startStream)
//                .andRoute(GET("/api/date"), eventHandler::infiniteTime)
//                .andRoute(POST("/api/test"), eventHandler::justTest);
                .andRoute(POST("/api/event"), eventHandler::insertEvent)
                .andRoute(POST("/api/events"), eventHandler::insertEvents)
                .andRoute(GET("/api/users"), usersHandler::getAllUsers)
                .andRoute(GET("/api/user"), usersHandler::getUserByToken)
                .andRoute(POST("/api/user"), usersHandler::insertUser)
                .andRoute(PUT("/api/user/lastread"), usersHandler::updateLastRead);
    }
}
