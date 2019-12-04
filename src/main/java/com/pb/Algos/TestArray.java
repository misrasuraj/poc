package com.pb.Algos;

public class TestArray
{

  public static void main(String[] args)
  {
    //char[] charArray = {'X','Y','p','X','Z','Y','Z','p'};
    char[] charArray = {'X','Y','Y','X'};
//    charArray[0] ='X';
//    charArray[1] ='X';
//    charArray[2] ='Y';
//    charArray[3] ='Y';
//    charArray[4] ='X';
//    charArray[5] ='Z';
//    charArray[6] ='X';
//    charArray[7] ='Y';
//    charArray[8] ='Z';
//    charArray[9] ='X';

    for(int i =0;i< charArray.length; i++)
    {
      for (int j = i; j < charArray.length; j++)
      {
        if (i == j) continue;
        if (charArray[i] == charArray[j])
        {
          i++;
          swap(charArray, i, j);
        }
      }
    }

    System.out.println(charArray);

  }

  private static void swap(char[] charArray, int i, int j)
  {
    char temp = charArray[i];
    charArray[i] = charArray[j];
    charArray[j] = temp;
  }
}
