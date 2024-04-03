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
        TreeAnalyzerVisitor<K, V> visitor = new TreeAnalyzerVisitor<>();
        root.accept(visitor);
        toReturn += "Min path height to bottom: " + visitor.minHeight + "\n";
        toReturn += "Max path height to bottom: " + visitor.maxHeight + "\n";
        toReturn += "Ideal height if balanced: " + Math.ceil(Math.log(count)) + "\n";
        return toReturn;
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        // TODO: Implement this
        // Remember null check.
        // If root is null, should go there.
        if (null==key || value==null) throw new IllegalArgumentException("Person or phone number cannot be null");
        if (root == null){
            root = new TreeNode<>(key,value);
            count++;
            return true;
        }
        int added = root.insert(key,value,key.hashCode());
        if (TreeNode.currentAddTreeDepth>maxTreeDepth){
            maxTreeDepth = TreeNode.currentAddTreeDepth;
        }
        // update the root node. But it may have children
        // so do not just replace it with this new node but set
        // the keys and values for the already existing root.
        TreeNode.currentAddTreeDepth = 0;
        if (added == 1){
            count++;
            return true;
        }else return false;
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        // TODO: Implement this. //Think about this
        if (null == key) throw new IllegalArgumentException("Person to find cannot be null");
        return (root.find(key,key.hashCode()));
    }

    @Override
    public void ensureCapacity(int size) throws OutOfMemoryError {
        // Nothing to do here. Trees need no capacity.
    }

    @Override
    public Pair<K, V>[] toSortedArray() {
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


}