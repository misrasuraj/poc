package com.pb.com.pb.mongocdc;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.bson.types.BSONTimestamp;

import java.util.Date;

public class TestMongoCDC
{

  private static MongoClient client = null;
  private static BSONTimestamp lastTimeStamp = new BSONTimestamp((int)(new Date(2016,10,17).getTime()),0);
  private static DBCollection shardTimeCollection = null;


  public static void main(String[] args)
  {
    DBCollection fromCollection = new MongoClient("localhost",27017).getDB("local").getCollection(
            "oplog.rs");
    DBObject timeQuery = getTimeQuery();
    System.out.println("Start timestamp: " + timeQuery);
    DBCursor opCursor = fromCollection.find(timeQuery)
            /*.sort(new BasicDBObject("$natural", 1))
            .addOption(Bytes.QUERYOPTION_TAILABLE)
            .addOption(Bytes.QUERYOPTION_AWAITDATA)
            .addOption(Bytes.QUERYOPTION_NOTIMEOUT)*/;
    try
    {
      while (true)
      {
        if (!opCursor.hasNext())
        {
          continue;
        }
        else
        {
          DBObject nextOp = opCursor.next();
          lastTimeStamp = ((BSONTimestamp)nextOp.get("ts"));
          shardTimeCollection.update(new BasicDBObject(),
                  new BasicDBObject("$set", new BasicDBObject("ts",
                          lastTimeStamp)), true, true, WriteConcern.SAFE);

        }
      }
    }
    finally
    {

    }
  }

  private static DBObject getTimeQuery()
  {
    return lastTimeStamp == null ? new BasicDBObject() : new BasicDBObject(
            "ts", new BasicDBObject("$gt", lastTimeStamp));
  }

}
