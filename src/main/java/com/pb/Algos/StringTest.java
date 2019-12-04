package com.pb.Algos;

import java.util.ArrayList;

public class StringTest
{
  public static void main(String[] args)
  {
    /*String x = "aabbblaslakslkasbb";
    System.out.println("Is string has all unique chars : " + checkUniqueString(x));
    System.out.println("Removed duplicate chars : " + removeDuplicates(x));
    int[][] matrix1 = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}};

    ArrayList<Integer> set = new ArrayList<>();
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(4);
    System.out.println(getSubsets(set, 0));*/
    int[] marks = {10,20,30,40};
    numOfPrizes(4,marks);

  }

  private static int numOfPrizes(int k, int[] marks)
  {
    int length = marks.length;
    int[] rank = new int[length];
    rank[length - 1] = 1;
    int i = length - 2;
    int j = 1;
    while (i >= 0)
    {
      boolean specialRank = false;
      if (marks[i] < marks[i + 1])
      {
        rank[i] = rank[i + 1] + 1;
      }
      else if (marks[i] == marks[i + 1])
      {
        specialRank = true;
        rank[i] = rank[i + 1];
      }
      if (marks[i] > marks[i - 1])
      {
        if (specialRank)
        {
          rank[i - 1] = length-i;
        }
        else
        {
          rank[i-1] = rank[i]+1;
        }
      }
      else if (marks[i] == marks[i - 1])
      {
        rank[i - 1] = rank[i + 1];
      }
      i = i-2;
    }
    System.out.println(rank);
    int numOfPrizes = 0;
    for(int m = 0 ; m<rank.length; m--)
    {
      if(rank[m]==k)
      {
        numOfPrizes = length -m;
      }
    }

    return numOfPrizes;
  }

  private static ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set, int index)
  {
    ArrayList<ArrayList<Integer>> allsubsets;
    if (set.size() == index)
    {
      allsubsets = new ArrayList<ArrayList<Integer>>();
      allsubsets.add(new ArrayList<Integer>()); // Empty set
    }
    else
    {
      allsubsets = getSubsets(set, index + 1);
      int item = set.get(index);
      ArrayList<ArrayList<Integer>> moresubsets = new ArrayList<ArrayList<Integer>>();
      for (ArrayList<Integer> subset : allsubsets)
      {
        ArrayList<Integer> newsubset = new ArrayList<>();
        newsubset.addAll(subset);
        newsubset.add(item);
        moresubsets.add(newsubset);
      }
      allsubsets.addAll(moresubsets);
    }
    return allsubsets;

  }



  private static char[] removeDuplicates(String x)
  {
    if (x == null)
    {
      return null;
    }
    int length = x.length();
    char[] charArray = x.toCharArray();
    for (int j = 0; j < length - 1; j++)
      for (int i = j; i < length - 1; i++)
      {
        if (charArray[j] == charArray[i + 1])
        {
          int k;
          for (k = i + 1; k < length - 1; k++)
          {
            charArray[k] = charArray[k + 1];
          }
          charArray[k] = 0;
          length = length - 1;
          if (i > 0)
            i = i - 1;
          int l;
          for (l = j; j > 0; j--)
          {
            charArray[l] = charArray[l - 1];
          }
          charArray[0] = 0;
          length = length - 1;
          j = j + 1;
        }
      }
    System.out.println(charArray);
    return charArray;
  }

  private static boolean checkUniqueString(String x)
  {
    boolean isUnique = true;
    char[] charArray = x.toCharArray();

    Lable1:
    for (int j = 0; j < x.length() - 1; j++)
      for (int i = j; i < x.length() - 1; i++)
      {
        if (charArray[j] == charArray[i + 1])
        {
          isUnique = false;
          break Lable1;
        }
      }
    return isUnique;
  }
}
