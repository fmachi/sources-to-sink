package com.company.challenge.adapters;

import com.company.challenge.adapters.interceptors.LogInterceptor;
import com.company.challenge.ports.Message;
import com.company.challenge.ports.MessageSource;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public abstract class RestTemplateMessageSource<M> implements MessageSource
{
  private final String urlTemplate;
  private final String host;
  private final String port;
  private final Class<M> exchangedItemClass;

  private final RestTemplate restTemplate;

  public RestTemplateMessageSource(String urlTemplate, String host, String port, Class<M> exchangedItemClass)
  {
    this.urlTemplate = urlTemplate;
    this.host = host;
    this.port = port;
    this.exchangedItemClass = exchangedItemClass;
    this.restTemplate = new RestTemplate();
    this.restTemplate.setInterceptors(Collections.singletonList((ClientHttpRequestInterceptor)new LogInterceptor()));
  }

  public Message nextMessage()
  {
    try
    {
      return innerNextMessage();
    } catch (HttpMessageNotReadableException ex) {
      throw new MalformedMessageException();
    } catch (HttpClientErrorException ex) {
      if(ex.getStatusCode().value()==406) {
        return Message.youBetterWait();
      }
      throw ex;
    }
  }

  protected abstract Message adapt(M m);

  private Message innerNextMessage()
  {
    M message = restTemplate.getForObject(urlTemplate,
                                            exchangedItemClass,
                                            host,
                                            port);
    return adapt(message);
  }
}
