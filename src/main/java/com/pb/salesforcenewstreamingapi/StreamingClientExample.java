package com.pb.salesforcenewstreamingapi;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import org.eclipse.jetty.client.ContentExchange;

/**
 * This example demonstrates how a streaming client works
 * against the Salesforce Streaming API.
 **/

public class StreamingClientExample
{

  // This URL is used only for logging in. The LoginResult
  // returns a serverUrl which is then used for constructing
  // the streaming URL. The serverUrl points to the endpoint
  // where your organization is hosted.

  static final String LOGIN_ENDPOINT = "https://login.salesforce.com";
  private static final String USER_NAME = "avinash@pb.com";
  private static final String PASSWORD = "Sita@123RHoURHLIxGAxyTQAXNX7JJv1";
  // NOTE: Putting passwords in code is not a good practice and not recommended.

  // Set this to true only when using this client
  // against the Summer'11 release (API version=22.0).
  private static final boolean VERSION_22 = false;
  private static final boolean USE_COOKIES = VERSION_22;

  // The channel to subscribe to. Same as the name of the PushTopic.
  // Be sure to create this topic before running this sample.
  private static final String CHANNEL = VERSION_22 ? "/InvoiceStatementUpdates" : "/topic/InvoiceStatementUpdates";
  private static final String STREAMING_ENDPOINT_URI = VERSION_22 ?
          "/cometd" : "/cometd/37.0";

  // The long poll duration.
  private static final int CONNECTION_TIMEOUT = 20 * 1000;  // milliseconds
  private static final int READ_TIMEOUT = 120 * 1000; // milliseconds

  private static long startTime;

  public static void main(String[] args) throws Exception
  {

    System.out.println("Running streaming client example....");

    startTime = System.currentTimeMillis();
    final BayeuxClient client = makeClient();

    client.getChannel(Channel.META_HANDSHAKE).addListener
            (new MessageListener()
            {

              public void onMessage(ClientSessionChannel channel, Message message)
              {

                System.out.println("[CHANNEL:META_HANDSHAKE]: " + message);

                boolean success = message.isSuccessful();
                if (!success)
                {
                  String error = (String)message.get("error");
                  if (error != null)
                  {
                    System.out.println("Error during HANDSHAKE: " + error);
                    //System.out.println("Exiting...");
                    //System.exit(1);
                  }

                  Exception exception = (Exception)message.get("exception");
                  if (exception != null)
                  {
                    System.out.println("Exception during HANDSHAKE: ");
                    exception.printStackTrace();
                    //System.out.println("Exiting...");
                    //System.exit(1);

                  }
                }
                else
                {
                 // message.getExt().put(CometDReplayExtension.EXTENSION_NAME, Boolean.TRUE);
                  client.getChannel(CHANNEL).subscribe(new MessageListener()
                  {
                    public void onMessage(ClientSessionChannel channel, Message message)
                    {
                      System.out.println("Received Message: " + message);
                    }
                  });

                }
              }

            });

    client.getChannel(Channel.META_CONNECT).addListener(
            new MessageListener()
            {
              public void onMessage(ClientSessionChannel channel, Message message)
              {

                System.out.println("[CHANNEL:META_CONNECT]: " + message);
                long currentTime = System.currentTimeMillis();
                System.out.println("Time in millis between two heartbeats.." + (currentTime - startTime));
                startTime = currentTime;
                boolean success = message.isSuccessful();
                if (!success)
                {
                  String error = (String)message.get("error");
                  if (error != null)
                  {
                    System.out.println("Error during CONNECT: " + error);
                    //System.out.println("Exiting...");
                    //System.exit(1);
                  }
                }

              }

            });

    client.getChannel(Channel.META_DISCONNECT).addListener(
            new MessageListener()
            {
              public void onMessage(ClientSessionChannel channel, Message message)
              {

                System.out.println("[CHANNEL:META_DISCONNECT]: " + message);
                //long currentTime = System.currentTimeMillis();
                //System.out.println("Time in millis between two heartbeats.."+(currentTime-startTime));
                //startTime = currentTime;
                boolean success = message.isSuccessful();
                if (!success)
                {
                  String error = (String)message.get("error");
                  if (error != null)
                  {
                    System.out.println("Error during DISCONNECT: " + error);
                    System.out.println("Exiting...");
                    System.exit(1);
                  }
                }
              }

            });

    client.getChannel(Channel.META_SUBSCRIBE).addListener(
            new MessageListener()
            {

              public void onMessage(ClientSessionChannel channel, Message message)
              {

                System.out.println("[CHANNEL:META_SUBSCRIBE]: " + message);
                boolean success = message.isSuccessful();
                if (!success)
                {
                  String error = (String)message.get("error");
                  if (error != null)
                  {
                    System.out.println("Error during SUBSCRIBE: " + error);
                    System.out.println("Exiting...");
                    System.exit(1);
                  }
                }
                else
                {
                  message.getExt();
                }
              }
            });

    Map<String,Long> replayMap = new HashMap<String,Long>();
    replayMap.put(CHANNEL,Long.parseLong("-2"));
    client.addExtension(new CometDReplayExtension<>(replayMap));
    client.handshake();
    System.out.println("Waiting for handshake");

    boolean handshaken = client.waitFor(10 * 1000, BayeuxClient.State.CONNECTED);
    if (!handshaken)
    {
      System.out.println("Failed to handshake: " + client);
      System.exit(1);
    }

    System.out.println("Subscribing for channel: " + CHANNEL);

    /*client.getChannel(CHANNEL).subscribe(new MessageListener()
    {
      public void onMessage(ClientSessionChannel channel, Message message)
      {
        System.out.println("Received Message: " + message);
      }
    });*/

    System.out.println("Waiting for streamed data from your organization ...");
    while (true)
    {
      // This infinite loop is for demo only,
      // to receive streamed events on the
      // specified topic from your organization.
    }
  }

