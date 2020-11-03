package packag;

import java.util.Iterator;

public class NodePositionalList<E> implements PositionalList<E>, Iterable<E> {

	public class DNode<E> implements Position<E>{

		private DNode<E> next;															//variable that points to the next Node		
		private DNode<E> prev;															//variable that points to the previous Node
		private E data;																	//variable that stores the data that will be put into a Node
		
		/**
		 * This constructs a doubly  linked node
		 * with link to its previous and next node
		 * along with the element inside the node.
		 * @param p the link to the previous node
		 * @param d element in the node
		 * @param n the link to the next node
		 */
		public DNode(DNode<E> p, E d, DNode<E> n) {
			this.prev = p;																//sets prev to the value of p
			this.data = d;																//sets data to the value of d
			this.next = n;																//sets next to the value of n
		}
		
		/**
		 * Returns the element stored at this position.
		 *
		 * @return the stored element
		 * @throws IllegalStateException if position no longer valid
		 */
		@Override
		public E getElement() throws IllegalArgumentException{
			if(next == null) {															//if no next node
				throw new IllegalArgumentException("Position no longer valid"); 		//throw exception
			}
			return this.data; 															//return the stored element
		}
	}
	
	//-----------------------nested PositionIterator class-----------------------
	
	/**This class maintains the position of the next element to be returned*/
	private class PositionIterator implements Iterator<Position<E>> {

		private Position<E> pointer = first();											//position of the next element to report
		private Position<E> last = null;												//position of last reported element
		
		/** Tests whether the iterator has a next object. 
		 * 
		 * @return true if the iterator has a next object, false
		 *         otherwise
		 */
		@Override
		public boolean hasNext() {
			if(pointer != null) {														//if the current node is not null
				return true;															//return true
			}
			else {																		//if the current node is null
				return false;															//return false
			}
		}

		/** Returns the next position in the iterator. 
		 * 
		 * @return the next position in the iterator
		 */
		@Override
		public Position<E> next() {
			if(pointer == null) {														//if the next element in the iterator is null
				try {
					throw new NoSuchElementException("No next position"); 				//throw exception
				} catch (NoSuchElementException e) {
					e.printStackTrace();
				}	
			}
			last = pointer;																//element at this position might later be removed
			try {
				pointer = after(pointer);												//pointer now becomes the element that is right after it
			} catch (InvalidPositionException e) {										//throw exception if position is not valid
				e.printStackTrace();
			}																		
			return last;																//return the last reported element
		}
		
