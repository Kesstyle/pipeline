package by.minsk.kes.pipeline.service;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.listener.KesListener;
import by.minsk.kes.pipeline.persistence.dao.EventDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ReactiveEventService {

  @Autowired
  private EventDao eventDao;

  private Flux<KesEvent> kesEventFlux;

  public Flux<KesEvent> getAllEventsInf() {
    if (kesEventFlux == null) {
      kesEventFlux = Flux.create(emitter -> {
            final KesListener<KesEvent> listener = event -> emitter.next(event);
            eventDao.registerListener(listener);
          }, FluxSink.OverflowStrategy.BUFFER);
//          .map(t -> {
//        final List<KesEvent> events = eventDao.selectAll();
//        int i = new Random().nextInt(100) % events.size();
//        return events.get(i);
//      });
    }
    return kesEventFlux;
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
