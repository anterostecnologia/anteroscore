package br.com.anteros.core.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class ListUtils<E> {

	static final Object[] EMPTY_ARRAY = new Object[0];

	private static final List<Object> EMPTY = new ArrayList<Object>(Arrays.asList(EMPTY_ARRAY));

	public static <E> List<E> of() {
		return (List<E>) EMPTY;
	}

	public static <E> List<E> of(E... element) {
		return new ArrayList<E>(Arrays.asList(element));
	}

	public static <E> List<E> copyOf(Iterable<? extends E> elements) {
		checkNotNull(elements);
		return (elements instanceof Collection) ? copyOf(cast(elements)) : copyOf(elements.iterator());
	}
	
	public static <E> List<E> copyOf(Collection<? extends E> elements) {
	    return construct(elements.toArray());
	  }

	public static <E> List<E> copyOf(E[] elements) {
		switch (elements.length) {
		case 0:
			return ListUtils.of();
		default:
			return construct(elements.clone());
		}
	}

	private static <E> List<E> construct(Object... elements) {
		for (int i = 0; i < elements.length; i++) {
			checkElementNotNull(elements[i], i);
		}
		return (List<E>) new ArrayList<Object>(Arrays.asList(elements));
	}

	private static Object checkElementNotNull(Object element, int index) {
		if (element == null) {
			throw new NullPointerException("at index " + index);
		}
		return element;
	}

	public static <E> List<E> copyOf(Iterator<? extends E> elements) {
		if (!elements.hasNext()) {
			return of();
		}
		E first = elements.next();
		if (!elements.hasNext()) {
			return of(first);
		} else {
			List<E> result = new ArrayList<E>();
			result.add(first);
			while (elements.hasNext()) {
				result.add(elements.next());
			}
			return result;
		}
	}

	public static <T> T checkNotNull(T reference) {
		if (reference == null) {
			throw new NullPointerException();
		}
		return reference;
	}

	static <T> Collection<T> cast(Iterable<T> iterable) {
		return (Collection<T>) iterable;
	}
	
	
    public static Object convertToArray(final Class<?> type, final List<?> values) {
        final Object exampleArray = Array.newInstance(type, values.size());
        try {
            return values.toArray((Object[]) exampleArray);
        } catch (ClassCastException e) {
            for (int i = 0; i < values.size(); i++) {
                Array.set(exampleArray, i, values.get(i));
            }
            return exampleArray;
        }
    }
    
    public static <T> List<T> iterToList(final Iterable<T> it) {
        if (it instanceof List) {
            return (List<T>) it;
        }
        if (it == null) {
            return null;
        }

        final List<T> ar = new ArrayList<T>();
        for (final T o : it) {
            ar.add(o);
        }

        return ar;
    }
    
    /**
     * Returns a new list containing all elements that are contained in
     * both given lists.
     *
     * @param <E> the element type
     * @param list1  the first list
     * @param list2  the second list
     * @return  the intersection of those two lists
     * @throws NullPointerException if either list is null
     */
    public static <E> List<E> intersection(final List<? extends E> list1, final List<? extends E> list2) {
        final List<E> result = new ArrayList<>();

        List<? extends E> smaller = list1;
        List<? extends E> larger = list2;
        if (list1.size() > list2.size()) {
            smaller = list2;
            larger = list1;
        }

        final HashSet<E> hashSet = new HashSet<>(smaller);

        for (final E e : larger) {
            if (hashSet.contains(e)) {
                result.add(e);
                hashSet.remove(e);
            }
        }
        return result;
    }
    
    

}
