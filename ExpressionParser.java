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

        String acceptable_chars = "-+*/r^x() ";
 
        //iterate through rest
        for(int i=1; i<sb.length()-1 ; i++)
        {
            //check for invalid chars
            
            char c = sb.charAt(i);
            if(acceptable_chars.indexOf(c) == -1){
                throw new IllegalArgumentException("Invalid character found");
            }
            
            
            
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
    		if(sb.charAt(i) == '^'    || sb.charAt(i) == 'r'
    		   || sb.charAt(i) == '*' || sb.charAt(i) == '/'
    		   || sb.charAt(i) == '+' || sb.charAt(i) == '-'
    		   || sb.charAt(i) == '(' || sb.charAt(i) == ')')
    		{
    		    sb.insert(i, '\t');
    		    sb.insert(i+2, '\t');
    		    i ++;

    		}
    	}
    	
    	return sb.toString();
    }
   
    public static String[] get_token_array(String in) throws IOException
    {
        /*
         * Recieve string with tab delim between all ops."
         * 
         * Return STR array with item ine each one.
         * 
         * This is necessary to account for decimal inputs
         * items like PI.
         */
        String a = remove_spaces(in);
        String b = replace_consts(a);
        String c = insert_delimiters(b);
        
        
        return c.split("\\s+");
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
    
    public static String[] shunting_method(String exp) throws IOException
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
        
        System.out.println("Sanitized input");
        System.out.println(Arrays.toString(tokens));
        final String ops = "-+/*r^";
                
        int out_i = 0;
        
        String[] output_q = new String[tokens.length];
        Stack<String> op_stack = new Stack<String>();
        
        for(String t: tokens)
        {
            System.out.println("Current token: " + t);
            char c = t.charAt(0);
            
            
            
            //If token is #, add to output quaeu
            if(Character.isDigit(c))
            {
                output_q[out_i++] = t;
                continue;
            }
            
            
            //If the token is an operator, o1, then:
            else if(ops.indexOf(c) != -1)
            {
                //while there is an operator token, o2, 
                //at the top of the operator stack
                //o1 is prec-less than 
                
               
               // while(ops.indexOf(op_stack.peek().charAt(0)) != -1)   
                while(! op_stack.isEmpty())
                {
                   System.out.println("loop: " + t);
                  //while there is op token at top of stack.
                  char o2 = op_stack.peek().charAt(0);
                  //if o1 is operator & o2 is higher prec.
                  if(ops.indexOf(o2) >= 0 && op_precedence(o2,c)) //check for op.
                  {
                      //pop o2 onto 
                      output_q[out_i++] = op_stack.pop();
                      //break;
                  } else{                      
                      break;
                  }
                  
                    
                }
                System.out.println("Pushing to stack: " + t);
                op_stack.push(t);
                
            }
            else if(t.equals("("))
            {
                System.out.println("Pushing: " + t);
                op_stack.push(t);
            }
            else if(t.equals(")"))
            {
                //If the stack runs out without finding a left parenthesis, 
                //then there are mismatched parentheses.
                try{
                    
                //Until the token at the top of the stack is a left parenthesis,
                //pop operators off the stack onto the output queue.
                while(!op_stack.peek().equals("("))
                {
                    output_q[out_i++] = op_stack.pop();
                }
                //Pop the left parenthesis from the stack,
                //but not onto the output queue.\
                System.out.println(" Popped but not added: " + op_stack.pop());
                }
                catch(Exception EmptyStackException)
                {
                    throw new IllegalArgumentException("Unmatched parens");
                }
            }
            
            
        }
        //When there are no more tokens to read:
        String t2 = op_stack.pop();
        if(t2.equals("(") || t2.equals(")"))
        {
            throw new IllegalArgumentException("Mismatched parens");
        }
        
        //While there are still operator tokens in the stack
        while(! op_stack.isEmpty())
        {
            System.out.println("Popping extra off stack: "
                    + op_stack.peek());
            output_q[out_i++] = op_stack.pop();
        }
        
        System.out.println("test: " + str_arr_to_str(output_q));
        
        return output_q;
    }
    
    static boolean op_precedence(char a, char b)
    {
        /*
         * true when first arg has higher precedence.
         * 
         */
        final String ops = "-+/*r^";
        
        //Dividing by two makes them have "equal" with pair.
        //ie: - = 0, +=1, both are 0.
        int p1 = ops.indexOf(a) / 2;
        int p2 = ops.indexOf(b) / 2;
        
        
        
        if  (p1 >= p2) return true;
        else           return false;
        
    }


    private static boolean is_operator(char c)
    {
        
        if(c == '^' || c == 'r' || c== '*'|| c == '/'
                || c == '+'|| c == '-')
            return true;
        
        return false;
    }
    
 
    public static String str_arr_to_str(String[] in)
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
