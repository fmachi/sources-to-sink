package com.company.challenge.adapters.endpointB;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Id
{
  @JacksonXmlProperty(isAttribute=true)
  private String value;

  public String getValue()
  {
    return value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  @Override public String toString()
  {
    return "Id{" +
        "value='" + value + '\'' +
        '}';
  }
}
