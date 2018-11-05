package by.minsk.kes.pipeline.persistence.dao;

import by.minsk.kes.pipeline.persistence.ContextProvider;
import by.minsk.kes.pipeline.persistence.converter.AbstractConverter;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class CommonDao<T> {

    @Autowired
    protected ContextProvider provider;

    protected AbstractConverter converter;

    public List<T> selectAll() {
        return converter.convertIterableFromDB(selectAllRecords());
    }

    protected Iterable<Record> selectAllRecords() {
       return getContext().select().from(getTable()).fetch();
    }

    protected DSLContext getContext() {
        return provider.getContext();
    }

    protected abstract TableImpl getTable();
}
