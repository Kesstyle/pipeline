package by.minsk.kes.pipeline.service;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.listener.KesListener;
import by.minsk.kes.pipeline.persistence.dao.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReactiveEventService {

    @Autowired
    private EventDao eventDao;

    public Flux<KesEvent> getAllEventsInf() {
        final Flux<KesEvent> existing = Flux.fromIterable(eventDao.selectAll());
        return existing.mergeWith(Flux.create(emitter -> {
            final KesListener<KesEvent> listener = event -> emitter.next(event);
            eventDao.registerListener(listener);
        }, FluxSink.OverflowStrategy.BUFFER));
    }

    public Mono<Void> insertEvent(final Mono<KesEvent> event) {
        return event.map(e -> {
            eventDao.insert(e);
            return 1;
        }).onErrorResume(ex -> Mono.just(-1)).then();
    }

    public Mono<Void> insertEventBatch(final Flux<KesEvent> events) {
        final List<KesEvent> eventList = new ArrayList<>();
        return events.map(e -> {
            eventList.add(e);
            return 1;
        }).onErrorResume(ex -> Mono.just(-1))
                .doOnComplete(() -> eventDao.insertBatch(eventList)).then();
    }
}
