package com.company.challenge.domain;

public interface EventConsumer<P>
{
  public boolean accept(Event e);

  public void consume(Event<P> e);
}
