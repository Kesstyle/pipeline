package by.minsk.kes.pipeline.configuration;

import by.minsk.kes.pipeline.helper.UsersHelper;
import by.minsk.kes.pipeline.listener.KesListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

@Configuration
public class UsersConfiguration {

//  @Bean
//  public Flux<Integer> dbUpdater(final UsersHelper usersHelper) {
//    return Flux.create(emitter -> {
//          final KesListener<Map<String, Long>> listener = emitter::next;
//          usersHelper.register(listener);
//        }, FluxSink.OverflowStrategy.BUFFER);
//  }
}
