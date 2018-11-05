package by.minsk.kes.pipeline.persistence.converter;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.domain.KesEventOutcomeType;
import by.minsk.kes.pipeline.domain.KesEventType;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static by.minsk.kes.jooq.persistence.tables.Event.EVENT;

@Component
public class EventConverter extends AbstractConverter<KesEvent> {

    public KesEvent convertFromDB(final Record record) {
        if (record == null) {
            return null;
        }
        final KesEvent event = new KesEvent();
        event.setId(record.get(EVENT.ID));
        event.setName(record.get(EVENT.NAME));
        event.setDate(record.get(EVENT.DATE).toLocalDateTime());
        event.setKesEventType(KesEventType.getById(record.get(EVENT.TYPE)));
        event.setKesEventOutcomeType(KesEventOutcomeType.getById(record.get(EVENT.OUTCOME)));
        return event;
    }
}
