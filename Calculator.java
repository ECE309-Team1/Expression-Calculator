
public interface Calculator extends Accumulator{
	
	public String calculate(String exp)
		throws IllegalArgumentException;
}