package com.company.challenge.ports;

public class Message
{

  private final String id;
  private final boolean last;
  private final boolean await;

  private Message(String id, boolean last, boolean await)
  {
    this.id = id;
    this.last = last;
    this.await = await;
  }

  public String getId()
  {
    return id;
  }

  public boolean isLast()
  {
    return last;
  }

  public boolean isAwait()
  {
    return await;
  }

  @Override public String toString()
  {
    return "Message{" +
        "id='" + id + '\'' +
        ", last=" + last +
        ", await=" + await +
        '}';
  }

  public static Message forId(String id) {
    return new Message(id,false,false);
  }

  public static Message youBetterWait() {
    return new Message(null,false,true);
  }

  public static Message done() {
    return new Message(null,true,false);
  }
}
