package by.minsk.kes.pipeline.domain;

public enum KesEventType {
    GENERIC(0), URGENT(1), REACTION(2);

    private int id;

    KesEventType(final int id) {
        this.id = id;
    }

    public static KesEventType getById(final Integer id) {
        if (id == null) {
            return null;
        }
        for (final KesEventType eventType: values()) {
            if (eventType.getId() == id.intValue()) {
                return eventType;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
