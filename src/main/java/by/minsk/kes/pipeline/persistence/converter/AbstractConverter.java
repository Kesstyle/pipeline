package by.minsk.kes.pipeline.persistence.converter;

import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public abstract class AbstractConverter<T> {

    private static final String DATE_FORMAT = "yyyy-MM-dd ";

    public abstract T convertFromDB(final Record record);

    public List<T> convertIterableFromDB(final Iterable<Record> records) {
        return StreamSupport.stream(records.spliterator(), false).map(this::convertFromDB).collect(Collectors.toList());
    }

}
