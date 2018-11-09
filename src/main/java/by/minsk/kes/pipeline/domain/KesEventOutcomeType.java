package by.minsk.kes.pipeline.domain;

public enum KesEventOutcomeType {
        SUCCESS(0), FAILURE(1), CANCELLED(2), POSTPONED(3);

    private int id;

    KesEventOutcomeType(final int id) {
        this.id = id;
    }

    public static KesEventOutcomeType getById(final Integer id) {
        if (id == null) {
            return null;
        }
        for (final KesEventOutcomeType eventOutcomeType : values()) {
            if (eventOutcomeType.getId() == id.intValue()) {
                return eventOutcomeType;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
