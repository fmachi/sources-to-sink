package com.company.challenge.adapters;

public class MalformedMessageException extends RuntimeException
{
  public MalformedMessageException()
  {
    super("Received malformed message.");
  }
}
