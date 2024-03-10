
package oy.tol.tra;

import java.util.Arrays;

public class QueueImplementation<T> implements QueueInterface<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private T[] array;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public QueueImplementation(int capacity) {
        this.capacity = capacity > 0 ? capacity : DEFAULT_CAPACITY;
        this.array = (T[]) new Object[this.capacity];
        this.size = 0;
    }

    public QueueImplementation() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public void enqueue(T element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the queue.");
        }
        if (size == capacity) {
            reallocate();
        }
        array[size++] = element;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new QueueIsEmptyException("Queue is empty, cannot dequeue.");
        }
        T element = array[0];
        System.arraycopy(array, 1, array, 0, --size);
        return element;
    }

    @Override
    public T element() {
        if (isEmpty()) {
            throw new QueueIsEmptyException("Queue is empty, cannot retrieve element.");
        }
        return array[0];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        Arrays.fill(array, null);
        size = 0;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @SuppressWarnings("unchecked")
    private void reallocate() {
        capacity *= 2;
        T[] newArray = (T[]) new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
