package com.company.challenge.adapters.sinkEndpoint;

import com.company.challenge.ports.SinkMessage;
import org.junit.Test;

public class RestSinkRepositoryIT
{
  RestSinkRepository repository = new RestSinkRepository("localhost","7299");

  @Test
  public void sinkMessage() {
    repository.save(SinkMessage.orphanedFor("myId"));

    repository.save(SinkMessage.joinedFor("myId2"));
  }

}