		/** Removes the element returned by most recent call to next. 
		 * 
		 * @throws UnsupportedOperationException
		 */
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();									//throw exception
		}
	}
	//-----------------------end of nested PositionIterator class--------------------
	
	//-----------------------nested PositionIterable class-----------------------
	
	/**This class constructs and returns a new PositionIterator object each time
	 * its iterator() method is called.
	 */
	private class PositionIterable implements Iterable<Position<E>> {

		/** Returns an iterator for the elements in the collection. 
		 * 
		 * @return an iterator for the elements in the linked list
		 */
		@Override
		public Iterator<Position<E>> iterator() {
			return new PositionIterator();												//return a new PositionIterator object
		}

		/** Returns an iterable representation of the list's positions. 
		 * This class does not need the positions() method, therefore
		 * it will return null. 
		 */
		@Override
		public Iterable<Position<E>> positions() {
			return null;																//returns null
		}
	
	}
	//-----------------------end of nested PositionIterable class--------------------
	
	//----------------------------nested ElementIterator class-----------------------
	
	/** This class adapts the iteration produced by positions() to return elements. */
	private class ElementIterator implements Iterator<E> {

		Iterator<Position<E>> posIterator = new PositionIterator();						//creates an iterator
		
		/** Tests whether the iterator has a next object. 
		 * 
		 * @return true if iterator has a next object, false otherwise
		 */
		@Override
		public boolean hasNext() {
			return posIterator.hasNext();												//returns true if there is a next object, false otherwise
		}

		/** Returns the next position in the iterator. 
		 * 
		 * @return the element in the next position in the iterator 
		 */
		@Override
		public E next() {
			return posIterator.next().getElement();										//return element
		}
		
		/** Removes the element returned by most recent call to next. 
		 * 
		 * @throws UnsupportedOperationException
		 */
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();									//throw exception
		}
		
	}
	//----------------------------end of nested ElementIterator class--------------------
	
	private DNode<E> header;															//variable for the head node
	private DNode<E> trailer;															//variable for the tail node
	private int size = 0; 																//variable that keeps track of the size of the linked list
	
	/** Constructor that initializes the header and trailer*/
	public NodePositionalList() {
		header = new DNode<>(null, null, null);											//prev, data, and next are null
		trailer = new DNode<>(header, null, null);										//previous node is header
		header.next = trailer;															//the node after the header is the trailer
	}
	
	/**
	 * Returns the given node as a Position (or null, if it is a sentinel).
	 * 
	 * @param node the node in the linked list
	 * @return     the node
	 */
	private Position<E> position(DNode<E> node){
		if(node == header || node == trailer) {											//if node is the header or trailer
			return null;																//return null
		}
		return (Position<E>) node;														//return node
	}
	
	/** Returns an iterator for the elements in the collection. 
	 * 
	 * @return an iterator of the elements in the linked list
	 */
	@Override
	public Iterator<E> iterator() {
		return (Iterator<E>) new ElementIterator();										//returns an iterator of the elements in the linked list
	}

	/** Returns an iterable representation of the list's positions. 
	 * 
	 * @return an iterable representation of the list's positions
	 */
	@Override
	public Iterable<E> positions() {
		return (Iterable<E>) new PositionIterable(); 									//returns an iterable of the list's positions
	}

	/** Returns the number of elements in the list. 
	 * 
	 * @returns the number of elements in the linked list 
	 */
	@Override
	public int size() {
		return size;																	//returns the size
	}

	/** Tests whether the list is empty. 
	 * 
	 * @return true if the linked list is empty, false
	 *         otherwise
	 */
	@Override
	public boolean isEmpty() {
		if(size() == 0) {																//if the size of the linked list is 0
			return true;																//return true
		}
		return false;																	//else return false
	}

	/** Returns the first Position in the list (or null, if empty). 
	 * 
	 * @return the first position in the linked list, or null otherwise
	 */
	@Override
	public Position<E> first() {
		if(isEmpty()) {																	//if the linked list is empty
			return null;																//return null
		}
		else {																			//if not empty
			return position(header.next);												//return the first position
		}
	}

	/** Returns the last Position in the list (or null, if empty). 
	 * 
	 * @return the last position in the linked list, or null otherwise
	 */
	@Override
	public Position<E> last() {
		if(isEmpty()) { 																//if the linked list is empty
			return null;																//return null
		}
		else {																			//if not empty
			return position(trailer.prev);												//return the last position
		}
	}
	
	/**
	 * Adds element e to the linked list between the given nodes. 
	 * 
	 * @param e		element to be added
	 * @param pred	the previous node
	 * @param succ	the next node
	 * @return		the node that has been added between the pred and succ nodes
	 */
	private Position<E> addBetween(E e, DNode<E> pred, DNode<E> succ) {
		DNode<E> newNode = new DNode<>(pred, e, succ);									//create and link a new node
		pred.next = newNode;															//the next link of the previous node points to the new node
		succ.prev = newNode;															//the previous link of the next node points to the new node
		size++;																			//the size of the linked list is increased by 1
		return newNode;																	//return the new node
	}
	
	/**
	 * Checks whether the passed position is a valid node
	 * 
	 * @param v the position that is checked
	 * @return  the node of the passed position
	 * @throws InvalidPositionException if v does not exist or v is not a node
	 */
	protected Position<E> checkPosition(Position<E> v) throws InvalidPositionException{
		if(v == null || !(v instanceof DNode)) {										//if v does not exist or v is not a node
			throw new InvalidPositionException("The position is invalid");				//throw exception
		}
		return (Position<E>) v;
	}

	/**
	 * Returns the Position immediately before Position p (or null, if p is first).
	 * 
	 * @param p the position of the node
	 * @return  the position immediately before position p, or null if p is in the first
	 *          position
	 * @throws InvalidPositionException if position is invalid
	 */
	@Override
	public Position<E> before(Position<E> p) throws InvalidPositionException {
		if(p == header) {																//if p is header node
			return null;																//return null
		}
		else {
			DNode<E> node = (NodePositionalList<E>.DNode<E>) checkPosition(p);			//cast p as a node
			return position(node.prev);													//return the position of p
		}
		
	}

	/** Returns the Position immediately after Position p (or null, if p is last). 
	 * 
	 * @param p the position of the node
	 * @return the position immediately after position p, or null if p is in the last
	 *         position
	 * @throws InvalidPositionException  if position is invalid
	 * */
	@Override
	public Position<E> after(Position<E> p) throws InvalidPositionException {
		if(p == trailer) {																//if p is trailer node
			return null;																//return null
		}
		else {
			DNode<E> node = (NodePositionalList<E>.DNode<E>) checkPosition(p);			//cast p as a node
			return position(node.next);													//return the position of p
		}
	}

	/**
	 * Inserts element e at the front of the list and returns its new Position. 
	 * 
	 * @param e	the element to be inserted
	 * @return  the new position of the inserted element
	 */
	@Override
	public Position<E> addFirst(E e) {
		return addBetween(e, header.prev, header);										//return the added element just after the header node									
	}

	/**
	 * Inserts element e at the back of the list and returns its new Position.
	 * 
	 * @param e the element to be inserted
	 * @return  the new position of the inserted element
	 */
	@Override
	public Position<E> addLast(E e) {
		return addBetween(e, trailer, trailer.next);									//return the added element just before the trailer node
	}

	/**
	 * Inserts element e immediately before Position p and returns its new Position.
	 * 
	 * @param p  the position of the node
	 * @param e  the element to be inserted
	 * @return	 the new position of the inserted element
	 * @throws   InvalidPositionException if position is invalid
	 */
	@Override
	public Position<E> addBefore(Position<E> p, E e) throws InvalidPositionException {							
		DNode<E> node = (NodePositionalList<E>.DNode<E>) checkPosition(p);				//cast p as a node
		return addBetween(e, node.prev, node);											//return the position of p
	}

	/**
	 * Inserts element e immediately after Position p and returns its new Position.
	 * 
	 * @param p  the position of the node
	 * @param e  the element to be inserted
	 * @return   the new position of the inserted element
	 * @throws   InvalidPositionException if position is invalid
	 */
	@Override
	public Position<E> addAfter(Position<E> p, E e) throws InvalidPositionException {							
		DNode<E> node = (NodePositionalList<E>.DNode<E>) checkPosition(p);				//cast p as a node
		return addBetween(e, node, node.next);											//return the position of p
	}

	/**
	 * Replaces the element stored at Position p and returns the replaced element.
	 * 
	 * @param p  the position of the node
	 * @param e	 the element that will replaces the element stored at position p
	 * @return   the replaced element
	 * @throws   InvalidPositionException if position is invalid 
	 */
	@Override
	public E set(Position<E> p, E e) throws InvalidPositionException {
		DNode<E> node = (NodePositionalList<E>.DNode<E>) checkPosition(p);				//cast p as a node
		E replaced = node.data;															//variable to store the replaced element
		node.data = e;																	//replace the data in the position with element 'e'
		return replaced;																//return the replaced element
	}

	/**
	 * Removes the element stored at Position p and returns it (invalidating p).
	 * 
	 * @param p  the position of the node
	 * @return   the removed element
	 * @throws   InvalidPositionException if position is invalid 
	 */
	@Override
	public E remove(Position<E> p) throws InvalidPositionException {							
		DNode<E> node = (NodePositionalList<E>.DNode<E>) checkPosition(p);				//cast p as a node
		E removed = node.data;															//variable that holds the element to be removed
		DNode<E> before = node.prev;													//before is equal to the previous link of node 'n'
		DNode<E> after = node.next;														//after is equal to the next link of node 'n'
		before.next = after;															//the next link of the before node points to the after node
		after.prev = before;															//the previous link of the after node points to the before node
		size--;																			//decrease the size of the linked list by 1
		return removed;																	//return the removed element
	}
	
	/**
	 * Returns a string representation of the linked list
	 * 
	 * @return a string representation of the linked list
	 */
	public String toString() {
		String list = "";																//initialize an empty string
		
		DNode<E> currentNode = header;													//variable that points to the header node
		while(currentNode != null) {													//while a node exist
			list = list + currentNode.data + " ";										//add the element in current node with a space right after
			currentNode = currentNode.next;												//move the pointer to the next node
		}
		return list;																	//return the string
	}

}
