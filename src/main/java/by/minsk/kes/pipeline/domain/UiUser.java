package by.minsk.kes.pipeline.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UiUser {

  @JsonProperty
  private Long id;

  @JsonProperty
  private String name;

  @JsonProperty
  private String email;

  @JsonProperty
  private String token;

  @JsonProperty
  private boolean active;

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
}
