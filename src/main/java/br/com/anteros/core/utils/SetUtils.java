package br.com.anteros.core.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SetUtils<E> {

	public static <E> Set<E> of() {
		return new HashSet<E>();
	}

	public static <E> Set<E> copyOf(Iterable<? extends E> elements) {
		checkNotNull(elements);
		return (elements instanceof Collection) ? copyOf(cast(elements)) : copyOf(elements.iterator());
	}

	public static <E> Set<E> copyOf(Collection<? extends E> elements) {
		return (Set<E>) construct(elements.toArray());
	}
	
	public static <E> Set<E> of(E... element) {
		return new HashSet<E>(Arrays.asList(element));
	}

	public static <E> Set<E> copyOf(E[] elements) {
		switch (elements.length) {
		case 0:
			return SetUtils.of();
		default:
			return construct(elements.clone());
		}
	}

	public static <E> Set<E> of(E element) {
		return new HashSet<E>(Arrays.asList(element));
	}

	private static <E> Set<E> construct(E... elements) {
		for (int i = 0; i < elements.length; i++) {
			checkElementNotNull(elements[i], i);
		}
		return (Set<E>) new HashSet<Object>(Arrays.asList(elements));
	}

	private static Object checkElementNotNull(Object element, int index) {
		if (element == null) {
			throw new NullPointerException("at index " + index);
		}
		return element;
	}

	public static <E> Set<E> copyOf(Iterator<? extends E> elements) {
		if (!elements.hasNext()) {
			return of();
		}
		E first = elements.next();
		if (!elements.hasNext()) {
			return of(first);
		} else {
			Set<E> result = new HashSet<E>();
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

}
