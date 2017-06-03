package com.company.challenge.domain;

import com.company.challenge.adapters.MalformedMessageException;
import com.company.challenge.ports.Message;
import com.company.challenge.ports.MessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProducer implements Runnable
{
  private final static Logger logger = LoggerFactory.getLogger(MessageProducer.class);

  private final MessageSource messageSource;
  private final EventBus eventBus;
  private final EventFactory eventFactory;
  private final long timeToAwait;

  public MessageProducer(MessageSource messageSource,
                         EventBus eventBus,
                         EventFactory eventFactory,
                         long timeToAwait)
  {
    this.messageSource = messageSource;
    this.eventBus = eventBus;
    this.eventFactory = eventFactory;
    this.timeToAwait = timeToAwait;
  }

  public void run()
  {
    boolean exit = false;
    int numberOfMessages = 1;
    do {
      Message message = nextMessage();
      logger.info("Received message {} at iteration {}",message,numberOfMessages++);
      if(message!=null)
      {
        if (message.isAwait())
        {
          logger.info("Received hint to await for external processing.");
          await();
        }
        else if (message.isLast())
        {
          logger.info("Received shutdown");
          eventBus.emit(eventFactory.producerCompleted());
          exit = true;
        }
        else
        {
          logger.info("Emit message received.");
          eventBus.emit(eventFactory.receivedMessage(message));
        }
      } else {
        logger.warn("Received null message.");
      }

    } while(!exit);
  }

  private void await()
  {
    try
    {
      Thread.sleep(timeToAwait);
    } catch (InterruptedException ex) {

    }
  }

  protected Message nextMessage() {
    try
    {
      return messageSource.nextMessage();
    } catch (MalformedMessageException ex) {
      logger.error("Malformed message received",ex);
      return null;
    }
  }

}