  private static BayeuxClient makeClient() throws Exception
  {
    HttpClient httpClient = new HttpClient(new SslContextFactory());
    httpClient.setConnectTimeout(CONNECTION_TIMEOUT);  //Timeout for making the connection
    //httpClient.setTimeout(READ_TIMEOUT); //Timeout on waiting to read data
    httpClient.start();

    String[] pair = SoapLoginUtil.login(httpClient, USER_NAME, PASSWORD);

    if (pair == null)
    {
      System.exit(1);
    }

    assert pair.length == 2;
    final String sessionid = pair[0];
    String endpoint = pair[1];
    System.out.println("Login successful!\nServer URL: " + endpoint
            + "\nSession ID=" + sessionid);

    Map<String, Object> options = new HashMap<String, Object>();
    //options.put(ClientTransport.TIMEOUT_OPTION, READ_TIMEOUT);
    LongPollingTransport transport = new LongPollingTransport(
            options, httpClient)
    {

      @Override
      protected void customize(Request exchange)
      {
        super.customize(exchange);
        exchange.header("Authorization", "OAuth " + sessionid);
      }
    };

    BayeuxClient client = new BayeuxClient(salesforceStreamingEndpoint(
            endpoint), transport);
    if (USE_COOKIES) establishCookies(client, USER_NAME, sessionid);
    return client;
  }

  private static String salesforceStreamingEndpoint(String endpoint)
          throws MalformedURLException
  {
    return new URL(endpoint + STREAMING_ENDPOINT_URI).toExternalForm();
  }

  private static void establishCookies(BayeuxClient client, String user,
          String sid)
  {
   /* client.setCookie("com.salesforce.LocaleInfo", "us", 24 * 60 * 60 * 1000);
    client.setCookie("login", user, 24 * 60 * 60 * 1000);
    client.setCookie("sid", sid, 24 * 60 * 60 * 1000);
    client.setCookie("language", "en_US", 24 * 60 * 60 * 1000);*/
  }
}


