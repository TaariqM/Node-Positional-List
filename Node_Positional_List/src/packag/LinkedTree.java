package packag;

import java.util.Iterator;

public class LinkedTree<E> implements Tree<E> {
	
	//----------------------------nested TreeNode class-----------------------
	public class TreeNode<E> implements TreePosition<E> {

		private E element;																		//element stored at this node
		private TreePosition<E> parent; 														//adjacent node
		private PositionalList<Position<E>> children;											//children nodes
		
		/**
		 * Constructs a tree node with an element, parent and children
		 * 
		 * @param e element stored in the node
		 * @param above the parent node
		 * @param children list of children nodes
		 * @throws InvalidPositionException if position is invalid
		 */
		public TreeNode(E e, TreePosition<E> above, PositionalList<Position<E>> children) {
			element = e;																		//sets element to e
			parent = above;																		//sets parent to above
			this.children = children;															//sets this.children to children
		}
		/**
		 * Returns the element stored at this position.
		 *
		 * @return the stored element
		 * @throws IllegalStateException if position no longer valid
		 */
		@Override
		public E getElement() throws IllegalArgumentException {
			return element;																		//returns the element stored at this position
		}

		/**
		 * Sets the element of a node to the passed element
		 */
		@Override
		public void setElement(E o) {
			element = o;																		//sets element to the passed element
		}

		/**
		 * Returns the children of a tree node
		 * 
		 * @return the children of a tree node
		 */
		@Override
		public Iterable<Position<E>> getChildren() {
			return  (Iterable<Position<E>>) children;											//returns the children of a tree node
		}

		/**
		 * Sets the children of a tree node with position c
		 * 
		 * @param c position c
		 */
		@Override
		public void setChildren(PositionalList<Position<E>> c) {
			children = c;																		//set the children of a tree node with position c
		}

		/**
		 * Returns the parent of a tree node
		 * 
		 * @return the parent of a tree node
		 */
		@Override
		public TreePosition<E> getParent() {
			return parent;																		//returns the parent of a tree node
		}

		/**
		 * Sets the parent of a tree node with position v
		 * 
		 * @param v tree node with position v
		 */
		@Override
		public void setParent(TreePosition<E> v) {
			parent = v;																			//set the parent of a tree node with position v
		}
	}
	//----------------------------end of nested TreeNode class-----------------------
	
	//----------------------------nested ElementIterator class-----------------------
	/** This class adapts the iteration produced by positions() to return elements. */
	private class ElementIterator implements Iterator<E> {

		Iterator<Position<E>> posIterator = (Iterator<Position<E>>) positions().iterator();		//creates an iterator
		
		/** Tests whether the iterator has a next object. 
		 * 
		 * @return true if iterator has a next object, false otherwise
		 */
		@Override
		public boolean hasNext() {
			return posIterator.hasNext();														//returns true if there is a next object, false otherwise
		}

		/** Returns the next position in the iterator. 
		 * 
		 * @return the element in the next position in the iterator 
		 */
		@Override
		public E next() {
			return posIterator.next().getElement();												//return element
		}
		
