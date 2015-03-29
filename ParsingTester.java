import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;


public class ParsingTester {
	
	String res = "";
	ExpressionParser ep = new ExpressionParser();
	
	String alpha_str1 = "a b c";
	String alpha_res1 = "abc";
	
	String easy_str1 = "1 + 2 + 3";
	String easy_res1 = "1+2+3";
	
	String sym_str1 = "pi + e + 5";
	String sym_res1 = "" + Math.PI + "+" 
					+ Math.E + "+" + "5";
	
	String bad_str_spaces = "1+ 2 3 +4";

	@Test
	public void testSpaceremover()
	{
		
		res = ep.remove_spaces(alpha_str1);
		assertEquals(res,alpha_res1);
		
		res = ep.remove_spaces(easy_str1);
		assertEquals(res, easy_res1);
		
	}
	
	@Test
	public void testConstantReplacement() throws IOException
	{
		res = ep.remove_spaces(sym_str1);
		res = ep.replace_consts(res);
		
		assertEquals(res, sym_res1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorOnBadInput()
	{
		res = ep.remove_spaces(bad_str_spaces);
	}
	
	@Test
	public void testinsert_delim()
	{
	    res = ep.insert_delimiters("1+2+3");
	}
	
	@Test
	public void test_shunting()
	{
	    ep.shunting_method(easy_str1);
	    
	}
	
	@Test
	public void test_parens()
	{
	    String[] tokens = {"(", "5", "*",
	                       "(", ")", ")"};
	    int cPos = ep.findClosingParen(tokens,0);
	    assertEquals(cPos,5);
	}
	
	
	@Test
	public void test_infix_to_postfic()
	{        
	    String infix = "3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3";
	    System.out.println("postfix: " + ep.infixToPostfix(infix));
	    
	}
	

}
