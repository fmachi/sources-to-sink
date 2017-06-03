package com.company.challenge.domain;

public class Event<P>
{
  public static final String RECEIVED_MESSAGE = "receivedMessage";
  public static final String ORPHANED_MESSAGE = "orphanedMessage";
  public static final String JOINED_MESSAGE = "joinedMessage";
  public static final String PRODUCER_COMPLETED = "producerCompleted";

  private final String type;
  private final P      payload;

  public Event(String type, P payload)
  {
    this.type = type;
    this.payload = payload;
  }

  public String getType()
  {
    return type;
  }

  public P getPayload()
  {
    return payload;
  }

  @Override public String toString()
  {
    return "Event{" +
        "type='" + type + '\'' +
        ", payload=" + payload +
        '}';
  }
}
