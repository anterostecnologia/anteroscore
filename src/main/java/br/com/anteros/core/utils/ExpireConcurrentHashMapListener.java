package br.com.anteros.core.utils;

public interface ExpireConcurrentHashMapListener<K, V> {

	public void notifyOnAdd(K key, V value);

	public void notifyOnRemoval(K key, V value);
}