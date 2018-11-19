package by.minsk.kes.pipeline.persistence.dao;

import by.minsk.kes.pipeline.listener.KesListener;

import java.util.ArrayList;
import java.util.List;

public class CommonDao<T> {

    protected List<KesListener<T>> listeners = new ArrayList<>();

    public void registerListener(final KesListener<T> listener) {
        listeners.add(listener);
    }
}
