package question_2;

import java.util.*;

/**
 * An implementation of a SortedMap using an AVL tree.
 * @param <K> the type of keys in this map
 * @param <V> the type of values in this map
 */ 
public class AVLTreeMap<K extends Comparable<K>, V> implements SortedMap<K, V> {
    private static class Node<K, V> {
        /**
         * The key of this node.
         */
        K key;
        /**
         * The value associated with this key.
         */
        V value;
        /**
         * The height of the subtree rooted at this node.
         */
        int height;
        /**
         * The left child of this node.
         */
        Node<K, V> left;
        /**
         * The right child of this node.
         */
        Node<K, V> right;

        /**
         * Constructs a new Node with the given key and value.
         * @param key the key for this node
         * @param value the value associated with this key
         */
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.left = null;
            this.right = null;
        }
    }

    private Node<K, V> root;
    private int size;

    /**
     * Constructs an empty AVLTreeMap.
     */
    public AVLTreeMap() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Returns the number of key-value mappings in this map.
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     * @return true if this map contains no key-value mappings
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     * @param key the key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     * @throws ClassCastException if the specified key cannot be compared with the keys currently in the map
     * @throws NullPointerException if the specified key is null and this map does not permit null keys
     */
    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    /**
     * Returns true if this map maps one or more keys to the specified value.
     * @param value the value whose presence in this map is to be tested
     * @return true if this map maps one or more keys to the specified value
     */
    @Override
    public boolean containsValue(Object value) {
        for (Entry<K, V> entry : entrySet()) {
            if (Objects.equals(entry.getValue(), value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     * @throws ClassCastException if the specified key cannot be compared with the keys currently in the map
     * @throws NullPointerException if the specified key is null and this map does not permit null keys
    */
    @Override
    public V get(Object key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        @SuppressWarnings("unchecked")
        K k = (K) key;
        Node<K, V> node = root;
        while (node != null) {
            int cmp = k.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.value;
            }
        }
        return null;
    }

    /**
    * Associates the specified value with the specified key in this map.
    * @param key the key with which the specified value is to be associated
    * @param value the value to be associated with the specified key
    * @return the previous value associated with the specified key, or null if there was no mapping for the key
    * @throws ClassCastException if the specified key cannot be compared with the keys currently in the map
    * @throws NullPointerException if the specified key is null and this map does not permit null keys
    */
    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        if (root == null) {
            root = new Node<>(key, value);
            size++;
            return null;
        }
        Node<K, V> node = root;
        Node<K, V> parent = null;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                parent = node;
                node = node.left;
            } else if (cmp > 0) {
                parent = node;
                node = node.right;
            } else {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        Node<K, V> newNode = new Node<>(key, value);
        if (key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        size++;
        rebalance(parent);
        return null;
    }

    /**
    * Removes the mapping for a key from this map if it is present.
    * @param key the key whose mapping is to be removed from the map
    * @return the value to which the key had been mapped, or null if the key did not have a mapping
    * @throws ClassCastException if the specified key cannot be compared with the keys currently in the map
    * @throws NullPointerException if the specified key is null and this map does not permit null keys
    */
    @Override
    public V remove(Object key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        @SuppressWarnings("unchecked")
        K k = (K) key;
        Node<K, V> node = root;
        Node<K, V> parent = null;
        while (node != null) {
            int cmp = k.compareTo(node.key);
            if (cmp < 0) {
                parent = node;
                node = node.left;
            } else if (cmp > 0) {
                parent = node;
                node = node.right;
            } else {
                V oldValue = node.value;
                if (node.left == null && node.right == null) {
                    if (node == root) {
                        root = null;
                    } else if (node == parent.left) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                } else if (node.left == null) {
                    if (node == root) {
                        root = node.right;
                    } else if (node == parent.left) {
                        parent.left = node.right;
                    } else {
                        parent.right = node.right;
                    }
                } else if (node.right == null) {
                    if (node == root) {
                        root = node.left;
                    } else if (node == parent.left) {
                        parent.left = node.left;
                    } else {
                        parent.right = node.left;
                    }
                } else {
                    Node<K, V> successor = findMin(node.right);
                    node.key = successor.key;
                    node.value = successor.value;
                    if (successor == node.right) {
                        node.right = successor.right;
                    } else {
                        Node<K, V> successorParent = findParent(node.right, successor);
                        successorParent.left = successor.right;
                    }
                }
                size--;
                rebalance(parent);
                return oldValue;
            }
        }
        return null;
    }

    /**
    * Returns the number of key-value mappings in this map.
    * @return the number of key-value mappings in this map
    */
    @Override
    public int size() {
        return size;
    }

    /**
    * Returns a set view of the keys contained in this map.
    * @return a set view of the keys contained in this map
    */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        addKeys(root, keySet);
        return keySet;
    }

    /**
    * Adds the keys in the subtree rooted at the given node to the given set.
    * @param node the root of the subtree
    * @param keySet the set to add the keys to
    */
    private void addKeys(Node<K, V> node, Set<K> keySet) {
        if (node != null) {
            addKeys(node.left, keySet);
            keySet.add(node.key);
            addKeys(node.right, keySet);
        }
    }

    /**
    * Returns a collection view of the values contained in this map.
    * @return a collection view of the values contained in this map
    */
    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        addValues(root, values);
        return values;
    }

    /**
    * Adds the values in the subtree rooted at the given node to the given list.
    * @param node the root of the subtree
    * @param values the list to add the values to
    */
    private void addValues(Node<K, V> node, List<V> values) {
        if (node != null) {
            addValues(node.left, values);
            values.add(node.value);
            addValues(node.right, values);
        }
    }

    /**
    * Returns a set view of the mappings contained in this map.
    * @return a set view of the mappings contained in this map
    */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = new HashSet<>();
        addEntries(root, entrySet);
        return entrySet;
    }

    /**
     * Adds all entries in the subtree rooted at the specified node to the specified set.
     * @param node the root of the subtree whose entries should be added to the set
     * @param entrySet the set to which the entries should be added
     */
    private void addEntries(Node<K, V> node, Set<Map.Entry<K, V>> entrySet) {
        if (node != null) {
            addEntries(node.left, entrySet);
            entrySet.add(new Entry(node.key, node.value));
            addEntries(node.right, entrySet);
        }
    }
}

