package com.company.challenge.domain;

import com.company.challenge.ports.MessageRepository;
import com.company.challenge.ports.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducersCompletedEventConsumer implements EventConsumer
{
  private final static Logger logger = LoggerFactory.getLogger(ProducersCompletedEventConsumer.class);
  private int expectedNumberOfProducers;
  private final MessageRepository messageRepository;
  private final EventBus eventBus;
  private final EventFactory eventFactory;

  public ProducersCompletedEventConsumer(MessageRepository messageRepository,
                                         EventBus eventBus,
                                         EventFactory eventFactory,
                                         int expectedNumberOfProducers)
  {
    this.expectedNumberOfProducers = expectedNumberOfProducers;
    this.messageRepository = messageRepository;
    this.eventBus = eventBus;
    this.eventFactory = eventFactory;
  }

  public boolean accept(Event e)
  {
    return Event.PRODUCER_COMPLETED.equals(e.getType());
  }

  public void consume(Event e)
  {
    expectedNumberOfProducers--;
    logger.info("Producer consumed {}.",expectedNumberOfProducers);
    if(expectedNumberOfProducers==0) {
      logger.info("All non matched ids are assumed to be orphans");

      for(Message message:messageRepository.getMessages()) {
        logger.info("Emitting orphaned {}",message);
        eventBus.emit(eventFactory.orphaned(message));
      }

    }

  }
}
