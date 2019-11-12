package innopolis.stc21.MA;

import static org.junit.Assert.*;


import org.junit.Test;

import java.util.*;

public class MyGenericHashMapTest {

    @Test(expected = IllegalArgumentException.class)
    public void MyHashMapNegativeLoadFactor() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>(10, -0.1f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void MyHashMapNegativeCapacity() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>(-10, 0.1f);
    }

    @org.junit.Test
    public void size() {
        MyGenericHashMap<String, String> myHashMapCustom = new MyGenericHashMap<>(100, 0.50f);
        assertEquals(0, myHashMapCustom.size());

        MyGenericHashMap<String, String> myHashMapDefault = new MyGenericHashMap<>();
        assertEquals(0, myHashMapDefault.size());
        for (int i = 0; i < 100; i++) {
            myHashMapDefault.put(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(100, myHashMapDefault.size());
    }

    @org.junit.Test
    public void isEmpty() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        assertTrue(myHashMap.isEmpty());
        myHashMap.put("test", "test");
        assertFalse(myHashMap.isEmpty());
    }

    @org.junit.Test
    public void containsKey() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        for (int i = 0; i < 200; i++) {
            myHashMap.put(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 0; i < 200; i++) {
            assertTrue(myHashMap.containsKey(String.valueOf(i)));
        }
        for (int i = 200; i < 210; i++) {
            assertFalse(myHashMap.containsKey(String.valueOf(i)));
        }
    }

    @org.junit.Test
    public void containsValue() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        for (int i = 0; i < 200; i++) {
            myHashMap.put(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 0; i < 200; i++) {
            assertTrue(myHashMap.containsValue(String.valueOf(i)));
        }
        for (int i = 200; i < 210; i++) {
            assertFalse(myHashMap.containsValue(String.valueOf(i)));
        }
    }

    @org.junit.Test
    public void get() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        for (int i = 0; i < 200; i++) {
            myHashMap.put(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 0; i < 200; i++) {
            assertEquals(String.valueOf(i), myHashMap.get(String.valueOf(i)));
        }

        myHashMap.put(null, "NULL");
        assertEquals("NULL", myHashMap.get(null));

        myHashMap.put("null", null);
        assertNull(myHashMap.get("null"));
    }

    @org.junit.Test
    public void put() {

        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        assertEquals("4", myHashMap.put("4", "4"));

        assertEquals("12", myHashMap.put("12", "12"));
        assertEquals("21", myHashMap.put("21", "21"));

        assertEquals("12", myHashMap.put("12", "another"));
        assertEquals("21", myHashMap.put("21", "another"));

        assertEquals("123", myHashMap.put("123", "123"));
        assertEquals("132", myHashMap.put("132", "132"));
        assertEquals("213", myHashMap.put("213", "213"));
        assertEquals("231", myHashMap.put("231", "231"));
        assertEquals("312", myHashMap.put("312", "312"));
        assertEquals("321", myHashMap.put("321", "321"));

        assertEquals("123", myHashMap.put("123", "another"));
        assertEquals("132", myHashMap.put("132", "another"));
        assertEquals("213", myHashMap.put("213", "another"));
        assertEquals("231", myHashMap.put("231", "another"));
        assertEquals("312", myHashMap.put("312", "another"));
        assertEquals("321", myHashMap.put("321", "another"));

        assertNull(myHashMap.put(null, null));
        assertNull(myHashMap.put(null, "another"));
    }

    @org.junit.Test
    public void remove() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        for (int i = 0; i < 200; i++) {
            myHashMap.put(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 200; i < 210; i++) {
            assertNull(myHashMap.remove(String.valueOf(i)));
            assertEquals(200, myHashMap.size());
        }
        for (int i = 0; i < 200; i++) {
            assertEquals(String.valueOf(i), myHashMap.remove(String.valueOf(i)));
            assertEquals(200 - i - 1, myHashMap.size());
        }
    }

    @org.junit.Test
    public void putAll() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        for (int i = 0; i < 20; i++) {
            myHashMap.put(String.valueOf(i), String.valueOf(i));
        }
        MyGenericHashMap<String, String> myHashMapAddon = new MyGenericHashMap<>();
        for (int i = 20; i < 30; i++) {
            myHashMapAddon.put(String.valueOf(i), String.valueOf(i));
        }

        myHashMap.putAll(myHashMapAddon);
        for (int i = 0; i < 30; i++) {
            assertEquals(String.valueOf(i), myHashMap.get(String.valueOf(i)));
        }
        for (int i = 30; i < 40; i++) {
            assertNull(myHashMap.get(String.valueOf(i)));
        }
        assertEquals(30, myHashMap.size());

        MyGenericHashMap<String, String> myHashMapCreateByMap = new MyGenericHashMap<>(myHashMap);
        for (int i = 0; i < 30; i++) {
            assertEquals(String.valueOf(i), myHashMapCreateByMap.get(String.valueOf(i)));
        }
        for (int i = 30; i < 40; i++) {
            assertNull(myHashMapCreateByMap.get(String.valueOf(i)));
        }
        assertEquals(30, myHashMapCreateByMap.size());
    }

    @org.junit.Test
    public void clear() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        myHashMap.put("4", "4");
        myHashMap.put("12", "12");
        myHashMap.put("21", "21");
        assertEquals(3, myHashMap.size());

        myHashMap.clear();
        assertEquals(0, myHashMap.size());

        assertEquals("4", myHashMap.put("4", "4"));
        assertEquals("12", myHashMap.put("12", "12"));
        assertEquals("21", myHashMap.put("21", "21"));
        assertEquals(3, myHashMap.size());
    }

    @org.junit.Test
    public void keySet() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        for (int i = 0; i < 20; i++) {
            myHashMap.put(String.valueOf(i), String.valueOf(i));
        }
        Set<String> keys = myHashMap.keySet();
        for (int i = 0; i < 20; i++) {
            assertTrue(keys.contains(String.valueOf(i)));
            assertEquals(20, keys.size());
        }
        for (int i = 20; i < 30; i++) {
            assertFalse(keys.contains(String.valueOf(i)));
        }
    }

    @org.junit.Test
    public void values() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        for (int i = 0; i < 20; i++) {
            myHashMap.put(String.valueOf(i), String.valueOf(i));
        }
        Collection<String> values = myHashMap.values();
        for (int i = 0; i < 20; i++) {
            assertTrue(values.contains(String.valueOf(i)));
            assertEquals(20, values.size());
        }
        for (int i = 20; i < 30; i++) {
            assertFalse(values.contains(String.valueOf(i)));
        }
    }

    @org.junit.Test
    public void entrySet() {
        MyGenericHashMap<String, String> myHashMap = new MyGenericHashMap<>();
        for (int i = 0; i < 200; i++) {
            myHashMap.put(String.valueOf(i), String.valueOf(i));
        }
        Set<Map.Entry<String, String>> entries = myHashMap.entrySet();
        assertEquals(200, entries.size());

        class TestEntry<K, V> implements Map.Entry<K, V> {

            K key;
            V value;

            TestEntry(K key, V value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public K getKey() {
                return key;
            }

            @Override
            public V getValue() {
                return value;
            }

            @Override
            public final V setValue(V newValue) {
                V oldValue = this.value;
                this.value = newValue;
                return oldValue;
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

        for (int i = 0; i < 200; i++) {
            TestEntry<String, String> testEntry = new TestEntry<>(String.valueOf(i), String.valueOf(i));
            assertTrue(entries.contains(testEntry));
        }
    }
}