package com.company.challenge.adapters.sinkEndpoint;

public class SinkResponse
{
  String status;

  public String getStatus()
  {
    return status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  @Override public String toString()
  {
    return "SinkResponse{" +
        "status='" + status + '\'' +
        '}';
  }
}
