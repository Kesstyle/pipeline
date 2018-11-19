package by.minsk.kes.pipeline.domain;

public class UiUser {

    private Long id;
    private String name;
    private String email;
    private String token;
    private boolean active;
    private Long lastReadId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Long getLastReadId() {
        return lastReadId;
    }

    public void setLastReadId(final Long lastReadId) {
        this.lastReadId = lastReadId;
    }
}
