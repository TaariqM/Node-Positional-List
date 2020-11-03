package packag;

public class NonEmptyTreeException extends Exception {
	
	/**
	 * This class is called if a tree is not empty
	 */
	private static final long serialVersionUID = 1L;

	public NonEmptyTreeException(String s) {
		super(s);
	}

}
