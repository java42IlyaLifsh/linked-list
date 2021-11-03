package telran.util;
//HW_9_IlyaL
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class LinkedList<T> implements List<T> {
	private int size;

	private static class Node<T> {
		T obj;
		Node<T> next;
		Node<T> prev;

		Node(T obj) {
			this.obj = obj;
		}
	}

	private Node<T> head; // reference to the first element
	private Node<T> tail; // reference to the last element

	@Override
	public void add(T element) {
		Node<T> newNode = new Node<>(element);
		if (head == null) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		size++;

	}
	private Node<T> getNode(int index) {
		Node<T> res = null;
		if(isValidIndex(index)) {
			res = index <= size / 2 ? getNodefromLeft(index) : getNodeFromRight(index);
		}
		return res;
	}

	private Node<T> getNodeFromRight(int index) {
		Node<T> current = tail;
		int ind = size - 1;
		while(ind != index) {
			ind--;
			current = current.prev;
		}
		return current;
	}
	private Node<T> getNodefromLeft(int index) {
		Node<T> current = head;
		int ind = 0;
		while(ind != index) {
			ind++;
			current = current.next;
		}
		return current;
	}
	private boolean isValidIndex(int index) {
		
		return index >=0 && index < size;
	}
	@Override
	public boolean add(int index, T element) {
		boolean res = false;
		if (index == size) {
			add(element);
			res = true;
		} else if (isValidIndex(index)) {
			res = true;
			Node<T> newNode = new Node<>(element);
			if (index == 0) {
				addHead(newNode);
			} else {
				addMiddle(newNode, index);
			}
			size++;
		}
		return res;
	}

	private void addMiddle(Node<T> newNode, int index) {
		Node<T> nodeAfter = getNode(index);
		newNode.next = nodeAfter;
		newNode.prev = nodeAfter.prev;
		//nodeAfter.prev => reference to the old previous element
		nodeAfter.prev.next = newNode;
		nodeAfter.prev = newNode;
		
	}
	private void addHead(Node<T> newNode) {
		newNode.next = head;
		head.prev = newNode;
		head = newNode;
		
	}
	@Override
	public int size() {

		return size;
	}

	@Override
	public T get(int index) {
		T res = null;
		Node<T> resNode = getNode(index);
		if (resNode != null) {
			res = resNode.obj;
		}
		return res;
	}

	@Override
	public T remove(int index) {
		
			T result = null;
			if (!isValidIndex(index)) 	return result;
			if(index==0) {
					result = removHead();
				} else if(index==(size-1)) {
					result = removTail();
				} else {
					result = removMidle(index);
				}
				size--;
				return result; 
		}

	private T removMidle(int index) {
		Node<T> nodeToDel = getNode(index);
		Node<T> nextNode = nodeToDel.next;
		Node<T> prevNode = nodeToDel.prev;
		nextNode.prev = nodeToDel.prev;
		prevNode.next = nodeToDel.next;
		return nodeToDel.obj;
	}
	private T removTail() {
		T removEl = tail.obj;
		tail = tail.prev;
		if (tail != null) {
			tail.next = null;
		}
		return removEl;
	}
	private T removHead() {
		T removEl = head.obj;
		head = head.next;
		if (head != null) {
			head.prev = null;
		}
		return removEl;
	}
	@Override
	public int indexOf(Predicate<T> predicate) {
		int ind = 0;
		int res = -1;
		while(ind<size) {
			//[YG] - very inefficient solution. each get(ind) triggers passing over the list
			if(predicate.test(get(ind))) {
				res = ind;
				break;
			}
			ind++;
		}
		return res;
	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		
		int ind = size-1;
		int res= -1;
		while(ind>=0) {
			//[YG] - very inefficient solution. each get(ind) triggers passing over the list
			if(predicate.test(get(ind))) {
				res = ind;
				break;
			}
			ind--;
		}
		return res;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		int sizeOld = size;
		Node<T> curNode = tail;
		
		for (int index= size-1; index>=0; index--) {
			if(predicate.test(curNode.obj)) {
				remove(index); //[YG] - very inefficient solution. each remove(index) triggers passing over the list 
			} 
			curNode=curNode.prev;
		}
		return sizeOld > size;
	}

	@Override
	public void sort(Comparator<T> comp) {
		T[] array = listToArray();
		Arrays.sort(array, comp);
		fillListFromArray(array);
		

	}
	private T[] listToArray() {
		T[] array = (T[]) new Object[size];
		int index=0;
		while(index<size) {
			array[index] = get(index);
			index=index+1;
		}
		return array;
	}
	private void fillListFromArray(T[] array) {
		Node<T> cur = head;
		for (int index = 0; index < array.length; index++) 
		{
			cur.obj=array[index];
			cur=cur.next;
		}
	}	
}
