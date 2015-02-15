package alda.heap;

//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/*
* @author Hannes Hornvall haho2206 hornwall.hannes@gmail.com
* @author Lotta Månsson loma3374 Mansson.lotta@gmail.com
* @since  2015-02-15
*/

/**
 * Implements a binary heap. Note that all "matching" is based on the compareTo
 * method.
 * 
 * @author Mark Allen Weiss
 */

@SuppressWarnings("unchecked")
public class DHeap<AnyType extends Comparable<? super AnyType>> {

	int d = 2;

	/**
	 * Construct the binary heap.
	 */
	public DHeap() {
		currentSize = 0;
		array = (AnyType[]) new Comparable[DEFAULT_CAPACITY + 1];
	}

	/**
	 * Construct the binary heap.
	 * 
	 * @param capacity
	 *            the capacity of the binary heap.
	 */
	public DHeap(int d) {
		this();
		if (d < 2)
			throw new IllegalArgumentException("must be 2 or greater");
		this.d = d;
	}
	
	public DHeap(int d, int capacity )
    {
		this(d);
        array = (AnyType[]) new Comparable[ capacity + 1 ];
    }

	/**
	 * Construct the binary heap given an array of items.
	 */
	public DHeap(AnyType[] items) {
		currentSize = items.length;
		array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];

		int i = 1;
		for (AnyType item : items)
			array[i++] = item;
		buildHeap();
	}

	/**
	 * Insert into the priority queue, maintaining heap order. Duplicates are
	 * allowed.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(AnyType x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1);

		// Percolate up
		int hole = ++currentSize;
		if (hole > 1) {
			int parentIndex = parentIndex(hole);
			for (;hole != 1 && x.compareTo(array[parentIndex]) < 0; parentIndex = parentIndex(hole)) {
				array[hole] = array[parentIndex(hole)];
				hole = parentIndex;
				if (hole == 1)
					break;
			}
		}
		array[hole] = x;  
	}

	private void enlargeArray(int newSize) {
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for (int i = 0; i < old.length; i++)
			array[i] = old[i];
	}

	/**
	 * Find the smallest item in the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return array[1];
	}

	/**
	 * Remove the smallest item from the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType deleteMin() {
		if (isEmpty())
			throw new UnderflowException();
		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);

		return minItem;
	}

	/**
	 * Establish heap order property from an arbitrary arrangement of items.
	 * Runs in linear time.
	 */
	private void buildHeap() {
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
	}

	/**
	 * Test if the priority queue is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * Make the priority queue logically empty.
	 */
	public void makeEmpty() {
		currentSize = 0;
	}

	private static final int DEFAULT_CAPACITY = 10;

	private int currentSize; // Number of elements in heap
	private AnyType[] array; // The heap array

	/**
	 * Internal method to percolate down in the heap.
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	private void percolateDown(int hole) {
		int child;
		int lowestChild;
		AnyType tmp = array[hole];

		for (; firstChildIndex(hole) <= currentSize; hole = lowestChild) {
			child = firstChildIndex(hole);
			lowestChild = child;
			for (int i = 1; i < d; i++) {
				if ((child + i) <= currentSize
						&& array[child + i].compareTo(array[lowestChild]) < 0) {
					lowestChild = child + i;
				}
			}
			if (array[lowestChild].compareTo(tmp) <= 0) {
				array[hole] = array[lowestChild];
			} else {
				break;
			}
		}
		array[hole] = tmp;
	}

	// Test program
	public static void main(String[] args) {
		int numItems = 10000;
		DHeap<Integer> h = new DHeap<>();
		int i = 37;

		for (i = 37; i != 0; i = (i + 37) % numItems)
			h.insert(i);
		for (i = 1; i < numItems; i++)
			if (h.deleteMin() != i)
				System.out.println("Oops! " + i);
	}

	public int parentIndex(int i) {
		if (i < 2)
			throw new IllegalArgumentException();
		if (d == 2)
			return i / d;
		return Math.round(i * 1f / d);
	}

	public int firstChildIndex(int i) {
		if (i < 1) {
			throw new IllegalArgumentException();
		}
		return ((d * (i - 1)) + 2);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stud
		String s = "count: " + currentSize + " [";
		for (int i = 0; i < currentSize + 1; i++) {
			if (array[i] == null) {
				s += "null" + ", ";
			} else {
				s += array[i].toString() + ", ";
			}
		}
		s += "]";
		return s;
	}

	public int size() {
		return currentSize;
	}

	public Object get(int i) {
		return array[i];
	}
}