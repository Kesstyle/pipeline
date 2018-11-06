package by.minsk.kes.pipeline.persistence.dao;

import by.minsk.kes.pipeline.domain.KesEvent;
import by.minsk.kes.pipeline.persistence.converter.EventConverter;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static by.minsk.kes.jooq.persistence.Tables.EVENT;

@Component
public class EventDao extends CommonDao {

    protected TableImpl getTable() {
        return EVENT;
    }

    @Autowired
    protected void setConverter(final EventConverter converter) {
        this.converter = converter;
    }

    public void insert(final KesEvent kesEvent) {
        try (final Connection connection = getConnection()) {
            getContext(connection).insertInto(EVENT)
                    .set(EVENT.NAME, kesEvent.getName())
                    .set(EVENT.DATE, Timestamp.valueOf(kesEvent.getDate()))
                    .set(EVENT.TYPE, kesEvent.getKesEventType().getId())
                    .set(EVENT.OUTCOME, kesEvent.getKesEventOutcomeType().getId())
                    .execute();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }
}
