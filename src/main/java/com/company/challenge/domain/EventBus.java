package com.company.challenge.domain;

public interface EventBus
{
  public void emit(Event e);

  public void registerConsumer(EventConsumer eventConsumer);
}
