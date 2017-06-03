package com.company.challenge.adapters.endpointA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageA
{

  private String status;
  private String id;

  public String getStatus()
  {
    return status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  @Override public String toString()
  {
    return "MessageA{" +
        "status='" + status + '\'' +
        ", id='" + id + '\'' +
        '}';
  }
}
