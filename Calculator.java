
public interface Calculator extends Accumulator{
	
	public String calculate(String exp, String ans)
		throws IllegalArgumentException;
}