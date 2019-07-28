package a4;

import java.util.Comparator;

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.HashMap;

public class Heap<E, P> implements PriorityQueue<E, P> {

	private ArrayList<Element> heapArr;          		 //stores elements
	private HashMap<E, Integer> heapMap;                 //key is element. value is index
	private Comparator<P> comp;
	
	//public for testing reasons
	public class Element {
		
		private P priority;
		private E data;
		
		/**
		 * Constructs an Element object
		 */
		public Element(E element, P priority) {
			this.data = element;
			this.priority = priority;
		}
		
		/**
		 * Returns the priority of this element
		 * @return The priority of this element
		 */
		public P getPriority() {
			return this.priority;
		}
		
		/**
		 * Returns the data of this element 
		 * @return The data of this element
		 */
		public E getData() {
			return this.data;
		}
		
		/**
		 * Sets the new priority of this element
		 * @param newPriority The new priority
		 */
		public void setPriority(P newPriority) {
			this.priority = newPriority;
		}
		
		/**
		 * Sets the new data of this element
		 * @param newData The new data
		 */
		public void setData(E newData) {
			this.data = newData;
		}
		
		/**
		 * Return the string representation of this element
		 * @return The string representation of this element
		 */
		@Override
		public String toString() {
			return "Data: " + this.data + ", Priority: " + this.priority;
		}
	}	
	
	/**
	 * Constructs an empty heap, with comparator, c
	 * @param c The comparator to be used in this heap
	 */
	public Heap(Comparator<P> c) {
		this.heapArr = new ArrayList<Element>();
		this.heapMap = new HashMap<E, Integer>();
		this.comp = c;
	}
	
	/** return the comparator used for ordering priorities */
	@Override
	public Comparator<? super P> comparator() {
		return this.comp;
	}

	/** Return the number of elements in this.  Runs in O(1) time. */
	@Override
	public int size() {
		return this.heapArr.size();
	}

	/**
	 * Remove and return the largest element of this, according to comparator()
	 * Runs in O(log n) time.
	 * 
	 * @throws NoSuchElementException if this is empty 
	 */
	@Override
	public E poll() throws NoSuchElementException {
		if(this.heapArr.size() == 0)
			throw new NoSuchElementException("This heap is empty.");
		
		Element largest = this.heapArr.get(0);
		
		swap(largest, this.heapArr.get(size() - 1));
		this.heapArr.remove(size() - 1);
		this.heapMap.remove(largest.getData());
		heapifyTopToBottom(this.heapArr.get(0));
		
		return largest.getData();
	}

	/**
	 * Does the necessary swaps to make list into heap (recursively). 
	 * This helper works from the root to the leaves, stopping when necessary
	 * @param current The node to be compared to the children
	 */
	private void heapifyTopToBottom(Element current) {
		if(getRight(current) == null && getLeft(current) == null)
			return; //current is a leaf
		else if(getLeft(current) != null && getRight(current) == null) {
			//only has left child
			Element leftChild = getLeft(current);
			if(this.comp.compare(leftChild.getPriority(), current.getPriority()) > 0) {
				//left child is greater than parent, has greater priority, swap the two
				swap(leftChild, current);
			}
		} else {
			//left and right child exist
			Element r = getRight(current);
			Element l = getLeft(current);
			
			Element max;
			if(this.comp.compare(r.getPriority(), l.getPriority()) > 0)
				max = r;
			else if(this.comp.compare(r.getPriority(), l.getPriority()) < 0)
				max = l;
			else
				max = r;
			
			swap(max, current);
			heapifyTopToBottom(max);
		}
	}
	
	/**
	 * Swaps two elements
	 * @param e1 The element to be swapped with the other element
	 * @param e2 The element to be swapped with the other element
	 */
	private void swap(Element e1, Element e2) {
		int indexE1 = this.heapArr.indexOf(e1);
		int indexE2 = this.heapArr.indexOf(e2);
		
		this.heapArr.set(indexE1, e2);
		this.heapArr.set(indexE2, e1);
		
		this.heapMap.put(e1.getData(), indexE2);
		this.heapMap.put(e2.getData(), indexE1);
	}
	
	
	/**
	 * Return the largest element of this, according to comparator().
	 * Runs in O(1) time.
	 * 
	 * @throws NoSuchElementException if this is empty.
	 */
	@Override
	public E peek() throws NoSuchElementException {
		if(this.heapArr.size() == 0)
			throw new NoSuchElementException("This heap is empty.");
		
		return this.heapArr.get(0).getData();
	}

	/**
	 * Add the element e with priority p to this.  Runs in O(log n + a) time,
	 * where a is the time it takes to append an element to an ArrayList of size
	 * n.
	 * 
	 * @throws IllegalArgumentException if this already contains an element that
	 *                                  is equal to e (according to .equals())
	 */
	@Override
	public void add(E e, P p) throws IllegalArgumentException {
		if(this.heapMap.containsKey(e))
			throw new IllegalArgumentException("e, of type E, already exists.");
		
		Element el = new Element(e, p);
		this.heapArr.add(el);
		heapifyBottomToTop(el);
		this.heapMap.put(el.getData(), this.heapArr.indexOf(el));
	}
	
