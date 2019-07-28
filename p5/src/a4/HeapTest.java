package a4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Collections;

import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;


class HeapTest {

	public class IntegerComparator implements Comparator<Integer> {

	    public int compare(Integer a, Integer b) {
	        return a.intValue() - b.intValue();
	    }
	}
		
	@Test
	void testEmptyConstructor() {
		Heap<Integer, Integer> constructorTest = new Heap<Integer, Integer>(new IntegerComparator());
		assertEquals(0, constructorTest.size());
		assertEquals(Collections.EMPTY_LIST, constructorTest.getHeapArr());
		assertEquals(Collections.EMPTY_MAP, constructorTest.getHeapMap());
		
		assertEquals(true, constructorTest.assertInvariants());
	}
	
	@Test
	void testComparator() {
		IntegerComparator ic = new IntegerComparator();
		Heap<Integer, Integer> comparatorTest = new Heap<Integer, Integer>(ic);
		assertEquals(ic, comparatorTest.comparator());
	}
	
	@Test
	void testSize() {
		Heap<Integer, Integer> sizeTest = new Heap<Integer, Integer>(new IntegerComparator());
		assertEquals(0, sizeTest.size());
		sizeTest.add(1, 1);
		sizeTest.add(2, 2);
		sizeTest.add(3, 3);
		sizeTest.add(4, 4);
		sizeTest.add(5, 5);
		assertEquals(5, sizeTest.size());
	}
	
	@Test
	void testPoll() {
		Heap<Integer, Integer> pollTest = new Heap<Integer, Integer>(new IntegerComparator());
		pollTest.add(1, 1);
		pollTest.add(2, 2);
		pollTest.add(3, 3);
		Integer poll1 = pollTest.poll();
		assertEquals(3, poll1);
		assertEquals(2, pollTest.size());
		Integer poll2 = pollTest.poll();
		assertEquals(2, poll2);
		assertEquals(1, pollTest.size());
		
		assertEquals(true, pollTest.assertInvariants());

	}
	
	@Test
	void testPeek() {
		Heap<Integer, Integer> peekTest = new Heap<Integer, Integer>(new IntegerComparator());
		peekTest.add(1, 1);
		peekTest.add(3, 3);
		peekTest.add(2, 2);
		assertEquals(3, peekTest.peek());
	}
	
	@Test
	void testAdd() {
		Heap<Integer, Integer> addTest = new Heap<Integer, Integer>(new IntegerComparator());
		addTest.add(1, 1);
		assertEquals(1, addTest.size());
		
		assertEquals(true, addTest.assertInvariants());
	}
	
	@Test
	void testChangePriority() {
		Heap<Integer, Integer> priorityChangeTest = new Heap<Integer, Integer>(new IntegerComparator());
		priorityChangeTest.add(1, 1);
		priorityChangeTest.changePriority(1, 2);
		assertEquals(1, priorityChangeTest.size());
		assertEquals(2, priorityChangeTest.getHeapArr().get(0).getPriority());
		
		Heap<Integer, Integer> treap = createTreeHeap();
		/*
		 * (element, priority, index in arr)
		 *          (5, 5, 0)
		 *         /         \
		 *      (4,4, 1)       (3, 3, 2)
		 *     /        \    
		 *  (2, 2, 3)  (1, 1, 4)
		 */
		treap.changePriority(1, 6);
		/*
		 * (element, priority, index in arr)
		 *          (1, 6, 0)
		 *         /         \
		 *      (5, 5, 1)       (3, 3, 2)
		 *     /        \    
		 *  (2, 2, 3)  (4, 4, 4)
		 */
		assertEquals("[1, 5, 3, 2, 4]", treap.levelOrder());
		assertEquals(true, treap.assertInvariants());
	}
	
	@Test
	void testGetMin() {
		Heap<Integer, Integer> minTest = new Heap<Integer, Integer>(new IntegerComparator());
		minTest.add(11, 1);
		minTest.add(22, 2);
		assertEquals(11, minTest.getMin());
		
	}
	
