package com.company.challenge.domain;

import com.company.challenge.ports.Message;
import com.company.challenge.ports.SinkMessage;
import com.company.challenge.ports.SinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class SinkMessageEventConsumer implements EventConsumer<Message>
{
  private final static Logger logger = LoggerFactory.getLogger(SinkMessageEventConsumer.class);
  private final SinkRepository sinkRepository;
  private final static Set<String> eventTypes = new HashSet<String>(asList(Event.ORPHANED_MESSAGE,
                                                                           Event.JOINED_MESSAGE));

  public SinkMessageEventConsumer(SinkRepository sinkRepository)
  {
    this.sinkRepository = sinkRepository;
  }

  public boolean accept(Event e)
  {
    return eventTypes.contains(e.getType());
  }

  public void consume(Event<Message> event)
  {
    try
    {
      sinkRepository.save(fromEvent(event));
    } catch (Exception ex) {
      logger.error("Error sending sink message.",ex);
    }
  }

  private SinkMessage fromEvent(Event<Message> e)
  {
    String type = e.getType();
    String id = e.getPayload().getId();
    if(Event.ORPHANED_MESSAGE.equals(type)) {
      return SinkMessage.orphanedFor(id);
    } else {
      return SinkMessage.joinedFor(id);
    }
  }
}
