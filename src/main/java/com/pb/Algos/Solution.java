package com.pb.Algos;

import java.util.Scanner;

public class Solution {

  // Complete the minimumBribes function below.
  static int  count =0;
  static int check2swap = 0;
  static void minimumBribes(int[] q) {

    for(int i = 0; i<q.length-1; i++ )
    {

      if(q[i]>q[i+1])
      {
        count++;
        check2swap++;
        if(check2swap>2)
        {
          System.out.println("Too many swap");
          return;
        }
        swap(q,i);
      }
      else
      {
        check2swap--;
      }

    }
    System.out.println("Numberof swaps "+count);

  }
  public static void swap(int[] a, int i)
  {
    int temp = a[i];
    a[i] = a[i+1];
    a[i+1] = temp;
  }
  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    int t = scanner.nextInt();
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    for (int tItr = 0; tItr < t; tItr++) {
      int n = scanner.nextInt();
      scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

      int[] q = new int[n];

      String[] qItems = scanner.nextLine().split(" ");
      scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

      for (int i = 0; i < n; i++) {
        int qItem = Integer.parseInt(qItems[i]);
        q[i] = qItem;
      }

      minimumBribes(q);
    }

    scanner.close();
  }
}