	@Test
	void testGetMinAndRemove() {
		Heap<Integer, Integer> minRemoveTest = new Heap<Integer, Integer>(new IntegerComparator());
		minRemoveTest.add(11, 1);
		minRemoveTest.add(22, 2);
		assertEquals(11, minRemoveTest.getAndRemoveMin());
		assertEquals(1, minRemoveTest.size());
		
		assertEquals(true, minRemoveTest.assertInvariants());

	}
	
	@Test
	void testRemoveElementAndPriority() {
		Heap<Integer, Integer> removeTest = new Heap<Integer, Integer>(new IntegerComparator());
		removeTest.add(5, 5);
		removeTest.add(4, 4);
		removeTest.add(3, 3);
		removeTest.add(2, 2);
		removeTest.add(1, 1);
		/*
		 * (element, priority, index in arr)
		 *          (5, 5, 0)
		 *         /         \
		 *      (4,4, 1)       (3, 3, 2)
		 *     /        \    
		 *  (2, 2, 3)  (1, 1, 4)
		 */
		removeTest.remove(4, 4);
		
		//still need to do a test where the priority doesn't match the what ths priority should be
		
		
		/*
		 * (element, priority, index in arr)
		 *          (5, 5, 0)
		 *         /         \
		 *      (2,2, 1)       (3, 3, 2)
		 *     /         
		 *  (1, 1, 3) 
		 */
		assertEquals(4, removeTest.size());
		assertEquals("[5, 2, 3, 1]", removeTest.levelOrder());
		
		assertEquals(true, removeTest.assertInvariants());

	}
	
	@Test
	void testRemoveElement() {
		Heap<Integer, Integer> removeTest = new Heap<Integer, Integer>(new IntegerComparator());
		removeTest.add(5, 5);
		removeTest.add(4, 4);
		removeTest.add(3, 3);
		removeTest.add(2, 2);
		removeTest.add(1, 1);
		/*
		 * (element, priority, index in arr)
		 *          (5, 5, 0)
		 *         /         \
		 *      (4,4, 1)       (3, 3, 2)
		 *     /        \    
		 *  (2, 2, 3)  (1, 1, 4)
		 */
		removeTest.remove(4);
				
		
		/*
		 * (element, priority, index in arr)
		 *          (5, 5, 0)
		 *         /         \
		 *      (2,2, 1)       (3, 3, 2)
		 *     /         
		 *  (1, 1, 3) 
		 */
		assertEquals(4, removeTest.size());
		assertEquals("[5, 2, 3, 1]", removeTest.levelOrder());
		
		assertEquals(true, removeTest.assertInvariants());

	}
	
	private Heap<Integer, Integer> createTreeHeap() {
		Heap<Integer, Integer> treap = new Heap<Integer, Integer>(new IntegerComparator());
		//I know, now exactly a real treap, but just a name, :)
		/*
		 * (element, priority, index in arr)
		 *          (5, 5, 0)
		 *         /         \
		 *      (4,4, 1)       (3, 3, 2)
		 *     /        \    
		 *  (2, 2, 3)  (1, 1, 4)
		 */
		treap.add(5, 5);
		treap.add(4, 4);
		treap.add(3, 3);
		treap.add(2, 2);
		treap.add(1, 1);
		
		return treap;
	}
	
	@Test
	void testGetRight() {
		IntegerComparator c = new IntegerComparator();
		Heap<Integer, Integer> treap = createTreeHeap();
		assertEquals(treap.getHeapArr().get(2), treap.getRight(treap.getHeapArr().get(0)));
		assertEquals(treap.getHeapArr().get(4), treap.getRight(treap.getHeapArr().get(1)));
		assertEquals(null, treap.getRight(treap.getHeapArr().get(3)));
		assertEquals(null, treap.getRight(treap.getHeapArr().get(4)));
	}
	
	@Test
	void testGetLeft() {
		IntegerComparator c = new IntegerComparator();
		Heap<Integer, Integer> treap = createTreeHeap();
		assertEquals(treap.getHeapArr().get(1), treap.getLeft(treap.getHeapArr().get(0)));
		assertEquals(treap.getHeapArr().get(3), treap.getLeft(treap.getHeapArr().get(1)));
		assertEquals(null, treap.getLeft(treap.getHeapArr().get(3)));
		assertEquals(null, treap.getLeft(treap.getHeapArr().get(4)));
	}
	
