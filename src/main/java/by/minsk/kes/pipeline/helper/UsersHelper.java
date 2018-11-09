package by.minsk.kes.pipeline.helper;

import by.minsk.kes.pipeline.listener.KesListener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UsersHelper {

  private Map<String, Long> latestWatchedIds = new ConcurrentHashMap<>();
  private List<KesListener<Map<String, Long>>> listeners = new ArrayList<>();
  private Semaphore semaphore = new Semaphore(1);

  @Value("${user.events.db.batch:7}")
  private int batchSize;

  private AtomicInteger updatesCount = new AtomicInteger(0);

  public void addIdUpdate(final String token, final Long id) {
    addEntry(token, id);
    if (updatesCount.get() >= batchSize) {
      flush();
    }
  }

  public void addEntry(final String token, final Long id) {
    try {
      semaphore.acquire();
      latestWatchedIds.put(token, id);
      updatesCount.incrementAndGet();
    } catch (final InterruptedException e) {
      e.printStackTrace();
    } finally {
      semaphore.release();
    }
  }

  public void clearMap() throws InterruptedException {
    semaphore.acquire();
    latestWatchedIds.clear();
    semaphore.release();
  }

  public void register(final KesListener listener) {
    listeners.add(listener);
  }

  private void flush() {
    try {
      semaphore.acquire();
      notifyListeners();
      latestWatchedIds.clear();
      updatesCount.set(0);
    } catch (final InterruptedException e) {
      semaphore.release();
    }
  }

  private void notifyListeners() {
    final Map<String, Long> updates = new HashMap<>(latestWatchedIds);
    listeners.stream().forEach(listener -> listener.insertListener(updates));
  }
}
