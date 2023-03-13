package question_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A binary tree that can be restructured by moving nodes around.
 *
 * @param <T> the type of data stored in the tree
 */
public class RestructureableNodeBinaryTree<T extends Comparable<T>> {

    private Node<T> root;

    /**
     * Creates a new, empty RestructureableNodeBinaryTree.
     */
    public RestructureableNodeBinaryTree() {
        root = null;
    }

    /**
     * An inner class representing a node in the tree.
     *
     * @param <T> the type of data stored in the node
     */
    public class Node<T> {
        T data;
        Node<T> left, right, parent;

         /**
         * Creates a new Node with the specified data and parent.
         *
         * @param data   the data to store in the node
         * @param parent the parent of the new node
         */
        Node(T data, Node<T> parent) {
            this.data = data;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }

        /**
         * Returns true if this node has a left child.
         *
         * @return true if this node has a left child, false otherwise
         */
        boolean hasLeftChild() {
            return left != null;
        }

        /**
         * Returns true if this node has a right child.
         *
         * @return true if this node has a right child, false otherwise
         */
        boolean hasRightChild() {
            return right != null;
        }

        /**
         * Returns true if this node is a left child of its parent.
         *
         * @return true if this node is a left child of its parent, false otherwise
         */
        boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * Returns true if this node is a right child of its parent.
         *
         * @return true if this node is a right child of its parent, false otherwise
         */
        boolean isRightChild() {
            return parent != null && this == parent.right;
        }
    }

    // Method to find a node with a given value in the tree
    public Node<T> find(T value) {
        Node<T> current = root;
        while (current != null) {
            int cmp = value.compareTo(current.data);
            if (cmp == 0) {
                return current;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    // Method to insert a value into the tree
    public void insert(T value) {
        if (root == null) {
            root = new Node<T>(value, null);
        } else {
            insert(value, root);
        }
    }

    // Recursive helper method to insert a value into the tree
    private void insert(T value, Node<T> node) {
        int cmp = value.compareTo(node.data);
        if (cmp < 0) {
            if (node.hasLeftChild()) {
                insert(value, node.left);
            } else {
                node.left = new Node<T>(value, node);
            }
        } else {
            if (node.hasRightChild()) {
                insert(value, node.right);
            } else {
                node.right = new Node<T>(value, node);
            }
        }
    }

    // Method to delete a node from the tree
    public void delete(T value) {
        Node<T> node = find(value);
        if (node != null) {
            delete(node);
        }
    }

    // Helper method to delete a node from the tree
    private void delete(Node<T> node) {
        if (node.hasLeftChild() && node.hasRightChild()) {
            // Node to be deleted has two children
            Node<T> successor = findMin(node.right);
            node.data = successor.data;
            delete(successor);
        } else if (node.hasLeftChild()) {
            // Node to be deleted has only left child
            replace(node, node.left);
        } else if (node.hasRightChild()) {
            // Node to be deleted has only right child
            replace(node, node.right);
        } else {
            // Node to be deleted has no children
            replace(node, null);
        }
    }

    // Helper method to replace a node in the tree
    private void replace(Node<T> node, Node<T> replacement) {
        if (node.parent == null) {
            // Node to be replaced is root
            root = replacement;
        } else if (node.isLeftChild()) {
            // Node to be replaced is a left child
            node.parent.left = replacement;
        } else {
            // Node to be replaced is a right child
            node.parent.right = replacement;
        }

        if (replacement != null) {
            replacement.parent = node.parent;
        }
    }

    // Method to find the minimum node in a tree rooted at a given node
    private Node<T> findMin(Node<T> node) {
        while (node.hasLeftChild()) {
            node = node.left;
        }
        return node;
    }

    // Method to perform a rotation operation
    public void restructure(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> grandparent = parent.parent;

        if (grandparent == null) {
            // Node is the root of the tree, no rotation possible
            return;
        }

        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                // Left-Left case
                rotateRight(grandparent);
            } else {
                // Left-Right case
                rotateLeft(parent);
                rotateRight(grandparent);
            }
        } else {
            if (node.isLeftChild()) {
                // Right-Left case
                rotateRight(parent);
                rotateLeft(grandparent);
            } else {
                // Right-Right case
                rotateLeft(grandparent);
            }
        }
    }

    // Helper method to perform a left rotation at a node
    private void rotateLeft(Node<T> node) {
        Node<T> rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node.isLeftChild()) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
    }

    // Helper method to perform a right rotation at a node
    private void rotateRight(Node<T> node) {
        Node<T> leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node.isLeftChild()) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
    }

    public void traverseInOrder() {
        if (root == null) {
            return;
        }

        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            System.out.print(current.data + " ");

            current = current.right;
        }
    }

}