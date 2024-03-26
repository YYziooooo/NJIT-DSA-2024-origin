package oy.tol.tira.books;

interface Visitor<K extends Comparable<K>, V extends Comparable<V>> {
    void visit(TreeNode<K,V> node);
}