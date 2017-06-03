package com.company.challenge.adapters.sinkEndpoint;

import com.company.challenge.ports.SinkMessage;
import com.company.challenge.adapters.interceptors.LogInterceptor;
import com.company.challenge.ports.SinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class RestSinkRepository implements SinkRepository
{
  private final Logger logger = LoggerFactory.getLogger(RestSinkRepository.class);

  private final String host;
  private final String port;
  private final RestTemplate restTemplate;

  public RestSinkRepository(String host, String port)
  {
    this.host = host;
    this.port = port;
    this.restTemplate = new RestTemplate();
    this.restTemplate.setInterceptors(Collections
                                          .singletonList((ClientHttpRequestInterceptor)new LogInterceptor()));

  }

  public void save(SinkMessage sinkMessage)
  {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ResponseEntity<SinkResponse> stringResponseEntity = restTemplate
        .postForEntity("http://{host}:{port}/sink/a", new HttpEntity<SinkMessage>(sinkMessage , headers), SinkResponse.class, host, port);

    if(!"ok".equals(stringResponseEntity.getBody().getStatus())) {
      throw new WrongSinkMessageException(sinkMessage.getId());
    }
    logger.info("Post response with code {} and content {}",stringResponseEntity.getStatusCode(),stringResponseEntity.getBody());
  }
}
