package by.minsk.kes.pipeline.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class KesEvent {

    private Long id;
    private String name;
    private LocalDateTime date;
    private KesEventType kesEventType;
    private KesEventOutcomeType kesEventOutcomeType;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @JsonProperty("creationDate")
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    @JsonProperty("eventType")
    public KesEventType getKesEventType() {
        return kesEventType;
    }

    public void setKesEventType(final KesEventType kesEventType) {
        this.kesEventType = kesEventType;
    }

    @JsonProperty("eventOutcomeType")
    public KesEventOutcomeType getKesEventOutcomeType() {
        return kesEventOutcomeType;
    }

    public void setKesEventOutcomeType(final KesEventOutcomeType kesEventOutcomeType) {
        this.kesEventOutcomeType = kesEventOutcomeType;
    }
}
