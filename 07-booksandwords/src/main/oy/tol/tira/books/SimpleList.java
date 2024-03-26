package oy.tol.tira.books;

public class SimpleList {
    private String[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public SimpleList() {
        elements = new String[DEFAULT_CAPACITY];
    }

    public void add(String element) {
        if (size == elements.length) {
            increaseCapacity();
        }
        elements[size++] = element;
    }

    public boolean contains(String element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        size = 0;
        elements = new String[DEFAULT_CAPACITY];
    }

    private void increaseCapacity() {
        int newCapacity = elements.length * 2;
        String[] newElements = new String[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    public int size() {
        return size;
    }

    public String get(int index) {
        if (index >= 0 && index < size) {
            return elements[index];
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
}