package com.pb.thread;

import java.util.ArrayList;
import java.util.List;

public class MultipleRecursion
{

  public static void main(String args[])
  {
    List<String> Alist = new ArrayList<>();
    List<String> Plist = new ArrayList<>();
    List<String> Mlist = new ArrayList<>();

    Alist.add("A");
    Alist.add("B");
    Alist.add("C");
    Plist.add("P");
    Plist.add("Q");
    Plist.add("R");
   /* Mlist.add("M");
    Mlist.add("N");
    Mlist.add("O");*/

    List<List<String>> combineList = new ArrayList<>();
    combineList.add(Alist);
    combineList.add(Plist);
    //combineList.add(Mlist);

    System.out.print(mainRecursion(combineList));
  }

  private static List<List<String>> mainRecursion(List<List<String>> combineList)
  {
    List<List<String>> l2 = new ArrayList<>();
    List<List<String>> returnList = new ArrayList<>();
    List<String> l1 = combineList.get(0);
    for(String main : l1)
    {
      List<String> stringLi = new ArrayList<>();
      stringLi.add(main);
      l2.add(stringLi);
    }
    for(int i=1;i<combineList.size();i++)
    {
      l2 =  subRutine(combineList.get(i), l2);
    }
   return l2;
  }

  private static List<List<String>> subRutine(List<String> l1, List<List<String>> l2)
  {
    List<List<String>> combinedList = new ArrayList<>();
    for(String l1Element : l1)
    {
      for (List<String> l2Element : l2)
      {
        List<String> combined = new ArrayList<>();
        combined.add(l1Element);
        combined.addAll(l2Element);
        combinedList.add(combined);
      }
    }
    return combinedList;
  }
}
