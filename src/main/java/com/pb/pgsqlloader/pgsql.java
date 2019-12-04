package com.pb.pgsqlloader;

/**
 * Created by AB001MI on 4/8/2016.
 */
public class pgsql
{
  //String a;
  public static void main(String[] args)
  {
    String command = "psql -U postgres -h localhost -d postgres -c \"\\copy public.test from '.\\abc.txt'\"";
    try
    {
      Process process = Runtime.getRuntime().exec(command);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }

}
