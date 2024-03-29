package com.pb.salesforcenewstreamingapi;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSession;
import org.cometd.bayeux.client.ClientSession.Extension.Adapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CometDReplayExtension, typical usages are the following:
 * {@code client.addExtension(new CometDReplayExtension<>(replayMap));}
 *
 * @author yzhao
 * @since 198 (Winter '16)
 */
public class CometDReplayExtension<V> extends Adapter
{
  private static final String EXTENSION_NAME = "replay";
  private final ConcurrentMap<String, V> dataMap = new ConcurrentHashMap();
  private final AtomicBoolean supported = new AtomicBoolean();

  public CometDReplayExtension(Map<String, V> dataMap)
  {
    this.dataMap.putAll(dataMap);
  }

  @Override
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

  @Override
  public boolean rcvMeta(ClientSession session, Message.Mutable message)
  {
    switch (message.getChannel())
    {
      case Channel.META_HANDSHAKE:
        Map<String, Object> ext = message.getExt(false);
        this.supported.set(ext != null && Boolean.TRUE.equals(ext.get(EXTENSION_NAME)));
    }
    return true;
  }

  @Override
  public boolean sendMeta(ClientSession session, Message.Mutable message)
  {
    switch (message.getChannel())
    {
      case Channel.META_HANDSHAKE:
        message.getExt(true).put(EXTENSION_NAME, Boolean.TRUE);
        break;
      case Channel.META_SUBSCRIBE:
        if (supported.get())
        {
          message.getExt(true).put(EXTENSION_NAME, dataMap);
        }
        break;
    }
    return true;
  }
}