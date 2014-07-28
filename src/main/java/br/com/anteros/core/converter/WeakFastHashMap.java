/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package br.com.anteros.core.converter;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

class WeakFastHashMap extends HashMap {

    private Map map = null;

    private boolean fast = false;

    public WeakFastHashMap() {
        super();
        this.map = createMap();
    }

    public WeakFastHashMap(int capacity) {
        super();
        this.map = createMap(capacity);
    }

    public WeakFastHashMap(int capacity, float factor) {
        super();
        this.map = createMap(capacity, factor);
    }

    public WeakFastHashMap(Map map) {
        super();
        this.map = createMap(map);
    }


    public boolean getFast() {
        return (this.fast);
    }

    public void setFast(boolean fast) {
        this.fast = fast;
    }


    public Object get(Object key) {
        if (fast) {
            return (map.get(key));
        } else {
            synchronized (map) {
                return (map.get(key));
            }
        }
    }

    public int size() {
        if (fast) {
            return (map.size());
        } else {
            synchronized (map) {
                return (map.size());
            }
        }
    }

    public boolean isEmpty() {
        if (fast) {
            return (map.isEmpty());
        } else {
            synchronized (map) {
                return (map.isEmpty());
            }
        }
    }

    public boolean containsKey(Object key) {
        if (fast) {
            return (map.containsKey(key));
        } else {
            synchronized (map) {
                return (map.containsKey(key));
            }
        }
    }

    public boolean containsValue(Object value) {
        if (fast) {
            return (map.containsValue(value));
        } else {
            synchronized (map) {
                return (map.containsValue(value));
            }
        }
    }

    public Object put(Object key, Object value) {
        if (fast) {
            synchronized (this) {
                Map temp = cloneMap(map);
                Object result = temp.put(key, value);
                map = temp;
                return (result);
            }
        } else {
            synchronized (map) {
                return (map.put(key, value));
            }
        }
    }

    public void putAll(Map in) {
        if (fast) {
            synchronized (this) {
                Map temp =  cloneMap(map);
                temp.putAll(in);
                map = temp;
            }
        } else {
            synchronized (map) {
                map.putAll(in);
            }
        }
    }

    public Object remove(Object key) {
        if (fast) {
            synchronized (this) {
                Map temp = cloneMap(map);
                Object result = temp.remove(key);
                map = temp;
                return (result);
            }
        } else {
            synchronized (map) {
                return (map.remove(key));
            }
        }
    }

