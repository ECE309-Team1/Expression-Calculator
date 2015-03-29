import java.io.*;  
class Stack  
{  
   private double[] a;  
   private int top,m;  
   public Stack(int max)  
   {  
     m=max;  
     a=new double[m];  
     top=-1;  
   }  
   public void push(double key)  
   {  
     a[++top]=key;  
   }  
   public double pop()  
   {  
     return(a[top--]);  
   }  
}  
class Evaluation{  
   public double calculate(String s)  
   {  
	 int stringLength;
     double  result=0;  
     stringLength=s.length();  
     Stack a= new Stack(stringLength);  
     for(int i=0;i<stringLength;i++)  
     {  
       char ch=s.charAt(i);  
       if(ch>='0'&&ch<='9')  
         a.push((int)(ch-'0'));  
       else  
       {  
         double x=a.pop();  
         double y=a.pop();  
         switch(ch)  
         {  
           case '+':result=x+y;  
              break;  
           case '-':result=y-x;  
              break;  
           case '*':result=x*y;  
              break;  
           case '/':result=y/x;  
              break;  
           case 'r':result= Math.pow(x,1/y);
        	   break;
           case '^':result= Math.pow(x, y);
           	  break;
           default:result=0;  
         }  
         a.push(result);  
       }  
     }  
     result=a.pop();  
     return(result);  
   }  
}  
class PostfixEvaluation  
{  
   public static void main(String[] args)throws IOException  
   {  
     String input;  
     while(true)  
     {  
       System.out.println("Enter the postfix expresion");  
       input=getString();  
       if(input.equals(""))  
         break;  
       Evaluation e=new Evaluation();  
       System.out.println("Result:- "+e.calculate(input));  
     }  
   }  
   public static String getString()throws IOException  
   {  
     DataInputStream inp=new DataInputStream(System.in);  
     String s=inp.readLine();  
     return s;  
   }  
}  