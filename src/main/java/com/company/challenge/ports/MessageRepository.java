package com.company.challenge.ports;

import java.util.Set;

public interface MessageRepository
{
  public boolean checkJoinedOrAddOrphan(Message message);

  public Set<Message> getMessages();
}
