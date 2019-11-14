package innopolis.stc21.MA;

import static org.junit.Assert.*;


import org.junit.Test;

import java.util.*;

public class MyGenericHashMapTest {

    @Test(expected = IllegalArgumentException.class)
    public void MyHashMapNegativeLoadFactor() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>(10, -0.1f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void MyHashMapNegativeCapacity() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>(-10, 0.1f);
    }

    @org.junit.Test
    public void size() {
        MyGenericHashMap<Integer, Integer> myHashMapCustom = new MyGenericHashMap<>(100, 0.50f);
        assertEquals(0, myHashMapCustom.size());

        MyGenericHashMap<Integer, Integer> myHashMapDefault = new MyGenericHashMap<>();
        assertEquals(0, myHashMapDefault.size());
        for (Integer i = 0; i < 100; i++) {
            myHashMapDefault.put(i, i);
        }
        assertEquals(100, myHashMapDefault.size());
    }

    @org.junit.Test
    public void isEmpty() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        assertTrue(myHashMap.isEmpty());
        myHashMap.put(0, 0);
        assertFalse(myHashMap.isEmpty());
    }

    @org.junit.Test
    public void containsKey() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        for (Integer i = 0; i < 200; i++) {
            myHashMap.put(i, i);
        }
        for (Integer i = 0; i < 200; i++) {
            assertTrue(myHashMap.containsKey(i));
        }
        for (Integer i = 200; i < 210; i++) {
            assertFalse(myHashMap.containsKey(i));
        }
    }

    @org.junit.Test
    public void containsValue() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        for (Integer i = 0; i < 200; i++) {
            myHashMap.put(i, i);
        }
        for (Integer i = 0; i < 200; i++) {
            assertTrue(myHashMap.containsValue(i));
        }
        for (Integer i = 200; i < 210; i++) {
            assertFalse(myHashMap.containsValue(i));
        }
    }

    @org.junit.Test
    public void get() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        for (Integer i = 0; i < 200; i++) {
            myHashMap.put(i, i);
        }
        for (Integer i = 0; i < 200; i++) {
            assertEquals(i, myHashMap.get(i));
        }

        myHashMap.put(null, 0);
        assertEquals((Integer) 0, myHashMap.get(null));

        myHashMap.put(201, null);
        assertNull(myHashMap.get(201));
    }

    @org.junit.Test
    public void put() {

        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        assertEquals((Integer)4, myHashMap.put(4, 4));

        assertEquals((Integer)12, myHashMap.put(12, 12));
        assertEquals((Integer)21, myHashMap.put(21, 21));

        assertEquals((Integer)12, myHashMap.put(12, 1000));
        assertEquals((Integer)21, myHashMap.put(21, 1000));

        assertEquals((Integer)123, myHashMap.put(123, 123));
        assertEquals((Integer)132, myHashMap.put(132, 132));
        assertEquals((Integer)213, myHashMap.put(213, 213));
        assertEquals((Integer)231, myHashMap.put(231, 231));
        assertEquals((Integer)312, myHashMap.put(312, 312));
        assertEquals((Integer)321, myHashMap.put(321, 321));

        assertEquals((Integer)123, myHashMap.put(123, 1000));
        assertEquals((Integer)132, myHashMap.put(132, 1000));
        assertEquals((Integer)213, myHashMap.put(213, 1000));
        assertEquals((Integer)231, myHashMap.put(231, 1000));
        assertEquals((Integer)312, myHashMap.put(312, 1000));
        assertEquals((Integer)321, myHashMap.put(321, 1000));

        assertNull(myHashMap.put(null, null));
        assertNull(myHashMap.put(null, 1000));
    }

    @org.junit.Test
    public void remove() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        for (Integer i = 0; i < 200; i++) {
            myHashMap.put(i, i);
        }
        for (Integer i = 200; i < 210; i++) {
            assertNull(myHashMap.remove(i));
            assertEquals(200, myHashMap.size());
        }
        for (Integer i = 0; i < 200; i++) {
            assertEquals(i, myHashMap.remove(i));
            assertEquals(200 - i - 1, myHashMap.size());
        }
    }

    @org.junit.Test
    public void putAll() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        for (Integer i = 0; i < 20; i++) {
            myHashMap.put(i, i);
        }
        MyGenericHashMap<Integer, Integer> myHashMapAddon = new MyGenericHashMap<>();
        for (Integer i = 20; i < 30; i++) {
            myHashMapAddon.put(i, i);
        }

        myHashMap.putAll(myHashMapAddon);
        for (Integer i = 0; i < 30; i++) {
            assertEquals(i, myHashMap.get(i));
        }
        for (Integer i = 30; i < 40; i++) {
            assertNull(myHashMap.get(i));
        }
        assertEquals(30, myHashMap.size());

        MyGenericHashMap<Integer, Integer> myHashMapCreateByMap = new MyGenericHashMap<>(myHashMap);
        for (Integer i = 0; i < 30; i++) {
            assertEquals(i, myHashMapCreateByMap.get(i));
        }
        for (Integer i = 30; i < 40; i++) {
            assertNull(myHashMapCreateByMap.get(i));
        }
        assertEquals(30, myHashMapCreateByMap.size());
    }

    @org.junit.Test
    public void clear() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        myHashMap.put(4, 4);
        myHashMap.put(12, 12);
        myHashMap.put(21, 21);
        assertEquals(3, myHashMap.size());

        myHashMap.clear();
        assertEquals(0, myHashMap.size());

        assertEquals((Integer) 4, myHashMap.put(4, 4));
        assertEquals((Integer)12, myHashMap.put(12, 12));
        assertEquals((Integer)21, myHashMap.put(21, 21));
        assertEquals(3, myHashMap.size());
    }

    @org.junit.Test
    public void keySet() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        for (Integer i = 0; i < 20; i++) {
            myHashMap.put(i, i);
        }
        Set<Integer> keys = myHashMap.keySet();
        for (Integer i = 0; i < 20; i++) {
            assertTrue(keys.contains(i));
            assertEquals(20, keys.size());
        }
        for (Integer i = 20; i < 30; i++) {
            assertFalse(keys.contains(i));
        }
    }

    @org.junit.Test
    public void values() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        for (Integer i = 0; i < 20; i++) {
            myHashMap.put(i, i);
        }
        Collection<Integer> values = myHashMap.values();
        for (Integer i = 0; i < 20; i++) {
            assertTrue(values.contains(i));
            assertEquals(20, values.size());
        }
        for (Integer i = 20; i < 30; i++) {
            assertFalse(values.contains(i));
        }
    }

    @org.junit.Test
    public void entrySet() {
        MyGenericHashMap<Integer, Integer> myHashMap = new MyGenericHashMap<>();
        for (Integer i = 0; i < 200; i++) {
            myHashMap.put(i, i);
        }
        Set<Map.Entry<Integer, Integer>> entries = myHashMap.entrySet();
        assertEquals(200, entries.size());

        for (Integer i = 0; i < 200; i++) {
            Map.Entry<Integer, Integer> testEntry = new AbstractMap.SimpleEntry<>(i, i);
            assertTrue(entries.contains(testEntry));
        }
    }
}