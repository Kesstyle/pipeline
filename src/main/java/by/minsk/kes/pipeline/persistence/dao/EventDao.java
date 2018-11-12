package by.minsk.kes.pipeline.persistence.dao;

import static by.minsk.kes.jooq.persistence.Tables.EVENT;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.persistence.converter.EventConverter;

import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventDao extends CommonDao<KesEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(EventDao.class);

    protected TableImpl getTable() {
        return EVENT;
    }

    @Autowired
    protected void setConverter(final EventConverter converter) {
        this.converter = converter;
    }

    public List<KesEvent> selectLatestEvents(final Long id) {
        return converter.convertIterableFromDB(selectWithIdAfterGiven(id));
    }

    public void insert(final KesEvent kesEvent) {
        try (final Connection connection = getConnection();
             final DSLContext insert = getContext(connection);) {
            insertEvent(kesEvent, insert).execute();
            notifyListeners(kesEvent);
        } catch (final SQLException e) {
            throwError(e);
        }
    }

    public void insertBatch(final Collection<KesEvent> kesEventList) {
        try (final Connection connection = getConnection();
             final DSLContext insert = getContext(connection)) {
            insert.batch(kesEventList.stream()
                    .map(event -> insertEvent(event, insert)).collect(Collectors.toList())).execute();
        } catch (final SQLException e) {
            throwError(e);
        }
    }

    private InsertSetMoreStep insertEvent(final KesEvent kesEvent, final DSLContext insert) {
        return insert.insertInto(EVENT)
                .set(EVENT.NAME, kesEvent.getName())
                .set(EVENT.DATE, Timestamp.valueOf(LocalDateTime.now()))
                .set(EVENT.TYPE, kesEvent.getKesEventType().getId())
                .set(EVENT.OUTCOME, kesEvent.getKesEventOutcomeType().getId());
    }

    private KesEvent selectLatestEventWithCriteria(final String name, final int eventType, final int outcome) {
        final List<Record> result = selectWithCriteria(name, eventType, outcome);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return (KesEvent) converter.convertFromDB(result.get(result.size() - 1));
    }

    private List<Record> selectWithCriteria(final String name, final int eventType, final int outcome) {
        try (final Connection connection = getConnection();
             final DSLContext context = getContext(connection)) {
            return context.select().from(getTable()).where(EVENT.NAME.eq(name))
                    .and(EVENT.TYPE.eq(eventType))
                    .and(EVENT.OUTCOME.eq(outcome))
                    .fetch();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Iterable<Record> selectWithIdAfterGiven(final Long id) {
        if (id == null) {
            return selectAllRecords();
        }
        try (final Connection connection = getConnection();
             final DSLContext context = getContext(connection)) {
            return context.select().from(getTable())
                    .having(EVENT.ID.gt(id))
                    .fetch();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }

    private void notifyListeners(final KesEvent kesEvent) {
        final KesEvent newKesEvent = selectLatestEventWithCriteria(kesEvent.getName(),
                kesEvent.getKesEventType().getId(), kesEvent.getKesEventOutcomeType().getId());
        if (newKesEvent != null) {
            listeners.stream().forEach(l -> l.insertListener(newKesEvent));
        }
    }

    private void throwError(final Throwable e) {
        LOG.error("Error during call to DB: ", e);
        throw new RuntimeException(e);
    }
}
