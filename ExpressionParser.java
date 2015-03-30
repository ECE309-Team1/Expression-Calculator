import java.lang.*;
import java.io.*;

public class ExpressionParser
{

    public static void main(String[] args)
    {
        String exp = " abc";
        StringBuilder sb = new StringBuilder(exp);

               
        Double r = parse_expression("((3^6^2)^(2/(pi^e)))+x^(x+2)-300r5", "5");
        System.out.println("ans = " + r);  

    }

    public ExpressionParser()
    {
        System.out.println("A");
    }

    public static double parse_expression(String exp, String text) throws IllegalArgumentException 
    {
		// TODO Auto-generated method stub
//API to outside world. Returns evaluated outside value.
    	
    	// Remove spaces in the expression.
    	exp = ExpressionParser.remove_spaces(exp);
    	

    	try
    	{
        	// replace constants with their values in the expression.
    		exp = ExpressionParser.replace_consts(exp, text);
    		exp = eval_parentheses(exp);
    		
    	}
    	catch(Exception e)
    	{
    		throw new IllegalArgumentException(e.getMessage());
    	}
        return Double.parseDouble(exp);
	}

    public static String remove_spaces(String exp)
    {
        //Returns expression without spaces/
       
        StringBuilder sb = new StringBuilder(exp);
        //Check for invalid values.
        
  
        
        //remove spaces at corners without concern for surroundings.
        if(sb.charAt(0) == ' ')
        {
            sb.deleteCharAt(0);
        }
        if(sb.charAt(exp.length()-1) == ' ')
        {
            sb.deleteCharAt(exp.length()-1);
        }
        String acc = "-+*/r^x ";
        
        //iterate through rest
        for(int i=1; i<sb.length()-1 ; i++)
        {
            //check for invalid vals
            char c = sb.charAt(i);
            //must be digit or one of these vals.
            if(acc.indexOf(c) == -1 && !Character.isDigit(c))
            {
                throw new IllegalArgumentException("Invalid characterdetected: " + c);
                
            }
            
            
            if(sb.charAt(i) == ' ')
            {
                //Don't remove char if surroundings are both digits
                if(!(Character.isDigit(sb.charAt(i)) 
                   && Character.isDigit(sb.charAt(i))))
                   {
                       sb.deleteCharAt(i);
                   }
            }
        }
        
        
        
        
       
        return sb.toString();          
    }

    public static String replace_consts(String exp, String x)
    {
        //
        String new_exp = exp.replace("e", Double.toString(Math.E));

        new_exp = new_exp.replace("pi", Double.toString(Math.PI));

        //If contains, x, take input
        if(new_exp.contains("x")){
            try
            {
            	new_exp = new_exp.replace("x", x);
            }
            catch(Exception NumberFormatException)
            {
                throw new IllegalArgumentException("Entered value is not a number");
            }
        }
        
        return new_exp;
    }

