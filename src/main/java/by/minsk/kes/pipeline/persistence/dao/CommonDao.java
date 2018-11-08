package by.minsk.kes.pipeline.persistence.dao;

import by.minsk.kes.pipeline.listener.KesListener;
import by.minsk.kes.pipeline.persistence.ContextProvider;
import by.minsk.kes.pipeline.persistence.converter.AbstractConverter;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public abstract class CommonDao<T> {

  @Autowired
  protected ContextProvider provider;

  protected AbstractConverter converter;
  protected List<KesListener<T>> listeners = new ArrayList<>();

  public List<T> selectAll() {
    System.out.println("call to DB");
    return converter.convertIterableFromDB(selectAllRecords());
  }

  public void registerListener(final KesListener<T> listener) {
    listeners.add(listener);
  }

  protected Iterable<Record> selectAllRecords() {
    try (final Connection connection = getConnection();
         final DSLContext context = getContext(connection)) {
      return context.select().from(getTable()).fetch();
    } catch (final SQLException e) {
      e.printStackTrace();
    }
    return Arrays.asList();
  }

  protected Connection getConnection() {
    return provider.getConnection();
  }

  protected DSLContext getContext(final Connection connection) {
    return provider.getContext(connection);
  }

  protected abstract TableImpl getTable();
}
