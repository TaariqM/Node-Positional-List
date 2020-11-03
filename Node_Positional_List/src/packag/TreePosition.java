package packag;

public interface TreePosition<E> extends Position<E> {
	
	/** Sets the element of a node to the passed element. */
	public void setElement(E o);
	
	/** Returns the children of a tree node. */
	public Iterable<Position<E>> getChildren();
	
	/** Sets the children of a tree node with position c. */
	public void setChildren(PositionalList<Position<E>> c);
	
	/** Returns the parent of a tree node. */
	public TreePosition<E> getParent();
	
	/** Sets the parent of a tree node with position v. */
	public void setParent(TreePosition<E> v);

}
