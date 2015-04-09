package br.com.anteros.core.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class MapUtils<K, V> {

	public static <K, V> Map<K, V> of() {
		return new HashMap<K, V>();
	}

	public static <K, V> Map<K, V> copyOf(Map<? extends K, ? extends V> map) {
		return new HashMap<K, V>(map);
	}
}
