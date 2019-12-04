package com.pb.salesforcenewstreamingapi;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.InputStreamContentProvider;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public final class SoapLoginUtil
{

  // The enterprise SOAP API endpoint used for the login call in this example.
  private static final String SERVICES_SOAP_PARTNER_ENDPOINT = "/services/Soap/u/37.0/";

  private static final String ENV_START =
          "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
                  + "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " +
                  "xmlns:urn='urn:partner.soap.sforce.com'><soapenv:Body>";

  private static final String ENV_END = "</soapenv:Body></soapenv:Envelope>";

  private static byte[] soapXmlForLogin(String username, String password)
          throws UnsupportedEncodingException
  {
    return (ENV_START +
            "  <urn:login>" +
            "    <urn:username>" + username + "</urn:username>" +
            "    <urn:password>" + password + "</urn:password>" +
            "  </urn:login>" +
            ENV_END).getBytes("UTF-8");
  }

  public static String[] login(HttpClient client, String username, String password)
          throws IOException, InterruptedException, SAXException,
          ParserConfigurationException
  {

   /* ContentExchange exchange = new ContentExchange();
    exchange.setMethod("POST");
    exchange.setURL(getSoapURL());
    exchange.setRequestContentSource(new ByteArrayInputStream(soapXmlForLogin(
            username, password)));
    exchange.setRequestHeader("Content-Type", "text/xml");
    exchange.setRequestHeader("SOAPAction", "''");
    exchange.setRequestHeader("PrettyPrint", "Yes");

    client.send(exchange);*/

    ContentResponse cr = null;
    try
    {
     /* cr = client.newRequest(getSoapURL()).method("POST").header("Content-Type", "text/xml").header("SOAPAction", "''").
              content(new InputStreamContentProvider(new ByteArrayInputStream(soapXmlForLogin(
                      username, password)))).send();*/
      cr = client.POST(getSoapURL()).header("Content-Type", "text/xml").header("SOAPAction", "''").
              content(new InputStreamContentProvider(new ByteArrayInputStream(soapXmlForLogin(
                      username, password)))).send();

      /*String uri = getSoapURL();
      String realm = null;
      String u = username;
      String p = password;

      *//* Add authentication credentials *//*
      AuthenticationStore a = client.getAuthenticationStore();
      a.addAuthentication(
              new DigestAuthentication(URI.create(uri), realm, u, p));
      a.findAuthenticationResult(URI.create(uri));

      ContentResponse response = client
              .newRequest(uri)
              .send();*/
    }
    catch (TimeoutException e)
    {
      e.printStackTrace();
    }
    catch (ExecutionException e)
    {
      e.printStackTrace();
    }

    // System.out.println("wait for done status............."+exchange.waitForDone());
    String response = cr.getContentAsString();

    SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setNamespaceAware(true);
    SAXParser saxParser = spf.newSAXParser();

    LoginResponseParser parser = new LoginResponseParser();
    saxParser.parse(new ByteArrayInputStream(
            response.getBytes("UTF-8")), parser);

    if (parser.sessionId == null || parser.serverUrl == null)
    {
      System.out.println("Login Failed!\n" + response);
      return null;
    }

    System.out.println("client.getconnecttimeout...." + client.getConnectTimeout());

    URL soapEndpoint = new URL(parser.serverUrl);
    StringBuilder endpoint = new StringBuilder()
            .append(soapEndpoint.getProtocol())
            .append("://")
            .append(soapEndpoint.getHost());

    if (soapEndpoint.getPort() > 0) endpoint.append(":")
            .append(soapEndpoint.getPort());
    return new String[]{parser.sessionId, endpoint.toString()};
  }

  private static String getSoapURL() throws MalformedURLException
  {
    return new URL("https://login.salesforce.com" +
            getSoapUri()).toExternalForm();
  }

  private static String getSoapUri()
  {
    return SERVICES_SOAP_PARTNER_ENDPOINT;
  }

  private static class LoginResponseParser extends DefaultHandler
  {

    private boolean inSessionId;
    private String sessionId;

    private boolean inServerUrl;
    private String serverUrl;

    @Override
    public void characters(char[] ch, int start, int length)
    {
      if (inSessionId) sessionId = new String(ch, start, length);
      if (inServerUrl) serverUrl = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
    {
      if (localName != null)
      {
        if (localName.equals("sessionId"))
        {
          inSessionId = false;
        }

        if (localName.equals("serverUrl"))
        {
          inServerUrl = false;
        }
      }
    }

    @Override
    public void startElement(String uri, String localName,
            String qName, Attributes attributes)
    {
      if (localName != null)
      {
        if (localName.equals("sessionId"))
        {
          inSessionId = true;
        }

        if (localName.equals("serverUrl"))
        {
          inServerUrl = true;
        }
      }
    }
  }
}
