package com.company.challenge.adapters.sinkEndpoint;

public class WrongSinkMessageException extends RuntimeException
{
  public WrongSinkMessageException(String id)
  {
    super("Sink failure for message with id ["+id+"].");
  }
}
