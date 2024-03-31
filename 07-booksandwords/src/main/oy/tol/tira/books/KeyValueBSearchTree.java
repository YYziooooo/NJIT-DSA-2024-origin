package oy.tol.tira.books;

public class KeyValueBSearchTree<K extends Comparable<K>, V extends Comparable<V>> implements Dictionary<K, V> {

    // This is the BST implementation, KeyValueHashTable has the hash table
    // implementation

    private TreeNode<K, V> root;
    private int count = 0;
    private int maxTreeDepth = 0;

    @Override
    public Type getType() {
        return Type.BST;
    }

    @Override
    public int size() {
        // TODO: Implement this
        return count;
    }


    /**
     * Prints out the statistics of the tree structure usage.
     * Here you should print out member variable information which tell something
     * about
     * your implementation.
     * <p>
     * For example, if you implement this using a hash table, update member
     * variables of the class
     * (int counters) in add(K) whenever a collision happen. Then print this counter
     * value here.
     * You will then see if you have too many collisions. It will tell you that your
     * hash function
     * is good or bad (too much collisions against data size).
     */
    @Override
    public String getStatus() {
        String toReturn = "Tree has max depth of " + maxTreeDepth + ".\n";
        toReturn += "Longest collision chain in a tree node is " + TreeNode.longestCollisionChain + "\n";
        if(root == null){
            toReturn += "The tree is empty.\n";
            toReturn += "Min path height to bottom: 0\n";
            toReturn += "Max path height to bottom: 0\n";
        }else{
            TreeAnalyzerVisitor<K, V> visitor = new TreeAnalyzerVisitor<>();
            root.accept(visitor);
            toReturn += "Min path height to bottom: " + visitor.minHeight + "\n";
            toReturn += "Max path height to bottom: " + visitor.maxHeight + "\n";
        }
        toReturn += "Ideal height if balanced: " + Math.ceil(Math.log(count)) + "\n";
        return toReturn;
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        // TODO: Implement this
        // Remember null check.
        // If root is null, should go there.

        // update the root node. But it may have children
        // so do not just replace it with this new node but set
        // the keys and values for the already existing root.

        if (key == null || value == null) {
            throw new IllegalArgumentException("Neither key nor value can be null.");
        }
        if (root == null) {
            root = new TreeNode<>(key, value);
            count++;
            maxTreeDepth = 1; // Just added the root, so depth is 1
            return true;
        } else {
            int depthBefore = TreeNode.currentAddTreeDepth;
            int added = root.insert(key, value, customHashCode(key));
            int depthAfter = TreeNode.currentAddTreeDepth;
            TreeNode.currentAddTreeDepth = 0; // Reset for next addition
            if (added > 0) {
                count++; // Increase count if new node was added
            }
            if (depthAfter > maxTreeDepth) {
                maxTreeDepth = depthAfter; // Update max depth if it increased
            }
            return added > 0;
        }
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        // TODO: Implement this. //Think about this
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        return (root != null) ? root.find(key, customHashCode(key)) : null;
    }

    @Override
    public void ensureCapacity(int size) throws OutOfMemoryError {
        // Nothing to do here. Trees need no capacity.
    }

    @Override
    public Pair<K, V>[] toSortedArray() {
        if (root == null) {
            return new Pair[0];
        }
        TreeToArrayVisitor<K, V> visitor = new TreeToArrayVisitor<>(count);
        root.accept(visitor);
        Pair<K, V>[] sorted = visitor.getArray();
        Algorithms.fastSort(sorted);
        return sorted;
    }

    @Override
    public void compress() throws OutOfMemoryError {
        // Nothing to do here, since BST does not use extra space like array based
        // structures.
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