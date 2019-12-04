package com.pb.storedprocedure;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMysql
{

  public static void main(String[] args)
  {
    Connection conn = null;

    try
    {
      conn = DriverManager.getConnection("jdbc:mysql://localhost/sakila?user=root&password=Mysql@786");

      DatabaseMetaData databaseMetaData = conn.getMetaData();
      /*final ResultSet resultSet = databaseMetaData.getSchemas();
      while (resultSet.next())
      {
        System.out.println(resultSet.getString("TABLE_SCHEM"));
      }*/
      ResultSet ctlgs = databaseMetaData.getCatalogs();
      while(ctlgs.next())
      {
        System.out.println("ctlgs  =  "+ctlgs.getString(1));
      }

      /*CallableStatement cStmt = conn.prepareCall("{call film_in_stock(?, ?,?)}");
      cStmt.setInt(1, 1);
      cStmt.setInt(2, 1);
      cStmt.registerOutParameter(3, Types.INTEGER);
      System.out.println( cStmt.execute());*/

    }
    catch (SQLException e1)
    {
      e1.printStackTrace();
    }

  }
}



