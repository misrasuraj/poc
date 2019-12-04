package com.pb.Algos;

import java.util.*;
import java.lang.*;
import java.io.*;

class GFG { static int[] rowpath = {1, 0, -1, 0};
  static int[] colpath = {0, 1, 0, -1};
  static boolean isValid(int row,int col,int n)
  {
    return (row>=0 && row<n && col>=0 && col<n);
  }
  public static int findpath(int a[][],Pair s,int n)
  {
    boolean visited[][]=new boolean[n][n];

    Queue<Pair> q=new LinkedList<>();
    q.add(s);
    boolean pathFound=false;
    while(!q.isEmpty())
    {
      Pair p = q.poll();
      visited[p.x][p.y]=true;
      for(int i=0; i<4; i++)
      {
        int row = p.x+rowpath[i];
        int col = p.y+colpath[i];

        if(isValid(row,col,n))
        {
          if(a[row][col]==2)
          {
            pathFound=true;
            break;
          }


          if(!visited[row][col] && a[row][col]==3)
          {
            q.add(new Pair(row,col));
          }

        }
      }

      if(pathFound)
        break;


    }

    if(pathFound)
      return 1;
    else
      return 0;

  }
  public static void main (String[] args)throws IOException {
    //code
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    int T=Integer.parseInt(br.readLine());
    for(int t=0;t<T;t++)
    {
      int n=Integer.parseInt(br.readLine());
      int a[][]=new int[n][n];
      String line=br.readLine();
      String strs[]=line.trim().split(" ");
      int i=0;
      Pair s=null;
      for(int j=0;j<n;j++)
      {
        for(int k=0;k<n;k++)
        {
          a[j][k]=Integer.parseInt(strs[i++]);
          if(a[j][k]==1)
            s=new Pair(j,k);
        }
      }
      System.out.println(findpath(a,s,n));
    }
  }
}
class Pair
{
  int x;
  int y;
  public Pair(int x,int y)
  {
    this.x=x;
    this.y=y;
  }
}