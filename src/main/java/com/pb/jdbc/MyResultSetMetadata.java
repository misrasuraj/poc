package com.pb.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MyResultSetMetadata {

  public static void main(String a[]){

    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      con = DriverManager.
              getConnection("jdbc:oracle:thin:@<hostname>:<port num>:<DB name>"
                      ,"user","password");
      st = con.createStatement();
      rs = st.executeQuery("select * from emp");
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      for(int i=0;i<=columnCount;i++){
        System.out.println(rsmd.getColumnName(i));
        System.out.println(rsmd.getColumnType(i));
        System.out.println(rsmd.getColumnClassName());
        System.out.println(rsmd.getColumnClassName());
      }
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally{
      try{
        if(rs != null) rs.close();
        if(st != null) st.close();
        if(con != null) con.close();
      } catch(Exception ex){}
    }
  }
}