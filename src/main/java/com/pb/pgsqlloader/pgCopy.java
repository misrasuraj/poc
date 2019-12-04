package com.pb.pgsqlloader;

import org.postgresql.copy.CopyIn;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.sql.*;

public class pgCopy
{
  public static void main(String[] args) throws SQLException
  {
    


    
    
    Connection con = null;
    PreparedStatement pst = null;
    /* These are the insert values
    * You can also build them from a list or
    * you can accept the entries programatically
    * and then separate them with the delimeter
    * and build a byte array
    * | is used a delimiter between two fields
    * you can use your own here and replace the same in copyIn call
    */
    //byte[] insert_values = "昨夜|最高".getBytes();
    byte[] insert_values = "abc|".getBytes();

    //These are my connection parameters
    String url = "jdbc:postgresql://localhost:5432/postgres";
    //jdbc:postgresql://host:port/database
    String user = "postgres";
    String password = "admin";
    CopyIn cpIN = null;
    String driver = "org.postgresql.Driver";
    try

    {
      Class.forName(driver);
      con = DriverManager.getConnection(url, user, password);

      CopyManager cm = new CopyManager((BaseConnection)con);
      /*Copy command
      * Replace public.test(col1, col2) with you table Name and
      * replace | with the delimeter of you choice.
      * It should be same as the delimeter used in defining
      * the variable byte[] insert_values
      */
      cpIN = cm.copyIn("COPY public.postgresloadertest(\"Column8\" ,\"Column10\") FROM STDIN WITH DELIMITER '|'  NULL '' CSV QUOTE '\''");

      cpIN.writeToCopy(insert_values, 0, insert_values.length);
      //InputStream in = null;
      /*try
      {
        in = new BufferedInputStream(new FileInputStream("D:\\Stages\\BulkLoad\\DataNCtlFIles\\ORACLE\\testload5.12M.txt"));*/

       /*long inserted_rows = cm.copyIn("COPY public.postgresloadertest(\"Column1\" ,\"Column2\",\"Column3\",\"Column4\",\"Column5\",\"Column6\","
               + "\"Column7\",\"Column8\",\"Column9\",\"Column10\",\"Column11\",\"Column12\",\"Column13\",\"Column14\",\"Column15\") "
              + "FROM STDIN WITH DELIMITER ','",in);*/

       /*long inserted_rows = cm.copyIn("COPY public.postgresloadertest "
              + "FROM STDIN WITH DELIMITER ','",in);*/

        cpIN.endCopy();
      /*}
      catch (IOException e)
      {
        e.printStackTrace();
      }*/
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    catch (ClassNotFoundException ex)
    {
      ex.printStackTrace();
    }
    finally
    {
      try
      {
        if (con != null) con.close();
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
      }
    }
  }
}