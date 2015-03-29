import java.lang.*;
import java.util.Arrays;
import java.util.Stack;
import java.io.*;

public class ExpressionParser
{

    public static void main(String[] args)
    {
        String str1 = "1+e+pi";
        String str2 ="";
        
        try {
	        str2 = replace_consts(str1);
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        
        
        System.out.println(str2);

    }

    public ExpressionParser()
    {
        System.out.println("A");
    }


    public static double parse_expression(String exp)
    {
        //API to outside world. Returns evaluated outside value.
        return 0.0;
    }
    public static String remove_spaces(String exp)
    {
        //Returns expression without spaces/
       
        StringBuilder sb = new StringBuilder(exp);
        
         
        //remove spaces at corners without concern for surroundings.
        if(sb.charAt(0) == ' ')
        {
            sb.deleteCharAt(0);
        }
        if(sb.charAt(exp.length()-1) == ' ')
        {
            sb.deleteCharAt(exp.length()-1);
        }

        //iterate through rest
        for(int i=1; i<sb.length()-1 ; i++)
        {
            if(sb.charAt(i) == ' ')
            {
                //Don't remove char if surroundings are both digits
                if(!(Character.isDigit(sb.charAt(i-1)) 
                   && Character.isDigit(sb.charAt(i+1))))
                   {
                       sb.deleteCharAt(i);
                   }
                else
                {
                	throw new IllegalArgumentException("Invalid spaces in statement");
                }
            }
        }
       
        return sb.toString();          
    }

    public static String replace_consts(String exp) throws IOException
    {
        //
        String new_exp = exp.replace("e", Double.toString(Math.E));
        
        new_exp = new_exp.replace("pi", Double.toString(Math.PI));
        
        new_exp = new_exp.replace("PI", Double.toString(Math.PI));

        //If contains, x, take input
        if(new_exp.contains("x")){
            BufferedReader br = new BufferedReader(
                                new InputStreamReader(System.in));
            String x = br.readLine();
            try
            {
            }
            catch(Exception NumberFormatException)
            {
                System.out.println("Enterred value is not a number");
            }
        }
        
        return new_exp;
    }

    public static String insert_delimiters(String exp)
    {
    	/* Receives string with no spaces.
    	 * inserts tab characters in between
    	 * Tab characters because it's unlikely as natural input.
    	 */
    	
    	StringBuilder sb = new StringBuilder(exp);
    	
    	//insert spaces surrounding all ops.
    	for(int i=0; i < sb.length(); i++)
    	{
    		if(sb.charAt(i) == '^'
    		   || sb.charAt(i) == 'r'
    		   || sb.charAt(i) == '*'
    		   || sb.charAt(i) == '/'
    		   || sb.charAt(i) == '+'
    		   || sb.charAt(i) == '-')
    		{
    		    sb.insert(i, '\t');
    		    sb.insert(i+2, '\t');
    		    i ++;

                System.out.println(sb.toString());
    		}
    	}
    	
    	return sb.toString();
    }
    
    public static double shunting_method(String exp)
    {
        /*
         * Receives string with tab delimiters around operands.
         * 
         * 
         * Returns evaluated statement.
         */
        
        String[] output_queaue;
        
        Stack fnct_stack = new Stack();
        
        
        //Seperate into tokens along delimeters.
        //STR array contains all numbers, and operators.
        String[] tokens = exp.split("\\s+");
        
        return 0;
    }
    
    
    
    public static String eval_parentheses(String exp)
    {
        return "";
    }

    public static String eval_exp(String exp)
    {
        //
        return "";
    }

    public static String eval_mult(String exp)
    {
        return "";
    }

    public static String eval_add(String exp)
    {
        return "";
    }

    public static String smallest_exp(String exp)
    {
        return "";
    }


}
