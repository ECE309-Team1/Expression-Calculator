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
   
    public static String[] get_token_array(String in)
    {
        /*
         * Recieve string with tab delim between all ops."
         * 
         * Return STR array with item ine each one.
         * 
         * This is necessary to account for decimal inputs
         * items like PI.
         */
        
        return in.split("\\s+");
    }
    
    public static int findClosingParen(String[] tokens, int oPos)
    {
        /*
         * Recieves String array of token.
         * Recieves index of left paren
         */
        
        if(!tokens[oPos].equals("("))
        {
            throw new IllegalArgumentException("Unmatches paren");
        }
        
        
        int cPos = oPos;
        int i=1;
        
        while(i>0)
        {
            String s = tokens[++cPos];
            
            if(i>tokens.length || cPos > tokens.length)
            {
                throw new IllegalArgumentException("Unmatches paren");
            }
            
            if(s.equals("("))
            {
                i++;
            }
            else if (s.equals(")"))
            {
                i--;
            }
        }
        
        if(!tokens[cPos].equals(")"))
        {
            throw new IllegalArgumentException("Unmatches paren");
        }
        
        return cPos;
    }
    
    public static String[] shunting_method(String exp)
    {
        /*
         * Receives string with tab delimiters around operands.
         * 
         * 
         * Returns evaluated statement.
         */       
        
        //Seperate into tokens along delimeters.
        //STR array contains all numbers, and operators.
        String[] tokens = get_token_array(exp);
        
        final String ops = "-+/*r^";
        
        String[] output_q = new String[tokens.length];
        
        int out_i = 0;
        

        StringBuilder sb = new StringBuilder();
        Stack<String> op_stack = new Stack<String>();
        Stack<Integer> s = new Stack<>();
        
        
        for(String t: tokens)
        {
            //"Token" may be more than a simple char, if it's 
            //3.1415, etc
            char c = t.charAt(0);
            int op_value = ops.indexOf(c);
            
            if(Character.isDigit(c))
            {
                sb.append(t);
            }
            /*
             * Skip steps 3-4, not for this assignment.
             * (no individual functions
             */
            
            //If the token is an operator, o1, then:
            if(is_operator(c))
            {
               while(!op_stack.isEmpty())
               {
                   int p2 = s.peek() / 2;
                   int p1 = op_value/2;
                   
                   if ( p2 > p1 || (p2 == p1))
                   {
                       sb.append(ops.charAt(s.pop())).append(' ');
                   }
                   
               }
            }
            
            
            
            //If the token is a left parenthesis, then push it onto the stack.
            else if (c == '(')
            {
                op_stack.push(t);
            }
            //If the token is a right parenthesis:
            else if (c == ')')
            {
                
                
                //If the stack runs out without finding a left parenthesis, 
                //then there are mismatched parentheses.
                try{
                    
                //Until the token at the top of the stack is a left parenthesis,
                //pop operators off the stack onto the output queue.
                while(!op_stack.peek().equals("("))
                {
                    sb.append(op_stack.pop());
                }
                //Pop the left parenthesis from the stack,
                //but not onto the output queue.\
                op_stack.pop();
                }
                catch(Exception EmptyStackException)
                {
                    throw new IllegalArgumentException("Unmatched parens");
                }
                
            }
            
        }
        //When there are no more tokens to read:
        
        //While there are still operator tokens in the stack
        while(! op_stack.isEmpty())
        {
            String t2 = op_stack.pop();
            
            if(t2.equals("(") || t2.equals(")"))
            {
                throw new IllegalArgumentException("Mismatched parens");
            }
            sb.append(t2);
        }
        
        System.out.println("shunt str: " + sb.toString());
        
        
        return output_q;
    }
    
    static String infixToPostfix(String infix) {
        final String ops = "-+/*^";
        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();
 
        for (String token : infix.split("\\s")) {
            char c = token.charAt(0);
            int idx = ops.indexOf(c);
            if (idx != -1 && token.length() == 1) {
                if (s.isEmpty())
                    s.push(idx);
                else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
                            sb.append(ops.charAt(s.pop())).append(' ');
                        else break;
                    }
                    s.push(idx);
                }
            } else if (c == '(') {
                s.push(-2);
            } else if (c == ')') {
                while (s.peek() != -2)
                    sb.append(ops.charAt(s.pop())).append(' ');
                s.pop();
            } else {
                sb.append(token).append(' ');
            }
        }
        while (!s.isEmpty())
            sb.append(ops.charAt(s.pop())).append(' ');
        return sb.toString();
    }
    
 
    private static boolean is_operator(char c)
    {
        
        if(c == '^' || c == 'r' || c== '*'|| c == '/'
                || c == '+'|| c == '-')
            return true;
        
        return false;
    }
    
 
   public String str_arr_to_str(String[] in)
    {
        String rslt = "";
        for (int i=0; i<in.length; i++)
        {
            rslt += in[i];
        }
        return rslt;
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
