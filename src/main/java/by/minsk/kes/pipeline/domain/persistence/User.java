package by.minsk.kes.pipeline.domain.persistence;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_counter")
    @SequenceGenerator(name = "users_counter", sequenceName = "users_counter_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String email;
    private String active;

    @Column(name = "last_read_id")
    private Long lastReadId;

    @NaturalId
    private String token;

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

    public String getActive() {
        return active;
    }

    public void setActive(final String active) {
        this.active = active;
    }

    public Long getLastReadId() {
        return lastReadId;
    }

    public void setLastReadId(final Long lastReadId) {
        this.lastReadId = lastReadId;
    }
}