    public static String eval_parentheses(String exp)
    {
    	int n = 0;
    	int m =  exp.length();
    	String toSend = "";
    	String result = "";

		int openPar = exp.length() - exp.replace(")", "").length();
    	int closePar = exp.length() - exp.replace("(", "").length();
    	
    	if(exp.contains("(") && exp.contains(")"))
    	{
			while(exp.contains("(") && exp.contains(")"))
			{
				if(exp.contains(")("))
				{
					throw new IllegalArgumentException("Error. No explicit multiplication or parantheses not in right order");
				}
				if(!(openPar == closePar))
				{
					throw new IllegalArgumentException("Parantheses do not match.");
				}
				m = exp.indexOf(')');
	    		n = exp.substring(0,m).lastIndexOf('(');
	    		
	    		toSend = exp.substring(n+1, m);
	    		result = eval_exp(toSend);
	    		exp = exp.replace(exp.substring(n, m+1), result);
	    		exp = exp.replace("--", "+");
	    		System.out.println("Expression: " + exp);
			}
    	}
		
		toSend = exp.substring(0);
		result = eval_exp(toSend);
		exp = exp.replace(exp, result);
        return exp;
    }

public static String eval_exp(String exp)
{
    	
		for(int i = 0; i < exp.length(); i++)
		{
			if(exp.charAt(i) == 'r' || exp.charAt(i) == '^')
			{
				if(exp.charAt(i) == 'r')
				{
					int op_ind;
			    	int ind1 = 0;
			    	int ind2 = -1;
			    	String op1;
			    	String op2;
			    	int a = 0;
			    	int b = exp.length();
			    	
					op_ind = exp.indexOf('r');
					for(a = op_ind; a>=0; a--)
	    			{
	    				if(exp.charAt(a) == '*' || exp.charAt(a) == '/' || exp.charAt(a) == '+' || exp.charAt(a) == '-' )
	    				{
	    					ind1 = a+1;
	    					break;
	    				}
	    			}
	    			
	    			for(b = op_ind+1; b<exp.length(); b++)
	    			{
	    				if(exp.charAt(b) == '*' || exp.charAt(b) == '/' || exp.charAt(b) == '+' || exp.charAt(b) == '-' || exp.charAt(b) == '^' || exp.charAt(b) == 'r')
	    				{
	    					ind2 = b;
	    					break;
	    				}
	    			}
	    			
	    			op1 = exp.substring(ind1, i);
	    			
	    			if(ind2 > 0)
	    				op2 = exp.substring(i+1, ind2);
	    			else
	    				op2 = exp.substring(i+1);
	    			
	    			double ans = Math.pow(Double.parseDouble(op1), 1/Double.parseDouble(op2));
	    			
	    			exp = exp.replace(exp.substring(ind1, b), Double.toString(ans));
	    			i = 0;
				}
				 
				else
				{
					int op_ind;
			    	int ind1 = 0;
			    	int ind2 = -1;
			    	String op1;
			    	String op2;
			    	int a = 0;
			    	int b = exp.length();
			    	
					op_ind = exp.indexOf('^');
					for(a = op_ind; a>=0; a--)
	    			{
	    				if(exp.charAt(a) == '*' || exp.charAt(a) == '/' || exp.charAt(a) == '+' || exp.charAt(a) == '-' )
	    				{
	    					ind1 = a+1;
	    					break;
	    				}
	    			}
	    			
	    			for(b = op_ind+1; b<exp.length(); b++)
	    			{
	    				if(exp.charAt(b) == '*' || exp.charAt(b) == '/' || exp.charAt(b) == '+' || exp.charAt(b) == '-' || exp.charAt(b) == '^' || exp.charAt(b) == 'r')
	    				{
	    					ind2 = b;
	    					break;
	    				}
	    			}
	    			
	    			op1 = exp.substring(ind1, i);
	    			
	    			if(ind2 > 0)
	    				op2 = exp.substring(i+1, ind2);
	    			else
	    				op2 = exp.substring(i+1);
	    			
	    			double ans = Math.pow(Double.parseDouble(op1), Double.parseDouble(op2));
	    			
	    			exp = exp.replace(exp.substring(ind1, b), Double.toString(ans));
	    			i = 0;
				}
			}
		}
		
		for(int i = 0; i < exp.length(); i++)
		{
			if(exp.charAt(i) == '*' || exp.charAt(i) == '/')
			{
				if(exp.charAt(i) == '*')
				{
					int op_ind;
			    	int ind1 = 0;
			    	int ind2 = -1;
			    	String op1;
			    	String op2;
			    	int a = 0;
			    	int b = exp.length();
			    	
					op_ind = exp.indexOf('*');
					for(a = op_ind; a>=0; a--)
	    			{
	    				if(exp.charAt(a) == '+' || exp.charAt(a) == '-' )
	    				{
	    					ind1 = a+1;
	    					break;
	    				}
	    			}
	    			
	    			for(b = op_ind+1; b<exp.length(); b++)
	    			{
	    				if(exp.charAt(b) == '*' || exp.charAt(b) == '/' || exp.charAt(b) == '+' || exp.charAt(b) == '-')
	    				{
	    					ind2 = b;
	    					break;
	    				}
	    			}
	    			
	    			op1 = exp.substring(ind1, i);
	    			
	    			if(ind2 > 0)
	    				op2 = exp.substring(i+1, ind2);
	    			else
	    				op2 = exp.substring(i+1);
	    			
	    			double ans = Double.parseDouble(op1) * Double.parseDouble(op2);
	    			
	    			exp = exp.replace(exp.substring(ind1, b), Double.toString(ans));
	    			i = 0;
				}
				 
				else
				{
					int op_ind;
			    	int ind1 = 0;
			    	int ind2 = -1;
			    	String op1;
			    	String op2;
			    	int a = 0;
			    	int b = exp.length();
			    	
					op_ind = exp.indexOf('/');
					for(a = op_ind; a>=0; a--)
	    			{
	    				if(exp.charAt(a) == '+' || exp.charAt(a) == '-' )
	    				{
	    					ind1 = a+1;
	    					break;
	    				}
	    			}
	    			
	    			for(b = op_ind+1; b<exp.length(); b++)
	    			{
	    				if(exp.charAt(b) == '*' || exp.charAt(b) == '/' || exp.charAt(b) == '+' || exp.charAt(b) == '-' || exp.charAt(b) == '^' || exp.charAt(b) == 'r')
	    				{
	    					ind2 = b;
	    					break;
	    				}
	    			}
	    			
	    			op1 = exp.substring(ind1, i);
	    			
	    			if(ind2 > 0)
	    				op2 = exp.substring(i+1, ind2);
	    			else
	    				op2 = exp.substring(i+1);
	    			
	    			double ans = Double.parseDouble(op1) / Double.parseDouble(op2);
	    			
	    			exp = exp.replace(exp.substring(ind1, b), Double.toString(ans));
	    			i = 0;
				}
			}
		}
    	
		for(int i = 0; i < exp.length(); i++)
		{
			if(exp.charAt(i) == '+' || exp.charAt(i) == '-')
			{
				if(exp.charAt(i) == '+')
				{
					int op_ind;
			    	int ind1 = 0;
			    	int ind2 = -1;
			    	String op1;
			    	String op2;
			    	int a = 0;
			    	int b = exp.length();
			    	
					op_ind = exp.indexOf('+');
					for(a = op_ind; a>=0; a--)
	    			{
	    				if(exp.charAt(a) == '*' || exp.charAt(a) == '/')
	    				{
	    					ind1 = a+1;
	    					break;
	    				}
	    			}
	    			
	    			for(b = op_ind+1; b<exp.length(); b++)
	    			{
	    				if(exp.charAt(b) == '+' || exp.charAt(b) == '-')
	    				{
	    					ind2 = b;
	    					break;
	    				}
	    			}
	    			
	    			op1 = exp.substring(ind1, i);
	    			
	    			if(ind2 > 0)
	    				op2 = exp.substring(i+1, ind2);
	    			else
	    				op2 = exp.substring(i+1);
	    			
	    			double ans = Double.parseDouble(op1) + Double.parseDouble(op2);
	    			
	    			exp = exp.replace(exp.substring(ind1, b), Double.toString(ans));
	    			i = 0;
				}
				 
				else
				{
					int op_ind;
			    	int ind1 = 0;
			    	int ind2 = -1;
			    	String op1;
			    	String op2;
			    	int a = 0;
			    	int b = exp.length();
			    	
					op_ind = exp.indexOf('-');
					for(a = op_ind; a>=0; a--)
	    			{
	    				if(exp.charAt(a) == '*' || exp.charAt(a) == '/')
	    				{
	    					ind1 = a+1;
	    					break;
	    				}
	    			}
	    			
	    			for(b = op_ind+1; b<exp.length(); b++)
	    			{
	    				if(exp.charAt(b) == '+' || exp.charAt(b) == '-')
	    				{
	    					ind2 = b;
	    					break;
	    				}
	    			}
	    			
	    			op1 = exp.substring(ind1, i);
	    			
	    			if(ind2 > 0)
	    				op2 = exp.substring(i+1, ind2);
	    			else
	    				op2 = exp.substring(i+1);
	    			
	    			double ans = Double.parseDouble(op1) - Double.parseDouble(op2);
	    			
	    			exp = exp.replace(exp.substring(ind1, b), Double.toString(ans));
	    			i = 0;
				}
			}
		}
		
		return exp;
    }    

}
