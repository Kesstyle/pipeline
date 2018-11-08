package by.minsk.kes.pipeline.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestDomain {

  private Long id;
  private String name;

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
}
