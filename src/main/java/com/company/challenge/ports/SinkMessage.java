package com.company.challenge.ports;

public class SinkMessage
{
  private final String kind;
  private final String id;

  private SinkMessage(String kind, String id)
  {
    this.kind = kind;
    this.id = id;
  }

  public String getKind()
  {
    return kind;
  }

  public String getId()
  {
    return id;
  }

  public static SinkMessage joinedFor(String id) {
    return new SinkMessage("joined",id);
  }

  public static SinkMessage orphanedFor(String id) {
    return new SinkMessage("orphaned",id);
  }

  @Override public String toString()
  {
    return "SinkMessage{" +
        "kind='" + kind + '\'' +
        ", id='" + id + '\'' +
        '}';
  }
}
