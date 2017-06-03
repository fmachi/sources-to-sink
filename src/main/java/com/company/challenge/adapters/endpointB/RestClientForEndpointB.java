package com.company.challenge.adapters.endpointB;

import com.company.challenge.adapters.RestTemplateMessageSource;
import com.company.challenge.ports.Message;
import com.company.challenge.ports.MessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClientForEndpointB extends RestTemplateMessageSource<MessageB> implements MessageSource
{

  private final static Logger logger = LoggerFactory.getLogger(RestClientForEndpointB.class);

  public RestClientForEndpointB(String host, String port)
  {
    super("http://{host}:{port}/source/b",host,port,MessageB.class);
  }

  protected Message adapt(MessageB messageB)
  {

    logger.info("B-Received {}",messageB);

    //TODO: unable to parse the Done empty tag, so far
    // it was a patch
    //if(messageB.getDone()!=null) {
    if(messageB.getId()==null) {
      return Message.done();
    }

    return Message.forId(messageB.getId().getValue());
  }
}
