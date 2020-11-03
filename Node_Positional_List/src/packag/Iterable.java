package packag;

import java.util.Iterator;

public interface Iterable<E> {
	
	/** Returns an iterator for the elements in the collection. */
	public Iterator<E> iterator();
	
	/** Returns an iterable representation of the list's positions. */
	public Iterable<E> positions();
}
