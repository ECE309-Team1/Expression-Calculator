import java.lang.*;
import java.io.*;

public class ExpressionParser
{

    public static void main(String[] args)
    {
        String exp = " abc";
        StringBuilder sb = new StringBuilder(exp);

               
        String r = remove_spaces("a b c d");
        System.out.println(r);  

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
                if(!(Character.isDigit(sb.charAt(i)) 
                   && Character.isDigit(sb.charAt(i))))
                   {
                       sb.deleteCharAt(i);
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
        
        return "";
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
