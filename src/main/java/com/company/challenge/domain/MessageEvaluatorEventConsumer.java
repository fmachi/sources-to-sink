package com.company.challenge.domain;

import com.company.challenge.ports.Message;
import com.company.challenge.ports.MessageRepository;

import static com.company.challenge.domain.Event.RECEIVED_MESSAGE;

public class MessageEvaluatorEventConsumer implements EventConsumer<Message>
{
  private final MessageRepository repository;
  private final EventBus eventBus;
  private final EventFactory eventFactory;

  public MessageEvaluatorEventConsumer(MessageRepository repository,
                                       EventBus eventBus, EventFactory eventFactory)
  {
    this.repository = repository;
    this.eventBus = eventBus;
    this.eventFactory = eventFactory;
  }

  public boolean accept(Event e)
  {
    return RECEIVED_MESSAGE.equals(e.getType());
  }

  public void consume(Event<Message> e)
  {
    Message payload = e.getPayload();

    if(repository.checkJoinedOrAddOrphan(payload)) {
      eventBus.emit(eventFactory.joined(payload));
    }
  }
}
