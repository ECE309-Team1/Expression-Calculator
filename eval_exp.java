public static Double eval_exp(String exp)
{
    	String leftOperand;
    	String rightOperand;
    	char op = ' ';
    	
    	for (int i = 0; i < exp.length(); i++) 
    	{
    		char c = exp.charAt(i);
    		rightOperand = exp.substring(i+1).trim();
    		leftOperand = exp.substring(0, i).trim();
    		if ((c== '+') || (c== '-') || (c== '*') || (c== '/') || (c== 'r') || (c== '^'))
    		{	
    			op = c;
    		}
    	}
    	
		if (op == ' ') 
		{
			Double value = Double.parseDouble(exp);
			return value;
		} 
    	
		
		double leftValue; 
		double rightValue;
		double finalValue;
		
		try 
		{
			leftValue = Double.parseDouble(leftOperand); 
			rightValue = Double.parseDouble(rightOperand);
			
		}
		catch (NumberFormatException nfe) 
		{
			System.out.println("Left or Right operand is not a number");
		}
    	
		switch(op) {
		case '+' : finalValue = leftValue + rightValue;
		break;
		
		case '-' : finalValue = leftValue - rightValue;
		break;
		
		case '*' : finalValue = leftValue * rightValue;
		break;
		
		case '/' : finalValue = leftValue / rightValue;
		break;
		
		case 'r' : finalValue = pow(leftValue,1/rightValue);
		break;
		
		case '^' : finalValue = pow(leftValue,rightValue);
		break;
		
		}   
}    
