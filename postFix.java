import java.io.*;  
  
public class postFix{  
	public class Stack  
	{  
	   private double[] stack;  
	   private int top,m;  
	   public Stack(int max)  
	   {  
	     m=max;  
	     stack=new double[m];  
	     top=-1;  
	   }  
	   public void push(double key)  
	   {  
	     stack[++top]=key;  
	   }  
	   public double pop()  
	   {  
	     return(stack[top--]);  
	   }  
	}
 
	   /*public static void main(String[] args)throws IOException  
	   {  
	     //String[] input = {"4","2","+","3","5","1","-","*","+"};  
		   String[] input = {"-27","3","r"}; 
	       //System.out.println("Enter the postfix expresion");  
	       //input=getString();  
	       //if(input.equals(""))  
	       //  break;  
	       postFix e=new postFix(); 
	       System.out.println("Result: "+e.calculate(input));  
	     
	     
	}*/
   public double calculate(String[] s)  
   {  
	 int stringLength;
     double  result=0;  
     stringLength=s.length;  
     Stack a= new Stack(stringLength);  
     for(int i=0;i<stringLength;i++)  
     {  
       char ch=s[i].charAt(0);  
       if(ch>='0'&&ch<='9' || ch == '-')  
       {
    	   a.push(Double.parseDouble(s[i]));
       }  
       else 
       {  
         double rightOperand=a.pop();  
         double leftOperand=a.pop();  
         switch(ch)  
         {  
           case '+':result=leftOperand+rightOperand;  
              break;  
           case '-':result=rightOperand-leftOperand;  
              break;  
           case '*':result=leftOperand*rightOperand;  
              break;  
           case '/':result=rightOperand/leftOperand;  
              break;  
           case 'r':result= Math.pow(leftOperand,1/rightOperand);
        	   break;
           case '^':result= Math.pow(leftOperand, rightOperand);
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

 
