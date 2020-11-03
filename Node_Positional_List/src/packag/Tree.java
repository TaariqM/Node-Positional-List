package packag;

import java.util.Iterator;

public interface Tree<E> extends Iterable<E> {

	/** Returns the number of positions (and hence elements) that are contained in the tree */
	int size();
	
	/**Returns the position of the root of the tree (or null if tree is empty) */
	Position<E> root();
	
	/** Returns the position of the parent of position p (or null if p is the root). */
	Position<E> parent(Position<E> p) throws InvalidPositionException;
	
	/**Returns an iterable collection containing the children of position p (if any). */
	Iterable<Position<E>> children(Position<E> p) throws InvalidPositionException;
	
	/**Returns the number of children of position p. */
	int numChildren(Position<E> p) throws InvalidPositionException;
	
	/**Returns true if position p has at least one child. */
	boolean isInternal(Position<E> p) throws InvalidPositionException;
	
	/**Returns true if position p does not have any children. */
	boolean isExternal(Position<E> p) throws InvalidPositionException;
	
	/**Returns true if position p is the root of the tree. */
	boolean isRoot(Position<E> p) throws InvalidPositionException;
	
	/**Returns true if the tree does not contain any positions (and thus no elements). */
	boolean isEmpty();
	
	/** Returns an iterator for all elements in the tree. */
	public Iterator<E> iterator();
	
	/** Returns an iterable representation of the list's positions. */
	public Iterable<E> positions();
	
}
