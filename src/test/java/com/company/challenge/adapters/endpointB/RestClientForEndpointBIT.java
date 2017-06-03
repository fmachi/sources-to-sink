package com.company.challenge.adapters.endpointB;

import com.company.challenge.adapters.MalformedMessageException;
import com.company.challenge.ports.MessageSource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClientForEndpointBIT
{
  private final Logger logger = LoggerFactory.getLogger(RestClientForEndpointBIT.class);

  MessageSource endpointB = new RestClientForEndpointB("localhost", "7299");

  @Test
  public void receiveNextMessage()
  {
    for (int i = 1; i <= 1000; i++)
    {
      try
      {
        logger.info("Parsed {}", endpointB.nextMessage());
      }
      catch (MalformedMessageException e)
      {
        logger.error("Malformed item.");
      }
    }
  }

}
