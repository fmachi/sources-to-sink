package com.company.challenge.domain;

import com.company.challenge.ports.Message;

import static com.company.challenge.domain.Event.*;

public class EventFactory
{

  public Event receivedMessage(Message message) {
    return new Event(RECEIVED_MESSAGE, message);
  }

  public Event orphaned(Message message) {
    return new Event(ORPHANED_MESSAGE, message);
  }

  public Event joined(Message message) {
    return new Event(JOINED_MESSAGE, message);
  }

  public Event producerCompleted()
  {
    return new Event(PRODUCER_COMPLETED, null);
  }

}