		/** Removes the element returned by most recent call to next. 
		 * 
		 * @throws UnsupportedOperationException
		 */
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();											//throw exception
		}
	}
	//----------------------------end of nested ElementIterator class--------------------
	
	protected TreePosition<E> root;																//root of the tree
	protected int size;																			//number of nodes in the tree 
	protected E element;																		//element to be stored in a node
	
	/**
	 * Constructor that creates an empty linked tree
	 */
	public LinkedTree() {
		root = null;																			//root is set to null
		size = 0;																				//the size of the linked tree is 0
	}
	 
	/**
	 * Constructor that creates a linked tree with one node
	 * with a specific element inside
	 * 
	 * @param root passed node becomes the node of the tree
	 * @param elem passed element becomes the element inside 
	 * 		  the node
	 */
	public LinkedTree(TreePosition<E> root, E elem) {
		this.root = root;																		//this.root is set to root
		element = elem;																			//element is set to elem
		size = 1;																				//size is set to one
	}
	
	/**
	 * Constructor that creates a linked tree with a subtree that stores
	 * an element at the root of the subtree
	 * 
	 * @param elem element to be stored in the root of the subtree 
	 * @param subtrees list of nodes that are in the subtree
	 * @throws InvalidPositionException if position is invalid
	 */
	public LinkedTree(E elem, PositionalList<E> subtrees) throws InvalidPositionException {
		PositionalList<E> kids = new NodePositionalList<E>();									//a positional list created to hold the elements in the subtree
		root = new TreeNode(elem, null, subtrees);												//a newly created root node
		size = 1;																				//size of tree is set to one
		int y = 0;																				//variable to be incremented for the while loop
	
		while (y < subtrees.size()) {															//while y is less than the size of the subtree
			LinkedTree<E> subtree = (LinkedTree<E>) subtrees.first();							//creates a subtree that holds the first element in the subtrees list
			TreeNode<E> node = (LinkedTree<E>.TreeNode<E>) subtree.root();						//node becomes the root of the subtree
			node.setParent(root);																//the variable 'root' becomes the root of 'node'
			kids.addLast((E) node);																//'node' is added to the end of the 'kids' positional list
			subtrees.remove(subtrees.first());													//remove the first element from the subtrees positional list
			y++;																				//increment y
			size = size + subtree.size();														//the size of the subtree is added to the size of the tree
		}
	}
	
	/** 
	 * Returns the number of positions (and hence elements) that are contained in the tree 
	 * 
	 * @return the number of positions in the tree
	 */
	@Override
	public int size() {
		return size;																			//returns the number of positions in the tree
	}

	/**
	 * Returns the position of the root of the tree (or null if tree is empty) 
	 * 
	 * @return the position of the root of the tree or null if the tree is empty
	 */
	@Override
	public Position<E> root() {
		if(isEmpty()) {																			//if tree is empty
			return null;																		//return null
		}
		return root;																			//returns the postion of the root of the tree
	}

	/**
	 * Returns the position of the parent of position p (or null if p is the root). 
	 * 
	 * @param p the position that is passed
	 * @return 	the position of the parent of position p 
	 * @throws  InvalidPositionException if position is invalid 
	 */
	@Override
	public Position<E> parent(Position<E> p) throws InvalidPositionException {
		if(p == root) {																			//if the position is equal to the root
			return null;																		//throw exception
		}
		TreePosition<E> node = checkPosition(p);												//check if p is a valid position
		return node.getParent();																//return the parent of position p
	}

	/**
	 * Returns an iterable collection containing the children of position p (if any).
	 * 
	 * @param p	the position that is passed
	 * @return 	iterable collection containing the children of position p
	 * @throws 	InvalidPositionException if position is invalid
	 */
	@Override
	public Iterable<Position<E>> children(Position<E> p) throws InvalidPositionException {
		TreePosition<E> node = checkPosition(p);												//check if p is a valid position
		return (Iterable<Position<E>>) node.getChildren();										//return an iterable collection containing the children of position p
	}

	/**
	 * Returns the number of children of position p.
	 * 
	 * @param p the position that is passed
	 * @return  the number of children of position p
	 */
	@Override
	public int numChildren(Position<E> p) {
		TreePosition<E> node = (TreePosition<E>) p;												//check if p is a valid position
		int count = 0;																			//variable to hold the number of children
		while(node.getChildren().iterator().hasNext()) {										//while there is a next child
			count++;																			//increment count by 1
		}
		return count;																			//return the count
	}

	/**
	 * Returns true if position p has at least one child.
	 * 
	 * @param	the position that is passed
	 * @return	true if position p has at least one child
	 * @throws InvalidPositionException if position is invalid
	 */
	@Override
	public boolean isInternal(Position<E> p) throws InvalidPositionException {
		TreePosition<E> node = checkPosition(p);												//check if p is a valid position
		if(numChildren(node) == 0) {															//if number of children is 0
			return false;																		//return false
		}
		else {
			return true;																		//else return true
		}
	}

	/**
	 * Returns true if position p does not have any children.
	 * 
	 * @param p the position that is passed
	 * @return	true if position p does not have any children	
	 */
	@Override
	public boolean isExternal(Position<E> p) {
		if(numChildren(p) == 0) {																//if number of children is 0
			return true;																		//return true
		}
		return false;																			//else return false
	}

	/**
	 * Returns true if position p is the root of the tree.
	 * 
	 * @param p	the position that is passed
	 * @return 	true if position p is the root of the tree
	 * @throws 	InvalidPositionException if position is invalid 	
	 */
	@Override
	public boolean isRoot(Position<E> p) throws InvalidPositionException {
		TreePosition<E> node = checkPosition(p);												//check if p is a valid position
		if(node == root) {																		//if position p is the root of the tree
			return true;																		//return true
		}
		return false;																			//return false
	}

	/**
	 * Returns true if the tree does not contain any positions (and thus no elements).
	 * 
	 * @return true if the tree does not contain any positions
	 */
	@Override
	public boolean isEmpty() {
		if(size() == 0) {																		//if the size of the tree is equal to 0
			return true;																		//return true
		}
		return false;																			//else return false
	}

	/**
	 * Returns an iterator for all elements in the tree.
	 * 
	 * @return an iterator for all elements in the tree 
	 */
	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();															//returns an iterator for all elements in the tree
	}

	/** Returns an iterable representation of the list's positions. 
	 * 
	 * @return an iterable representation of the list's positions
	 */
	@Override
	public Iterable<E> positions() {
		return (Iterable<E>) preorder();														//return a preorder representation of the list's positions
	}
	
	/**
	 * Checks whether the passed position is a valid tree node
	 * 
	 * @param v the passed position
	 * @return 	the position of tree node v
	 * @throws 	InvalidPositionException if v is an invalid position
	 */
	protected TreePosition<E> checkPosition(Position<E> v) throws InvalidPositionException { 
		if (v == null || !(v instanceof TreePosition)) {										//if v does not exist or v is not a node
			throw new InvalidPositionException("The position is invalid");						//throw exception
		}
		return (TreePosition<E>) v;																//return the position of the tree  node v
	}
	
	/**
	 * Adds a root node to an empty tree
	 * 
	 * @param e	the element in the root node
	 * @return 	the newly added root node
	 * @throws 	NonEmptyTreeException if root node already exists
	 */
	public Position<E> addRoot(E e) throws NonEmptyTreeException {
		if(!isEmpty()) {																		//if the tree is not empty
			throw new NonEmptyTreeException("Tree is not empty");								//throw exception
		}
		root = new TreeNode(e, null, null);														//create a root for the tree
		size = 1;																				//size of the tree becomes 1
		return root;																			//return the root of the tree
	}
	
	/**
	 * Creates a new tree node in a non-empty tree
	 * It should put the new Tree Node as the next (i.e. last) child in the children of its parent 
	 * 
	 * @param element	the element in the new tree node
	 * @param parent	the parent node of the new tree node
	 * @param children	the children of the new tree node
	 * @return			the new tree node
	 */
	protected TreePosition<E> createNode(E element, TreePosition<E> parent, PositionalList<Position<E>> children){
		return new TreeNode<E>(element,parent,children);										//return the new tree node
	}
	
	/**
	 * Swaps the positions of node v and w in the tree
	 * 
	 * @param v the position of v that is passed
	 * @param w the position of w that is passed
	 * @throws  InvalidPositionException if position is invalid
	 */
	public void swapElements(Position<E> v, Position<E> w) throws InvalidPositionException {
		TreePosition<E> node1 = checkPosition(v);												//cast v as a tree node
		TreePosition<E> node2 = checkPosition(w);												//cast w as a tree node
		TreePosition<E> tmp = node1;															//temporary variable that holds node1
		node1 = node2;																			//set node1 equal to node2
		node2 = tmp;																			//set node2 equal to the value in tmp
	}
	
	/**
	 * Returns an iterable collection of positions of the tree, reported in preorder.
	 * 
	 * @return an iterable collection of positions of the tree in a preorder traversal
	 */
	public Iterable<Position<E>> preorder() {
		PositionalList<Position<E>> list = new NodePositionalList<Position<E>>();				//create a Node Positional List
		if(!isEmpty()) {																		//if the tree is empty
			preorderSubtree(root(), list);														//call the preorderSubtree class
		}
		return (Iterable<Position<E>>) list;													//else return an iterable collection of positions of the tree
	}
	
	/** 
	 * Adds positions of the subtree rooted at Position p to the given snapshot. 
	 * 
	 */
	private void preorderSubtree(Position<E> p, PositionalList<Position<E>> list) {											
		TreePosition<E> node = (TreePosition<E>) p;												//cast p as a tree position
		list.addLast(node);																		//add node to the end of the positional list
		while(node.getChildren().iterator().hasNext()) {										//while another child exists
			Position<E> position = node.getChildren().iterator().next();						//position is set to the next child
			preorderSubtree(position, list);													//recursively call the preorderSubtree method with the next child as a parameter and fill the positional list
		}
	}
	
	/**
	 * Prints the elements of the linked tree in a preorder traversal
	 * with a parenthic representation
	 * 
	 * @param node the position that is passed
	 * @return the parenthic representation of the linked tree
	 * @throws InvalidPositionException if position is invalid
	 */
	private String stringHelper(Position<E> node) throws InvalidPositionException {
		String tree = null;																		//initialize String rep to null
		try {																					//try
			Position<E> n = checkPosition(node);												//check that node is a valid position
			if (n.getElement() == null) {														//if the element in the position does not exist
				tree = "";																		//rep is empty
				System.out.println(tree);														//print out rep
			}
			else {
				tree = n.getElement().toString();												//else set rep as the element in the position
				System.out.println(tree);														//print out rep
			}
			Iterator children = (Iterator) children(node);										//children is an iterator that holds the children contained in the position 'node'
			tree += "(";																		//add an open bracket to the String rep 
			
			while (children.hasNext()) {														//while another child exists
				stringHelper((Position<E>) children.next());									//recursively call the preorderPrint method with the position of the next child as a parameter
			}
			tree += ")";																		//add a close bracket to the String rep
			return tree;																		//return the String
		}
		catch (InvalidPositionException e){														//catch the exception if a position is invalid
			System.out.println("Invalid Position");												//print out the following statement
		}
		return tree;																			//return the String rep
	 }

	 /**
	  * Returns a string representation of this tree.
	  *
	  * @returns A string representation of this tree.
	  */
	  public String toString() {
		  try {																					//try
			  return stringHelper(root);														//return the String from the preorderPrint method
		  } 
		  catch (InvalidPositionException e) {													//catch the exception if a position is invalid
			  System.out.println("Invalid Position");											//print out the following statement
			} 
		  return null;																			//return null
	  }
}