    public void clear() {
        if (fast) {
            synchronized (this) {
                map = createMap();
            }
        } else {
            synchronized (map) {
                map.clear();
            }
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return (true);
        } else if (!(o instanceof Map)) {
            return (false);
        }
        Map mo = (Map) o;
        if (fast) {
            if (mo.size() != map.size()) {
                return (false);
            }
            Iterator i = map.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry e = (Map.Entry) i.next();
                Object key = e.getKey();
                Object value = e.getValue();
                if (value == null) {
                    if (!(mo.get(key) == null && mo.containsKey(key))) {
                        return (false);
                    }
                } else {
                    if (!value.equals(mo.get(key))) {
                        return (false);
                    }
                }
            }
            return (true);
            
        } else {
            synchronized (map) {
                if (mo.size() != map.size()) {
                    return (false);
                }
                Iterator i = map.entrySet().iterator();
                while (i.hasNext()) {
                    Map.Entry e = (Map.Entry) i.next();
                    Object key = e.getKey();
                    Object value = e.getValue();
                    if (value == null) {
                        if (!(mo.get(key) == null && mo.containsKey(key))) {
                            return (false);
                        }
                    } else {
                        if (!value.equals(mo.get(key))) {
                            return (false);
                        }
                    }
                }
                return (true);
            }
        }
    }

    public int hashCode() {
        if (fast) {
            int h = 0;
            Iterator i = map.entrySet().iterator();
            while (i.hasNext()) {
                h += i.next().hashCode();
            }
            return (h);
        } else {
            synchronized (map) {
                int h = 0;
                Iterator i = map.entrySet().iterator();
                while (i.hasNext()) {
                    h += i.next().hashCode();
                }
                return (h);
            }
        }
    }

    public Object clone() {
        WeakFastHashMap results = null;
        if (fast) {
            results = new WeakFastHashMap(map);
        } else {
            synchronized (map) {
                results = new WeakFastHashMap(map);
            }
        }
        results.setFast(getFast());
        return (results);
    }

    public Set entrySet() {
        return new EntrySet();
    }

    public Set keySet() {
        return new KeySet();
    }

    public Collection values() {
        return new Values();
    }

    protected Map createMap() {
        return new WeakHashMap();
    }

    protected Map createMap(int capacity) {
        return new WeakHashMap(capacity);
    }

    protected Map createMap(int capacity, float factor) {
        return new WeakHashMap(capacity, factor);
    }
    
    protected Map createMap(Map map) {
        return new WeakHashMap(map);
    }
    
    protected Map cloneMap(Map map) {
        return createMap(map);
    }

    private abstract class CollectionView implements Collection {

        public CollectionView() {
        }

        protected abstract Collection get(Map map);
        protected abstract Object iteratorNext(Map.Entry entry);


        public void clear() {
            if (fast) {
                synchronized (WeakFastHashMap.this) {
                    map = createMap();
                }
            } else {
                synchronized (map) {
                    get(map).clear();
                }
            }
        }

        public boolean remove(Object o) {
            if (fast) {
                synchronized (WeakFastHashMap.this) {
                    Map temp = cloneMap(map);
                    boolean r = get(temp).remove(o);
                    map = temp;
                    return r;
                }
            } else {
                synchronized (map) {
                    return get(map).remove(o);
                }
            }
        }

        public boolean removeAll(Collection o) {
            if (fast) {
                synchronized (WeakFastHashMap.this) {
                    Map temp = cloneMap(map);
                    boolean r = get(temp).removeAll(o);
                    map = temp;
                    return r;
                }
            } else {
                synchronized (map) {
                    return get(map).removeAll(o);
                }
            }
        }

        public boolean retainAll(Collection o) {
            if (fast) {
                synchronized (WeakFastHashMap.this) {
                    Map temp = cloneMap(map);
                    boolean r = get(temp).retainAll(o);
                    map = temp;
                    return r;
                }
            } else {
                synchronized (map) {
                    return get(map).retainAll(o);
                }
            }
        }

        public int size() {
            if (fast) {
                return get(map).size();
            } else {
                synchronized (map) {
                    return get(map).size();
                }
            }
        }


        public boolean isEmpty() {
            if (fast) {
                return get(map).isEmpty();
            } else {
                synchronized (map) {
                    return get(map).isEmpty();
                }
            }
        }

        public boolean contains(Object o) {
            if (fast) {
                return get(map).contains(o);
            } else {
                synchronized (map) {
                    return get(map).contains(o);
                }
            }
        }

        public boolean containsAll(Collection o) {
            if (fast) {
                return get(map).containsAll(o);
            } else {
                synchronized (map) {
                    return get(map).containsAll(o);
                }
            }
        }

        public Object[] toArray(Object[] o) {
            if (fast) {
                return get(map).toArray(o);
            } else {
                synchronized (map) {
                    return get(map).toArray(o);
                }
            }
        }

        public Object[] toArray() {
            if (fast) {
                return get(map).toArray();
            } else {
                synchronized (map) {
                    return get(map).toArray();
                }
            }
        }


        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (fast) {
                return get(map).equals(o);
            } else {
                synchronized (map) {
                    return get(map).equals(o);
                }
            }
        }

        public int hashCode() {
            if (fast) {
                return get(map).hashCode();
            } else {
                synchronized (map) {
                    return get(map).hashCode();
                }
            }
        }

        public boolean add(Object o) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection c) {
            throw new UnsupportedOperationException();
        }

        public Iterator iterator() {
            return new CollectionViewIterator();
        }

        private class CollectionViewIterator implements Iterator {

            private Map expected;
            private Map.Entry lastReturned = null;
            private Iterator iterator;

            public CollectionViewIterator() {
                this.expected = map;
                this.iterator = expected.entrySet().iterator();
            }
 
            public boolean hasNext() {
                if (expected != map) {
                    throw new ConcurrentModificationException();
                }
                return iterator.hasNext();
            }

            public Object next() {
                if (expected != map) {
                    throw new ConcurrentModificationException();
                }
                lastReturned = (Map.Entry)iterator.next();
                return iteratorNext(lastReturned);
            }

            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }
                if (fast) {
                    synchronized (WeakFastHashMap.this) {
                        if (expected != map) {
                            throw new ConcurrentModificationException();
                        }
                        WeakFastHashMap.this.remove(lastReturned.getKey());
                        lastReturned = null;
                        expected = map;
                    }
                } else {
                    iterator.remove();
                    lastReturned = null;
                }
            }
        }
    }

    private class KeySet extends CollectionView implements Set {
    
        protected Collection get(Map map) {
            return map.keySet();
        }
    
        protected Object iteratorNext(Map.Entry entry) {
            return entry.getKey();
        }
    
    }
    
    private class Values extends CollectionView {
    
        protected Collection get(Map map) {
            return map.values();
        }
    
        protected Object iteratorNext(Map.Entry entry) {
            return entry.getValue();
        }
    }
    
    private class EntrySet extends CollectionView implements Set {
    
        protected Collection get(Map map) {
            return map.entrySet();
        }
    
        protected Object iteratorNext(Map.Entry entry) {
            return entry;
        }
    
    }

}
