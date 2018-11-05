package by.minsk.kes.pipeline.service;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.persistence.dao.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
public class ReactiveEventService {

    @Autowired
    private EventDao eventDao;

    public Flux<KesEvent> getAllEvents() {
        return Flux.fromIterable(eventDao.selectAll()).delayElements(Duration.ofSeconds(2));
    }

    public ConnectableFlux<KesEvent> getAllEventsInf() {
        return Flux.create(fluxSink -> {
            List<KesEvent> events = null;
            int i = 0;
            while(true) {
                if (events == null) {
                    events = eventDao.selectAll();
                }
                if (events != null && events.size() > i) {
                    fluxSink.next(events.get(i++));
                } else {
                    i = 0;
                    fluxSink.next(new Object());
                }

            }
        }).map(o -> (KesEvent) o).sample(Duration.ofSeconds(2)).publish();
    }

    public Mono<Void> insertEvent(final Mono<KesEvent> event) {
        event.subscribe(e -> eventDao.insert(e));
        return Mono.empty().then();
    }
}
