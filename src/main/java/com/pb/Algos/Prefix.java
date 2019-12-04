package com.pb.Algos;

public class Prefix
{
  private static char [] a = {' ','*',' ','+',' ','7',' ','*',' ','*',' ','4',' ','6',' ','+',' ','8',' ','9',' ','5',' '};
  private static int i;

  public static void main(String[] args)
  {
    System.out.println(eval());
  }

  private static int eval()
  {
    int x = 0;
    while(a[i]==' ') i++;
    if(a[i] == '+')
    {
      i++; return eval()+eval();
    }
    if(a[i] == '*')
    {
      i++; return eval()*eval();
    }
    while((a[i]>='0') && (a[i]<='9'))
      x = 10*x + (a[i++]-'0');
    return x;
  }
}
