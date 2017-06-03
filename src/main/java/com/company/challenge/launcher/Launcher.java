package com.company.challenge.launcher;

import com.company.challenge.domain.*;
import com.company.challenge.adapters.endpointA.RestClientForEndpointA;
import com.company.challenge.adapters.endpointB.RestClientForEndpointB;
import com.company.challenge.adapters.persistence.InMemoryMessageRepository;
import com.company.challenge.adapters.sinkEndpoint.RestSinkRepository;
import com.company.challenge.ports.MessageRepository;
import com.company.challenge.ports.MessageSource;
import com.company.challenge.ports.SinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Launcher
{
  private final static Logger logger = LoggerFactory.getLogger(Launcher.class);

  public static void main(String[] args) {

    EventFactory eventFactory = new EventFactory();
    SingleThreadDispatcherEventBus eventBus = new SingleThreadDispatcherEventBus(1000);

    MessageSource fromEndpointA = new RestClientForEndpointA("localhost","7299");
    MessageProducer producerForEndpointA = new MessageProducer(fromEndpointA,eventBus,eventFactory,500);

    MessageSource fromEndpointB = new RestClientForEndpointB("localhost", "7299");
    MessageProducer producerForEndpointB = new MessageProducer(fromEndpointB,eventBus,eventFactory,500);

    SinkRepository sinkRepository = new RestSinkRepository("localhost","7299");

    MessageRepository messageRepository = new InMemoryMessageRepository();

    eventBus.registerConsumer(new MessageEvaluatorEventConsumer(messageRepository, eventBus, eventFactory));
    eventBus.registerConsumer(new ProducersCompletedEventConsumer(messageRepository, eventBus, eventFactory, 2));
    eventBus.registerConsumer(new SinkMessageEventConsumer(sinkRepository));

    eventBus.init();

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.submit(producerForEndpointA);
    executorService.submit(producerForEndpointB);

    try
    {
      executorService.shutdown();
      executorService.awaitTermination(1, TimeUnit.MINUTES);
      logger.info("Producers completed.");

    } catch (InterruptedException ex){
      logger.error("Interrupted",ex);
    }


    eventBus.destroy();


  }
}