	/**
	 * Does the necessary swaps to make list into heap (recursively). 
	 * This helper works from the leaves to the root, stopping when necessary
	 * @param current The node to be compared to the parent
	 */
	private void heapifyBottomToTop(Element current) {
		Element parent = getParent(current);
		if(parent == null)     //has no parent
			return;
		else if(this.comp.compare(parent.getPriority(), current.getPriority()) > 0)    //parent is greater than child
			return;
		else {
			//parent is smaller than child
			swap(parent, current);
			heapifyBottomToTop(current);
		}
	}

	/**
	 * Change the priority associated with e to p.
	 *
	 * @throws NoSuchElementException if this does not contain e.
	 */
	@Override
	public void changePriority(E e, P p) throws NoSuchElementException {
		if(!this.heapMap.containsKey(e))
			throw new NoSuchElementException("e, of type E, doesn't exist in this heap.");
		
		Element el = this.heapArr.get(this.heapMap.get(e));
		el.setPriority(p);
		
		swap(el, this.heapArr.get(this.heapArr.size() - 1));
		heapifyBottomToTop(el);
	}
	
	////////////////////////////////////////////////////////////////////////
	/*additional public methods*/
	////////////////////////////////////////////////////////////////////////
	
	/**
	 * Return the smallest element (priority) of this, according to comparator().
	 * 
	 * @throws NoSuchElementException if this is empty.
	 */
	public E getMin() throws NoSuchElementException {
		if(this.heapArr.size() == 0)
			throw new NoSuchElementException("This heap is empty, so a smallest element doesn't exist.");
		
		Element smallestPriority = this.heapArr.get(0);
		
		for(int i = 1; i < this.heapArr.size(); i++) {
			Element current = this.heapArr.get(i);
			
			if(this.comp.compare(current.getPriority(), smallestPriority.getPriority()) < 0)
				smallestPriority = current;
		}
		
		return smallestPriority.getData();
	}
	
	/**
	 * Remove and return the largest element of this, according to comparator()
	 * 
	 * @throws NoSuchElementException if this is empty 
	 */
	public E getAndRemoveMin() throws NoSuchElementException {
		if(this.heapArr.size() == 0)
			throw new NoSuchElementException("This heap is empty, so a smallest element doesn't exist.");
		
		E minimum = getMin();
		remove(minimum);
		return minimum;
	}
	
	/**
	 * Remove and return element E in this, given a priority
	 * 
	 * @throws NoSuchElementException if element E doesn't exist in this
	 */
	public E remove(E e, P p) throws NoSuchElementException{
		if(!this.heapMap.containsKey(e))
			throw new NoSuchElementException("e, of type E, doesn't exist in this heap.");
		
		if(this.heapMap.containsKey(e) && this.heapArr.get(this.heapMap.get(e)).getPriority() != p)
			throw new NoSuchElementException("e, of type E, exists, but not with priority, p, of type P.");
		
		return remove(e);
	}
	
	/**
	 * Remove and return element E in this, not given a priority.
	 * 
	 * @throws NoSuchElementException if element E doesn't exist in this
	 */
	public E remove(E e) throws NoSuchElementException {
		if(!this.heapMap.containsKey(e))
			throw new NoSuchElementException("e, of type E, doesn't exist in this heap.");
		
		Element eEl = this.heapArr.get(this.heapMap.get(e));
		Element rightmostLeaf = this.heapArr.get(this.heapArr.size() - 1);
		swap(eEl, rightmostLeaf);
		
		this.heapArr.remove(this.heapArr.size() - 1);
		this.heapMap.remove(e);
		
		heapifyTopToBottom(rightmostLeaf);
		
		return e;
	}
	

	
	/**
	 * Returns the right child of this
	 * @return The right child
	 * return null if the right child doesn't exist
	 */
	public Element getRight(Element e){
		int index = this.heapArr.indexOf(e);
		
		int indexRight = 2 * index + 2;
		
		if(indexRight >= size() || index < 0)
			return null;
		
		return this.heapArr.get(indexRight);
	}
	
	/**
	 * Returns the left child of this
	 * @return The left child
	 * returns null if left child doesn't exist
	 */
	public Element getLeft(Element e) {		
		int index = this.heapArr.indexOf(e);
		
		int indexLeft = 2 * index + 1;
		
		if(indexLeft >= size() || index < 0)
			return null;
		
		return this.heapArr.get(indexLeft);
	}
	
	/**
	 * Returns the parent of this
	 * @return The parent 
	 * return null if parent doesn't exist
	 */
	public Element getParent(Element e) {
		int index = this.heapArr.indexOf(e);
		
		int indexParent = (index - 1) / 2;
		
		if(indexParent < 0 || index == 0)
			return null;
		
		return this.heapArr.get(indexParent);
	}
	
	/**
	 * Print out this in pre-order (values)
	 * 
	 * @throws NoSuchElementException if this is empty 	 
	 * */
	public String preOrder() throws NoSuchElementException {
		//root, left, right
		if(this.heapArr.size() == 0)
			throw new NoSuchElementException("This heap is empty.");
			
		return "[" + preOrderHelper(this.heapArr.get(0)) + "]";
	}
	
