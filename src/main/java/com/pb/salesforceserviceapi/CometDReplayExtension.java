package com.pb.salesforceserviceapi;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
//import org.cometd.bayeux.client.ClientSession.Extension.Adapter;

/**
 * CometDReplayExtension, typical usages are the following:
 * {@code client.addExtension(new CometDReplayExtension<>(replayMap));}
 *
 * @author yzhao
 * @since 198 (Winter '16)
 */
public class CometDReplayExtension<V> implements ClientSession.Extension
{
  public static final String EXTENSION_NAME = "replay";
  private final ConcurrentMap<String, V> dataMap = new ConcurrentHashMap<String,V>();
  private final AtomicBoolean supported = new AtomicBoolean();

  public CometDReplayExtension(Map<String, V> dataMap)
  {
    this.dataMap.putAll(dataMap);
  }

  //@Override
  @SuppressWarnings("unchecked")
  public boolean rcv(ClientSession session, Message.Mutable message)
  {
    Object data = message.get(EXTENSION_NAME);
    if (this.supported.get() && data != null)
    {
      try
      {
        dataMap.put(message.getChannel(), (V)data);
      }
      catch (ClassCastException e)
      {
        return false;
      }
    }
    return true;
  }

  //@Override
  public boolean rcvMeta(ClientSession session, Message.Mutable message)
  {
    if (Channel.META_HANDSHAKE.equals(message.getChannel()))
    {

      Map<String, Object> ext = message.getExt(false);
      this.supported.set(ext != null && Boolean.TRUE.equals(ext.get(EXTENSION_NAME)));
    }
    return true;
  }

  public boolean send(ClientSession session, Message.Mutable message)
  {
    return true;
  }

  //@Override
  public boolean sendMeta(ClientSession session, Message.Mutable message)
  {
    if (Channel.META_HANDSHAKE.equals(message.getChannel()))
    {
      message.getExt(true).put(EXTENSION_NAME, Boolean.TRUE);
    }
    else if (Channel.META_SUBSCRIBE.equals(message.getChannel()))
    {

      if (supported.get())
      {
        message.getExt(true).put(EXTENSION_NAME, dataMap);
      }

    }
    return true;
  }
}