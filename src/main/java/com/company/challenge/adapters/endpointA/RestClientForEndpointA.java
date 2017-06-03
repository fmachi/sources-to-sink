package com.company.challenge.adapters.endpointA;

import com.company.challenge.adapters.RestTemplateMessageSource;
import com.company.challenge.ports.Message;
import com.company.challenge.ports.MessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClientForEndpointA extends RestTemplateMessageSource<MessageA> implements MessageSource
{

  private final static Logger logger = LoggerFactory.getLogger(RestClientForEndpointA.class);

  public RestClientForEndpointA(String host, String port)
  {
    super("http://{host}:{port}/source/a", host, port, MessageA.class);
  }

  protected Message adapt(MessageA messageA)
  {
    logger.info("A-Received {}",messageA);

    if("done".equals(messageA.getStatus())) {
      return Message.done();
    }
    return Message.forId(messageA.getId());
  }
}
