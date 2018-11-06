package by.minsk.kes.pipeline.service;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.persistence.dao.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class ReactiveEventService {

    @Autowired
    private EventDao eventDao;

    private ConnectableFlux<Integer> fluxStream;

    public Flux<KesEvent> getAllEvents() {
        return Flux.interval(Duration.ofSeconds(1)).fromIterable(eventDao.selectAll());
    }

    public Flux<LocalDateTime> infiniteFluxDate() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(t -> LocalDateTime.now()).onErrorResume(e -> {
                    e.printStackTrace();
                    return Flux.just(LocalDateTime.now());
                });
    }

    public ConnectableFlux<Integer> getInfiniteNumbers() {
        fluxStream = Flux.create(fluxSink -> {
            for (int i =0; i < Integer.MAX_VALUE; i++) {
                int j = new Random().nextInt(1000);
                fluxSink.next(new Random().nextInt(1000));
                System.out.println("Next is " + j);
            }
        }, FluxSink.OverflowStrategy.LATEST).map(o -> (Integer) o).sample(Duration.ofMillis(10))
                .doOnSubscribe(s -> System.out.println("Subscribed!"))
                .doOnNext(s -> System.out.println("Next returned is " + s))
                .doOnComplete(() -> System.out.println("Complete!"))
                .publish();
        return fluxStream;
    }

    public void startStream() {
        fluxStream.connect();
    }

    public Flux<KesEvent> getAllEventsInf() {
        return Flux.interval(Duration.ofSeconds(1)).map(t -> {
            int i = new Random().nextInt(100)%4;
            return (KesEvent)eventDao.selectAll().get(i);
        });
    }

    public Mono<Void> insertEvent(final Mono<KesEvent> event) {
        event.subscribe(e -> eventDao.insert(e));
        return Mono.empty().then();
    }
}
