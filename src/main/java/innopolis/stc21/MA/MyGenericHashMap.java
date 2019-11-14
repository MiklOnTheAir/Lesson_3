package innopolis.stc21.MA;

import java.util.*;

public class MyGenericHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;
    private static final int MAX_CAPACITY = Integer.MAX_VALUE;

    /**
     * Class for storing key-value pairs
     */
    private static class Node<K, V> implements Map.Entry<K,V>{

        private final int hash;
        private final K key;
        private V value;
        private Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        int getHash() {
            return hash;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        final Node<K, V> getNext() {
            return next;
        }

        @Override
        public final V setValue(V newValue) {
            V oldValue = this.value;
            this.value = newValue;
            return oldValue;
        }

        final Node<K, V> setNext(Node<K, V> newNext) {
            Node<K, V> oldNext = this.next;
            this.next = newNext;
            return oldNext;
        }

        @Override
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> node = (Map.Entry<?, ?>) o;
                if (Objects.equals(this.getKey(), node.getKey()) &&
                        Objects.equals(this.getValue(), node.getValue())) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public final String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }


    private int capacity;
    private float loadFactor;
    private int size;
    private Node<K, V>[] Nodes;

    MyGenericHashMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.size = 0;
        this.Nodes = new Node[DEFAULT_CAPACITY];
    }

    MyGenericHashMap(int capacity, float loadFactor) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity is nonpositive");
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("loadFactor is nonpositive");
        }

        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.Nodes = new Node[capacity];
    }

    MyGenericHashMap(Map<? extends K, ? extends V> m){
        this();
        putAll(m);
    }

    private final static int hash(Object key) {
        int hash = 0;
        if (key != null) {
            hash = (hash = key.hashCode()) ^ (hash >>> 16);
        }
        return Math.abs(hash);
    }

    private final int index(int hash) {
        int length = this.Nodes.length;
        if (length <= 0) {
            throw new ArrayIndexOutOfBoundsException(length);
        }
        if (hash < 0) {
            throw new ArrayIndexOutOfBoundsException(hash);
        }
        return hash % length;
    }

    /**
     * Internal search Node of MyGenericHashMap
     *
     * @param hash hash of the sought Node
     * @param key  key of the sought Node
     * @return found Node or null
     */
    private final Node<K, V> getNode(int hash, Object key) {
        Node current = this.Nodes[this.index(hash)];
        while (current != null) {
            if (current.getHash() == hash && ((current.getKey() != null && current.getKey().equals(key)) || key == null)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Internal put value in Map with replacement values if necessary
     * @param hash Hash of Node
     * @param key Key of Node
     * @param value New Value of Node
     * @param onlyIfAbsent replacement value if absent
     * @return New value if Node not exist or @onlyIfAbsent = false or old value if Node exist and @onlyIfAbsent = true
     */
    private final V putVal(int hash, K key, V value, boolean onlyIfAbsent) {
        Node<K, V> current = this.getNode(hash, key);
        if (current != null) {
            if (!onlyIfAbsent) {
                current.setValue(value);
            }
            return current.getValue();
        }
        int index = index(hash);
        Node<K, V> newNode = new Node<K, V>(hash, key, value, Nodes[index]);
        Nodes[index] = newNode;
        this.size++;
        if (size > capacity * loadFactor) {
            this.resize();
        }
        return value;
    }

    /**
     * Internal remove Node
     * @param key Key of Node
     * @return Еhe node that was deleted
     */
    private final Node<K, V> removeNode(Object key) {
        int hash = hash(key);
        int index = this.index(hash);
        Node<K, V> parent = null;
        Node<K, V> current = this.Nodes[index];
        while (current != null) {
            if (current.getHash() == hash && ((current.getKey() != null && current.getKey().equals(key)) || key == null)) {
                if (parent == null) {
                    this.Nodes[index] = current.getNext();
                } else {
                    parent.setNext(current.getNext());
                }
                this.size--;
                return current;
            }
            parent = current;
            current = current.getNext();
        }
        return null;
    }

    /**
     * Resize MyGenericHashMap. Doubling the size of the internal array
     */
    private final void resize() {
        this.capacity = (this.capacity < (MAX_CAPACITY >> 1))
                ? this.capacity << 1
                : MAX_CAPACITY;
        this.size = 0;
        Node<K, V>[] oldNodes = this.Nodes;
        this.Nodes = (Node<K, V>[]) new Node[this.capacity];
        for (int i = 0; i < oldNodes.length; i++) {
            Node<K, V> current = oldNodes[i];
            while (current != null) {
                this.put(current.getKey(), current.getValue());
                current = current.getNext();
            }
        }
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.getNode(hash(key), key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < Nodes.length; i++) {
            Node<K, V> current = Nodes[i];
            while (current != null) {
                if(Objects.equals(value, current.getValue())){
                    return true;
                }
                current = current.getNext();
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Node<K, V> current = this.getNode(hash(key), key);
        if (current != null) {
            return current.getValue();
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        return putVal(hash(key), key, value, true);
    }

    @Override
    public V remove(Object key) {
        Node<K, V> node = removeNode(key);
        return (node == null)
                ? null
                : node.getValue();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        int mapSize = m.size();
        if (((mapSize + this.size()) * this.loadFactor) > this.capacity) {
            this.resize();
        }
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            this.putVal(hash(key), key, value, true);
        }
    }

    @Override
    public void clear() {
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.size = 0;
        this.Nodes = new Node[capacity];
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < Nodes.length; i++) {
            Node<K, V> current = Nodes[i];
            while (current != null) {
                keys.add(current.getKey());
                current = current.getNext();
            }
        }

        return keys;
    }

    @Override
    public Collection<V> values() {
       Collection<V> collectionValues = new ArrayList<>();
        for (int i = 0; i < Nodes.length; i++) {
            Node<K, V> current = Nodes[i];
            while (current != null) {
                collectionValues.add(current.getValue());
                current = current.getNext();
            }
        }
        return collectionValues;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();
        for (int i = 0; i < Nodes.length; i++) {
            Node<K, V> current = Nodes[i];
            while (current != null) {
                entries.add(new AbstractMap.SimpleEntry<K, V>(current.getKey(),current.getValue()));
                current = current.getNext();
            }
        }
        return entries;
    }

}