	@Test
	void testGetParent() {
		IntegerComparator c = new IntegerComparator();
		Heap<Integer, Integer> treap = createTreeHeap();
		assertEquals(treap.getHeapArr().get(0), treap.getParent(treap.getHeapArr().get(1)));
		assertEquals(treap.getHeapArr().get(0), treap.getParent(treap.getHeapArr().get(2)));
		assertEquals(treap.getHeapArr().get(1), treap.getParent(treap.getHeapArr().get(3)));
		assertEquals(treap.getHeapArr().get(1), treap.getParent(treap.getHeapArr().get(4)));
	}

	/*
	 * (element, priority, index in arr)
	 *          (5, 5, 0)
	 *         /         \
	 *      (4,4, 1)       (3, 3, 2)
	 *     /        \    
	 *  (2, 2, 3)  (1, 1, 4)
	 */
	
	@Test
	void tesPreorder() {
		Heap<Integer, Integer> treap = createTreeHeap();
		//root, left, right
		assertEquals("[5, 4, 2, 1, 3]", treap.preOrder());
	}
	
	@Test
	void testInOrder() {
		Heap<Integer, Integer> treap = createTreeHeap();
		//left, root, right
		assertEquals("[2, 4, 1, 5, 3]", treap.inOrder());
	}
	
	@Test
	void testPostOrder() {
		Heap<Integer, Integer> treap = createTreeHeap();
		//left, right, root
		assertEquals("[2, 1, 4, 3, 5]", treap.postOrder());
	}
	
	@Test
	void testLevelOrder() {
		Heap<Integer, Integer> treap = createTreeHeap();
		assertEquals("[5, 4, 3, 2, 1]", treap.levelOrder());
	}
	
	@Test
	void testGetPriority() {
		IntegerComparator c = new IntegerComparator();
		Heap<Integer, Integer> priorityHeap = new Heap<Integer, Integer>(c);
		priorityHeap.add(46, 2);
		assertEquals(2, priorityHeap.getPriority(46));
	}
	
	@Test
	void testExceptions() {
		IntegerComparator c = new IntegerComparator();
		Heap<Integer, Integer> exceptionsBlank = new Heap<Integer, Integer>(c);
		Heap<Integer, Integer> noElement = createTreeHeap();
		/*
		 * (element, priority, index in arr)
		 *          (5, 5, 0)
		 *         /         \
		 *      (4,4, 1)       (3, 3, 2)
		 *     /        \    
		 *  (2, 2, 3)  (1, 1, 4)
		 */
		//poll - NoSuchElementException
		assertThrows(NoSuchElementException.class, () -> { exceptionsBlank.poll(); });
		
		//peek - NoSuchElementException
		assertThrows(NoSuchElementException.class, () -> { exceptionsBlank.peek(); });

		//add - IllegalArgumentException
		assertThrows(IllegalArgumentException.class, () -> { noElement.add(4, 4); });
		assertThrows(IllegalArgumentException.class, () -> { noElement.add(3, 9); });
		
		//changePriority - NoSuchElementException
		assertThrows(NoSuchElementException.class, () -> { noElement.changePriority(10, 100); });
		
		//getMin - NoSuchElementException
		assertThrows(NoSuchElementException.class, () -> { exceptionsBlank.getMin(); });
		
		//getAndRemoveMin - NoSuchElementException
		assertThrows(NoSuchElementException.class, () -> { exceptionsBlank.getAndRemoveMin(); });
		
		//remove (both)- NoSuchElementException
		assertThrows(NoSuchElementException.class, () -> { noElement.remove(9, 4); });
		assertThrows(NoSuchElementException.class, () -> { noElement.remove(4, 7); });
		assertThrows(NoSuchElementException.class, () -> { noElement.remove(10); });  
		
		//pre/in/post/level - NoSuchElementException
		assertThrows(NoSuchElementException.class, () -> { exceptionsBlank.preOrder(); });
		assertThrows(NoSuchElementException.class, () -> { exceptionsBlank.inOrder(); });
		assertThrows(NoSuchElementException.class, () -> { exceptionsBlank.postOrder(); });
		assertThrows(NoSuchElementException.class, () -> { exceptionsBlank.levelOrder(); });
	}
}