	/** Private helper method to help with returning preOrder() */
	private String preOrderHelper(Element current) {
		
		if(current != null) {
			if(getLeft(current) == null && getRight(current) == null)
				return "" + current.getData();
			else if(getLeft(current) != null && getRight(current) == null) 
				return current.getData() + ", " + preOrderHelper(getLeft(current));
			else
				return current.getData() + ", " + preOrderHelper(getLeft(current)) + ", " + preOrderHelper(getRight(current));
		}
		
		return "";
	}
	
	/**
	 * Print out this in in-order (values)
	 * 
	 * @throws NoSuchElementException if this is empty 	 
	 * */
	public String inOrder() throws NoSuchElementException {
		//left, root, right		
		if(this.heapArr.size() == 0)
			throw new NoSuchElementException("This heap is empty.");
		
		return "[" + inOrderHelper(this.heapArr.get(0)) + "]";
	}
	
	/** Private helper method to help with returning inOrder() */
	private String inOrderHelper(Element current) {
		
		if(current != null) {
			if(getLeft(current) == null && getRight(current) == null)
				return "" + current.getData();
			else if(getLeft(current) != null && getRight(current) == null) 
				return inOrderHelper(getLeft(current)) + ", " + current.getData();
			else {
				return inOrderHelper(getLeft(current)) + ", " + current.getData() + ", " + inOrderHelper(getRight(current));

			}
		}
		return "";
	}
	
	/**
	 * Print out this in post-order (values)
	 * 
	 * @throws NoSuchElementException if this is empty 	 
	 * */
	public String postOrder() throws NoSuchElementException {
		//left, right, root		
		if(this.heapArr.size() == 0)
			throw new NoSuchElementException("This heap is empty.");
		
		return "[" + postOrderHelper(this.heapArr.get(0)) + "]";
	}
	
	/** Private helper method to help with returning inOrder() */
	private String postOrderHelper(Element current) {
		
		if(current != null) {
			if(getLeft(current) == null && getRight(current) == null)
				return "" + current.getData();
			else if(getLeft(current) != null && getRight(current) == null) 
				return postOrderHelper(getLeft(current)) + ", " + current.getData();
			else {
				return postOrderHelper(getLeft(current)) + ", " + postOrderHelper(getRight(current)) + ", " + current.getData();

			}
		}
		return "";
	}
	
	/**
	 * Return this heap as a string in level order representation
	 * @return This heap as a string in level order representation
	 */
	public String levelOrder() throws NoSuchElementException{
		
		if(this.heapArr.size() == 0)
			throw new NoSuchElementException("This heap is empty.");
		
		String lo = "[";
		for(int i = 0; i < this.heapArr.size(); i++) {
			lo += "" + this.heapArr.get(i).getData();
			
			if(i != this.heapArr.size() - 1)
				lo += ", ";
		}
		lo += "]";
		
		return lo;
	}
	
	/**
	 * Returns the heap arraylist
	 * @return The heap arraylist
	 */
	public ArrayList<Element> getHeapArr() {
		return this.heapArr;
	}
	
	/**
	 * Returns the heap hashmap
	 * @return The heap hashmap
	 */
	public HashMap<E, Integer> getHeapMap() {
		return this.heapMap;
	}
	
	/**
	 * Returns the priority of element e
	 * @param e The element in which the priority is returned
	 * @return The priority of element e
	 */
	public P getPriority(E e) {
		int index = this.heapMap.get(e);
		return this.heapArr.get(index).getPriority();
	}
	
	//Note: this method is here, and not in JUnit test, due to parameterizing of generics issue
	/**
	 * Asserts that the invariants are true
	 * 
	 * Invariant 1: all children are smaller than the parent
	 * Invariant 2: Full tree
	 * 
	 * @return a boolean corresponding to whether the invariants are true or not
	 */
	public boolean assertInvariants() {
		//checking full tree
		boolean full = true;
		
		for(int i = 0; i < this.heapArr.size(); i++) {
			Element current = this.heapArr.get(i);
			
			if(getLeft(current) == null && getRight(current) != null) {
				full = false;
				break;
			}
		}
		
		return invariantHelperOne(this.heapArr) && full;
	}
	
	/**
	 * Helper method for checking invariant 1
	 * @param a The arraylist that will be used to check 
	 * @return a boolean corresponding to whether invariant 1 is true
	 */
	private boolean invariantHelperOne(ArrayList<Element> a) {
		for(int i = 0; i < a.size(); i++) {
			Element parent = a.get(0);
			Element left = getLeft(parent);
			Element right = getRight(parent);
			
			boolean leftSmall = true;
			boolean rightSmall = true;
			
			if(left != null) 
				if(this.comp.compare(left.getPriority(), parent.getPriority()) > 0)
					leftSmall = false;
			
			if(right != null) 
				if(this.comp.compare(right.getPriority(), parent.getPriority()) > 0)
					rightSmall = false;
			
			if(!(rightSmall || leftSmall)) {
				return false;
			}
		}
		
		return true;
	}
} 