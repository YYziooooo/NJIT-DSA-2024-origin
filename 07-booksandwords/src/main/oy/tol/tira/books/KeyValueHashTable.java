package oy.tol.tira.books;

public class KeyValueHashTable<K extends Comparable<K>, V extends Comparable<V>> implements Dictionary<K, V> {

    // This should implement a hash table.

    private Pair<K, V>[] values = null;
    private int count = 0;
    private int collisionCount = 0;
    private int maxProbingSteps = 0;
    private int reallocationCount = 0;
    private static final double LOAD_FACTOR = 0.45;
    private static final int DEFAULT_CAPACITY = 20;

    public KeyValueHashTable(int capacity) throws OutOfMemoryError {
        ensureCapacity(capacity);
    }

    public KeyValueHashTable() throws OutOfMemoryError {
        ensureCapacity(DEFAULT_CAPACITY);
    }

    @Override
    public Type getType() {
        return Type.HASHTABLE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void ensureCapacity(int capacity) throws OutOfMemoryError {
        if (capacity < DEFAULT_CAPACITY) {
            capacity = DEFAULT_CAPACITY;
        }
        // Assuming capacity means the count of elements to add, so multiplying by fill factor.
        values = (Pair<K, V>[]) new Pair[(int) ((double) capacity * (1.0 + LOAD_FACTOR))];
        reallocationCount = 0;
        count = 0;
        collisionCount = 0;
        maxProbingSteps = 0;
    }

    @Override
    public int size() {
        // TODO: Implement this.
        return count;
    }

    /**
     * Prints out the statistics of the hash table.
     * Here you should print out member variable information which tell something
     * about your implementation.
     * <p>
     * For example, if you implement this using a hash table, update member
     * variables of the class (int counters) in add() whenever a collision
     * happen. Then print this counter value here.
     * You will then see if you have too many collisions. It will tell you that your
     * hash function is not good.
     */
    @Override
    public String getStatus() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Hash table load factor is %.2f%n", LOAD_FACTOR));
        builder.append(String.format("Hash table capacity is %d%n", values.length));
        builder.append(String.format("Current fill rate is %.2f%%%n", (count / (double) values.length) * 100.0));
        builder.append(String.format("Hash table had %d collisions when filling the hash table.%n", collisionCount));
        builder.append(String.format("Hash table had to probe %d times in the worst case.%n", maxProbingSteps));
        builder.append(String.format("Hash table had to reallocate %d times.%n", reallocationCount));
        return builder.toString();
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        // TODO: Implement this.
        // Remeber to check for null values.
        // Remember to get the hash key from the Person,
        // hash table computes the index for the Person (based on the hash value),
        // if index was taken by different Person (collision), get new hash and index,
        // insert into table when the index has a null in it,
        // return true if existing Person updated or new Person inserted.
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");
        int index = Math.abs(customHashCode(key) % values.length);
        int originalIndex = index;
        boolean isNewAddition = false; // This will indicate whether a new key-value pair was added.
        int steps = 0;
        while (values[index] != null && !values[index].getKey().equals(key)) {
            index = (index + 1) % values.length;
            steps++;
            if (index == originalIndex) {
                throw new OutOfMemoryError("Hash table is full.");
            }
        }

        // If found an empty spot, it means adding a new key-value pair.
        if (values[index] == null) {
            count++;
            isNewAddition = true;
        }

        values[index] = new Pair<>(key, value);
        maxProbingSteps = Math.max(maxProbingSteps, steps);

        if (steps > 0) collisionCount++;


        if (((double) count / values.length) > LOAD_FACTOR) {
            reallocate((int) (values.length * (1.0 / LOAD_FACTOR)));
        }

        // Return true if a new key-value pair was added, false otherwise.
        return isNewAddition;
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");

        int index = Math.abs(customHashCode(key) % values.length);
        int originalIndex = index;

        while (values[index] != null && !values[index].getKey().equals(key)) {
            index = (index + 1) % values.length;
            if (index == originalIndex) {
                return null;
            }
        }

        return values[index] != null ? values[index].getValue() : null;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Pair<K, V>[] toSortedArray() {
        Pair<K, V>[] sorted = (Pair<K, V>[]) new Pair[count];
        int newIndex = 0;
        for (int index = 0; index < values.length; index++) {
            if (values[index] != null) {
                sorted[newIndex++] = new Pair<>(values[index].getKey(), values[index].getValue());
            }
        }
        Algorithms.fastSort(sorted);
        return sorted;
    }

    @SuppressWarnings("unchecked")
    private void reallocate(int newSize) throws OutOfMemoryError {
        if (newSize < DEFAULT_CAPACITY) {
            newSize = DEFAULT_CAPACITY;
        }
        reallocationCount++;
        Pair<K, V>[] oldPairs = values;
        this.values = (Pair<K, V>[]) new Pair[(int) ((double) newSize * (1.0 + LOAD_FACTOR))];
        count = 0;
        collisionCount = 0;
        maxProbingSteps = 0;
        for (int index = 0; index < oldPairs.length; index++) {
            if (oldPairs[index] != null) {
                add(oldPairs[index].getKey(), oldPairs[index].getValue());
            }
        }
    }

    @Override
    public void compress() throws OutOfMemoryError {
        int newCapacity = (int) (count * (1.0 / LOAD_FACTOR));
        if (newCapacity < values.length) {
            reallocate(newCapacity);
        }
    }
    private int customHashCode(K key) {
        int hash = 0;
        String keyString = key.toString();
        for (int i = 0; i < keyString.length(); i++) {
            hash = 31 * hash + keyString.charAt(i);
        }
        return hash;
    }

}