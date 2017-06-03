package com.company.challenge.adapters.endpointB;

public class MessageB
{
  Id id;
  Done done;

  public Done getDone()
  {
    return done;
  }

  public void setDone(Done done)
  {
    this.done = done;
  }

  public Id getId()
  {
    return id;
  }

  public void setId(Id id)
  {
    this.id = id;
  }

  @Override public String toString()
  {
    return "MessageB{" +
        "id=" + id +
        ", done=" + done +
        '}';
  }
}
