package packag;

public class InvalidPositionException extends Exception{
	
	/**
	 * This class is called when an invalid position is passed
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPositionException(String s) {
		super(s);
	}

}
