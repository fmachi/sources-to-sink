package com.company.challenge.adapters.persistence;

import com.company.challenge.ports.Message;
import com.company.challenge.ports.MessageRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InMemoryMessageRepository implements MessageRepository
{
  // Maybe a set of messages implementing hashcode and equals?
  private final Map<String,Message> messages = new HashMap<String, Message>();

  public synchronized boolean checkJoinedOrAddOrphan(Message message)
  {
    String messageId = message.getId();
    // A joined message could be received just once? I made this assumption
    if(messages.remove(messageId)!=null) {
      return true;
    }

    messages.put(messageId,message);
    return false;
  }

  public Set<Message> getMessages()
  {
    return new HashSet<Message>(messages.values());
  }
}
