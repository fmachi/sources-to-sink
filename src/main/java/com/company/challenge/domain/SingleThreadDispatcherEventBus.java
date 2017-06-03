package com.company.challenge.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SingleThreadDispatcherEventBus implements EventBus
{
  private final static Logger logger = LoggerFactory.getLogger(SingleThreadDispatcherEventBus.class);

  private final ArrayBlockingQueue<Event> eventsQueue;
  List<EventConsumer> consumers = new LinkedList<EventConsumer>();
  private ExecutorService executorService;
  private EventDispatcher eventDispatcher;

  public SingleThreadDispatcherEventBus(int bufferCapacity)
  {
    eventsQueue = new ArrayBlockingQueue<Event>(bufferCapacity);
  }

  public void init() {
    executorService = Executors.newFixedThreadPool(1);
    eventDispatcher = new EventDispatcher();
    executorService.submit(eventDispatcher);
  }

  public void destroy() {
    try
    {
      this.executorService.shutdown();
      this.executorService.awaitTermination(1, TimeUnit.MINUTES);
    } catch (InterruptedException ex) {
      logger.info("Interrupted!");
    }
    logger.info("EventBus stopped.");
  }

  public void emit(Event e)
  {
    if(!this.eventsQueue.offer(e)) {
      logger.error("Queue full!");
    }
  }

  public void registerConsumer(EventConsumer eventConsumer)
  {
    this.consumers.add(eventConsumer);
  }

  private class EventDispatcher implements  Runnable {

    AtomicBoolean isActive = new AtomicBoolean(true);

    public void run()
    {
      logger.info("Dispatcher thread starting.");
      int numberOfBlankEvents = 0;
      while(isActive.get()) {
        Event event = null;
        try
        {
          event = eventsQueue.poll(500l, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
          logger.error("Dispatcher thread interrupted.");
        }
        if(event!=null)
        {
          numberOfBlankEvents = 0;
          logger.info("Dispatching event {}",event);
          for (EventConsumer consumer : consumers)
          {
            if (consumer.accept(event))
            {
              consumer.consume(event);
            }
          }
        } else {
          logger.warn("No event received");

          numberOfBlankEvents++;
          if(numberOfBlankEvents==4) {
            isActive.set(false);
          }
        }
      }
      logger.info("Dispatcher thread leaving.");
    }


  }
}
