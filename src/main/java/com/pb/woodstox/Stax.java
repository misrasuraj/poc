package com.pb.woodstox;

import javax.xml.stream.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by AB001MI on 5/16/2016.
 */
public class Stax
{
  public static void main(String [] args) throws XMLStreamException
  {
    //System.setProperty("javax.xml.stream.XMLInputFactory", "com.ctc.wstx.stax.WstxInputFactory");
    XMLInputFactory inputFactory = XMLInputFactory.newInstance();

    String xml = "<books>"
            + "  <book id=\"str1234\">"
            + "    <author>str1234</author>"
            + "    <title>str1234</title>"
            + "    <genre>str1234</genre>"
            + "    <price>3.14159</price>"
            + "    <pub_date>2012-12-13</pub_date>"
            + "    <review>str1234</review>"
            + "  </book>"
            + "</books>";
    Source src = new StreamSource(new java.io.StringReader(xml));
    XMLStreamReader reader = inputFactory.createXMLStreamReader(src);
    if(reader.hasNext())
    {
      reader.next();
      if (reader.isStartElement())
      {
        // push current start tag name to XPathStack
        String elementName = reader.getName().toString();
        System.out.println(elementName);
       
      }
    }
  }
}
