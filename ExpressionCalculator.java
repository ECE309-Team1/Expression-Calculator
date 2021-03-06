
//Testing Bryan's first Commit

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class ExpressionCalculator implements ActionListener, Calculator {

	// GUI declarations
JFrame calcWindow = new JFrame();	
JLabel errorLabelField = new JLabel(); // displays errors here
JTextField inputArea = new JTextField("Choose calculator mode FIRST, then enter input");	// numerical input here
JLabel empty = new JLabel();
JLabel ForX = new JLabel("for X = ", SwingConstants.RIGHT);
JLabel ToX = new JLabel("to X = ", SwingConstants.RIGHT);
JTextField forX = new JTextField("");
JTextField toX = new JTextField("");
JTextArea totalDisplay = new JTextArea(); // displays total
JTextArea logDisplay = new JTextArea();
ButtonGroup group = new ButtonGroup();
JRadioButton accumulatorMode = new JRadioButton("Accumulator");
JRadioButton expressionMode = new JRadioButton("Expression");
JRadioButton testMode = new JRadioButton("Test");
JRadioButton graphMode = new JRadioButton("Graph");    
JButton clearButton = new JButton("Clear"); // clear button
JScrollPane logScrollPane = new JScrollPane(logDisplay);
// Construct the Accumulating Calculator and that's it.
JPanel topPanel = new JPanel();
JPanel centerPanel = new JPanel();
JPanel bottomPanel = new JPanel();
String newLine = System.lineSeparator();
boolean debug;
int totalRight = 0;
int totalWrong = 0;
	
	public static void main(String[] args) 
	{
		ExpressionCalculator ec = new ExpressionCalculator();
		if(args.length != 0)
		{
			if(args[0].equals("true"))
				ec.debug = true;
			else
				ec.debug = false;
		}
	}
	
	public ExpressionCalculator() // constructor 
	{
		// GUI build
		
		//topPanel.setSize(100,100);
		topPanel.setLayout(new GridLayout(2,1));
		centerPanel.setLayout(new GridLayout(2,1));
		bottomPanel.setLayout(new GridLayout(2,5));
		
		
		
		topPanel.add(errorLabelField);
		topPanel.add(totalDisplay);
		
		centerPanel.add(logScrollPane);
		centerPanel.add(inputArea);
		
		group.add(accumulatorMode);
		group.add(expressionMode);
		group.add(testMode);
		group.add(graphMode);
		accumulatorMode.setSelected(true);
	
		bottomPanel.add(accumulatorMode);
		bottomPanel.add(expressionMode);
		bottomPanel.add(testMode);
		bottomPanel.add(graphMode);
		bottomPanel.add(empty);
		bottomPanel.add(ForX);
		bottomPanel.add(forX);
		bottomPanel.add(ToX);
		bottomPanel.add(toX);
		bottomPanel.add(clearButton);
		
		clearButton.setBackground(Color.ORANGE);
		errorLabelField.setForeground(Color.BLUE);
		
		calcWindow.getContentPane().add(topPanel,"North");
		calcWindow.getContentPane().add(centerPanel, "Center");
		calcWindow.getContentPane().add(bottomPanel, "South");
		
		calcWindow.setTitle("Expression Calculator");
		
		totalDisplay.setEditable(false);
		logDisplay.setEditable(false);
		totalDisplay.setFont (new Font("default",Font.BOLD,14));
		logDisplay.setFont (new Font("default",Font.BOLD,14));
		inputArea.setFont (new Font("default",Font.BOLD,14));
		toX.setFont (new Font("default",Font.BOLD,14));
		forX.setFont (new Font("default",Font.BOLD,14));
		calcWindow.setFont(new Font("default", Font.BOLD, 16));
		totalDisplay.setLineWrap(true);
		logDisplay.setLineWrap(true);
		
		
		totalDisplay.setWrapStyleWord(true);
		logDisplay.setWrapStyleWord(true);
		
		
		calcWindow.setSize(850,850);
		calcWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calcWindow.setVisible(true);
		
		
		clearButton.addActionListener(this);
		graphMode.addActionListener(this);
		testMode.addActionListener(this);
		expressionMode.addActionListener(this);
		accumulatorMode.addActionListener(this);
		// Making the input field as an action listener
		inputArea.addActionListener(this);
		forX.addActionListener(this);
		toX.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		// clear the label field
		errorLabelField.setText("");
		
		// clear button implementation
		if (ae.getSource() == clearButton)
		{
			inputArea.setText("");
			totalDisplay.setText("");
			errorLabelField.setText("");
		}
		
		else if(ae.getSource() == accumulatorMode)
		{
			inputArea.setText("");
			totalDisplay.setText("");
			errorLabelField.setText("");
			toX.setText("");
			toX.setEditable(false);
			forX.setText("");
			forX.setEditable(false);
		}
		
		else if(ae.getSource() == expressionMode)
		{
			inputArea.setText("");
			totalDisplay.setText("");
			errorLabelField.setText("");
			toX.setText("");
			toX.setEditable(false);
			forX.setText("");
			forX.setEditable(true);
		}
		
		else if(ae.getSource() == testMode)
		{
			inputArea.setText("");
			totalDisplay.setText("");
			errorLabelField.setText("");
			toX.setText("");
			toX.setEditable(false);
			forX.setText("");
			forX.setEditable(false);
		}
		
		else if(ae.getSource() == graphMode)
		{
			inputArea.setText("");
			totalDisplay.setText("");
			errorLabelField.setText("");
			toX.setText("");
			toX.setEditable(true);
			forX.setText("");
			forX.setEditable(true);
		}
		
		else			// Code for when the "Enter" key pressed.
		{
			if(accumulatorMode.isSelected() == true)
			{
				// 1) Get the current input, current total and send it for parsing
				String total;
				try
				{
					String prevTotal;
					String input;
					
					// Simple tests for prev total - (To avoid unnecessary Double.parse exceptions) 
					if(totalDisplay.getText().equals(""))
						prevTotal = "0";
					else
						prevTotal = totalDisplay.getText();
					
					// Simple tests for input - (To avoid unnecessary Double.parse exceptions) 
					if(inputArea.getText().equals(""))
						input = "0";
					else
						input = inputArea.getText();
							
					total = accumulate(prevTotal, input);
					
					// Update the total after successful parsing
					totalDisplay.setText(total);
					errorLabelField.setText("");
					
					// Update the log by reading in the input and appending it to the log.
					// NOTE-JJ - Append to log Display (not Total Display)
					logDisplay.append(newLine + prevTotal + " + " + input + " = " + total + newLine);
				    // scroll the outChatArea to the bottom
				    logDisplay.setCaretPosition(logDisplay.getDocument().getLength());
				}
				
				catch(IllegalArgumentException e)
				{
					// If parse not successful, Update error message.
					errorLabelField.setText(e.toString());
				}
			}
				
			else if(expressionMode.isSelected() == true)
			{
				// 1) Get the current input, current total and send it for parsing
				String total;
				try
				{
					String input;
					
					// Simple tests for input - (To avoid unnecessary Double.parse exceptions) 
					if(inputArea.getText().equals(""))
						input = "0";
					else
						input = inputArea.getText();
					
					String x = forX.getText();
							
					total = calculate(input, x);
					
					// Update the total after successful parsing
					totalDisplay.setText(total);
					errorLabelField.setText("");
					
					// Update the log by reading in the input and appending it to the log.
					// NOTE-JJ - Append to log Display (not Total Display)
					logDisplay.append(newLine + input + " = " + total + newLine);
				    // scroll the outChatArea to the bottom
				    logDisplay.setCaretPosition(logDisplay.getDocument().getLength());
				}
				
				catch(IllegalArgumentException e)
				{
					// If parse not successful, Update error message.
					errorLabelField.setText(e.toString());
				}
			}
			
			else if(testMode.isSelected() == true)
			{
				learningMode(inputArea.getText(), forX.getText());
			}
			
			else if(graphMode.isSelected() == true)
			{
			}
			
			else
			{
			}
				
			// clear the input field.
			inputArea.setText("");
		}
	}

	@Override
	public String accumulate(String total, String amount)
			throws IllegalArgumentException 
	{
			if(debug)
			{
				System.out.println("Entered input was: " + amount);
				System.out.println("Previous total was: " + total);
			}
			
			boolean operatorSpecified = false;
			// Check if operator is specified
			if(amount.indexOf('+') >= 0 || amount.indexOf('-') >= 0)
				operatorSpecified = true;
			
			// check for space character after the operator, if operator is specified
			if(operatorSpecified && amount.charAt(1) == ' ')
				throw new IllegalArgumentException("Please don't enter a space after the operator");
			
			// remove all blanks and $ symbols
			amount = amount.replace(" ", "");
			amount = amount.replace("$", "");
			
			if(debug)
				System.out.println("Input after formatting: " + amount);
			
			// Test for decimal digits, if it is a decimal number
			int decimalOffset = amount.indexOf('.');
			if(decimalOffset >= 0)
			{
				int decimalLength = amount.substring(decimalOffset + 1).length();
				if(decimalLength != 2)
					throw new IllegalArgumentException("If entering a decimal number, please enter"
							+ " exactly two digits after decimal point.");
			}
			
			// After passing all the tests convert String to double
			try
			{
				double newTotal = Double.parseDouble(amount) + Double.parseDouble(total);
				// Convert new total back to String
				if(newTotal == 0)
					return "0";
				BigDecimal  totalBD = new BigDecimal(newTotal,MathContext.DECIMAL64);//set precision to 16 digits
				totalBD = totalBD.setScale(2,BigDecimal.ROUND_UP);//scale (2) is # of digits to right of decimal point.
				String totalString = totalBD.toPlainString();// no exponents
				
				if(debug)
					System.out.println("Final result: " + totalString);
				
				return totalString;

			}
			
			catch(NumberFormatException nfe)
			{
				throw new IllegalArgumentException("Please enter proper arguments. Entered argument"
						+ " was :" + amount);
			}		
	}

	@Override
	public String calculate(String exp, String x)
			throws IllegalArgumentException {
		Double ans = null;
		
		//test cases
		if(exp.contains("X"))
			exp.replace('X', 'x');
		if(exp.contains("x"))
		{
			if(forX.getText().isEmpty())
				throw new IllegalArgumentException("Please specify a value for x");
		}
		
		try
		{
			ans = parse_expression(exp, x);
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException(e.getMessage());
		}
		return ans.toString();
	}
	
	
	public void learningMode(String exp, String x)
	{
		String leftExp = null;
		String rightExp = null;
		double leftVal = -2;
		double rightVal = -1;
		
		for (int i = 0; i < exp.length(); i++) 
		{
			if (exp.charAt(i) == '=')
			{
				leftExp = exp.substring(0, i).trim();
				rightExp = exp.substring(i+1).trim();
			}
		}
		if (leftExp == null || rightExp == null)
			throw new IllegalArgumentException("Please include an = sign");

		// to be tested once expression parser is working.
		leftVal = parse_expression(leftExp, x); // run leftExp through ExpressionParser(?) and get return value leftVal
		rightVal = parse_expression(rightExp, x); // run rightExp through ExpressionParser(?) and get return value rightVal
		
		if (leftVal == rightVal)
		{	
			totalDisplay.setText("Correct!");
			totalRight++;
			double percentRight = (totalRight*100) / (totalRight + totalWrong);
			logDisplay.append(newLine + "You have " + totalRight + " correct tests. You have been correct " +
								percentRight + "% of the time."); 
			
		}
		else
		{
			totalDisplay.setText("Oops!");
			totalWrong++;
			double percentWrong = (totalWrong*100)	 / (totalRight + totalWrong);
			logDisplay.append(newLine + "You have " + totalWrong + " incorrect tests. You have been incorrect " +
					percentWrong + "% of the time.");
		}				
	}
	
	public static double parse_expression(String exp, String text) throws IllegalArgumentException 
    {
		// TODO Auto-generated method stub
//API to outside world. Returns evaluated outside value.
    	
    	// Remove spaces in the expression.
    	exp = remove_spaces(exp);
    	

    	try
    	{
        	// replace constants with their values in the expression.
    		exp = replace_consts(exp, text);
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
        
         
        //remove spaces at corners without concern for surroundings.
        if(sb.charAt(0) == ' ')
        {
            sb.deleteCharAt(0);
        }
        if(sb.charAt(exp.length()-1) == ' ')
        {
            sb.deleteCharAt(exp.length()-1);
        }

        String acc = "-+*/r^x)( pie.";
        //iterate through rest
        for(int i=1; i<sb.length()-1 ; i++)
        {
			 //check for invalid vals
			 char c = sb.charAt(i);
			 //must be digit or one of these vals.
			 if(acc.indexOf(c) == -1 && !Character.isDigit(c))
			 {
			     throw new IllegalArgumentException("Invalid character detected: " + c);
			     
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
	    			
	    			exp = exp.replaceFirst(exp.substring(ind1, b), Double.toString(ans));
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
	    			if(Double.isInfinite(ans) || Double.isNaN(ans))
	    				throw new IllegalArgumentException("Cannot divide by zero");
	    			
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