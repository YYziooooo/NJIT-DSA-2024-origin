package oy.tol.tra;

public class QueueImplementation<E> implements QueueInterface<E> {
    private Object[] itemArray;
    private int capacity;
    private int size = 0;
    private int head = 0;
    private int tail = 0;

    private static final int DEFAULT_QUEUE_SIZE = 10;

    public QueueImplementation() throws QueueAllocationException {
        capacity = DEFAULT_QUEUE_SIZE;
        itemArray = new Object[DEFAULT_QUEUE_SIZE];
    }
    public QueueImplementation(int capacity) {
        this.capacity = capacity;
        this.itemArray = (E[]) new Object[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }


    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public void enqueue(E element) throws QueueAllocationException, NullPointerException {
        if (element == null) {
            throw new NullPointerException("Cannot enqueue null element.");
        }
        if (size == capacity) {
            try {
                int newCapacity = 2 * capacity;
                Object[] newArray = new Object[newCapacity];
                for (int i = 0; i < size; i++) {
                    newArray[i] = itemArray[(head + i) % capacity];
                }
                itemArray = newArray;
                head = 0;
                tail = size;
                capacity = newCapacity;
            } catch (OutOfMemoryError e) {
                throw new QueueAllocationException("Cannot allocate more room for the queue.");
            }
        }
        itemArray[tail] = element;
        tail = (tail + 1) % capacity;
        size++;
    }

    @Override
    public E dequeue() throws QueueIsEmptyException {
        if (isEmpty()) {
            throw new QueueIsEmptyException("Queue is empty");
        }
        E element = (E) itemArray[head];
        itemArray[head] = null;
        head = (head + 1) % capacity;
        size--;
        return element;
    }

    @Override
    public E element() throws QueueIsEmptyException {
        if (size == 0) {
            throw new QueueIsEmptyException("Queue is empty.");
        }
        return (E) itemArray[head];
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
        for (int i = 0; i < capacity; i++) {
            itemArray[i] = null;
        }
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < size; i++) {
            builder.append(itemArray[(head + i) % capacity].toString());
            if (i < size - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}