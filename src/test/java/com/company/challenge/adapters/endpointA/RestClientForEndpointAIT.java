package com.company.challenge.adapters.endpointA;

import com.company.challenge.adapters.MalformedMessageException;
import com.company.challenge.ports.MessageSource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClientForEndpointAIT
{

  private final Logger logger = LoggerFactory.getLogger(RestClientForEndpointAIT.class);

  MessageSource endpointA = new RestClientForEndpointA("localhost", "7299");

  @Test
  public void receiveNextMessage()
  {
    for (int i = 1; i <= 1000; i++)
    {
      try
      {
        logger.info("Parsed {}", endpointA.nextMessage());
      }
      catch (MalformedMessageException e)
      {
        logger.error("Malformed item.");
      }
    }
  }

}
