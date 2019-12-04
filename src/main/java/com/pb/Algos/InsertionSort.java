package com.pb.Algos;

public class InsertionSort
{
  public static void main(String[] args)
  {
    int[] x = {3, 5, 1, 6, 9, 2, 0, 3, 5, 100, 201, 2, 3, 0};

    int i = 1;
    int j = 0;
    while (i < x.length)
    {
      j = i;
      i++;
      while (j !=0)
      {
        if (x[j] < x[j - 1])
        {
          swap(x,j,j-1);
          j--;
        }
        else
        {
          break;
        }
      }
    }
    System.out.println(x);
  }

  private static void swap(int[] x, int i, int j)
  {
    int temp = x[i];
    x[i] = x[j];
    x[j] = temp;
  }
}
