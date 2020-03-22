package br.com.anteros.core.utils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Uma solução de mapa de hash simultânea que armazena as chaves e os valores apenas por um período de tempo específico e expira depois disso
 * Tempo.
 * 
 * <pre>
 * 
 * // Criando um map
 * long expiryInMillis = 1 * 60 * 1000;	// 1 minute
 * WeakConcurrentHashMap&lt;String, Object&gt; map = new WeakConcurrentHashMap&lt;String, Object&gt;(expiryInMillis);
 * 
 * // Usando
 * map.put(&quot;key&quot;, valueObject);
 * Object valueObject = map.get(&quot;key&quot;);
 * 
 * // finalizar map
 * map.quitMap();
 * </pre>
 * 
 * E para verificar se o map está vivo
 * 
 * <pre>
 * if (map.isAlive()) {
 * 	// Suas operações no map
 * }
 * </pre>
 * 
 * @author Edson Martins
 *
 * @param <K>
 * @param <V>
 */
public class ExpireConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	private Map<K, Long> timeMap = new ConcurrentHashMap<K, Long>();
	private ExpireConcurrentHashMapListener<K, V> listener;
	private long expiryInMillis;
	private boolean mapAlive = true;

	public ExpireConcurrentHashMap() {
		this.expiryInMillis = 10000;
		initialize();
	}

	public ExpireConcurrentHashMap(ExpireConcurrentHashMapListener<K, V> listener) {
		this.listener = listener;
		this.expiryInMillis = 10000;
		initialize();
	}

	public ExpireConcurrentHashMap(long expiryInMillis) {
		this.expiryInMillis = expiryInMillis;
		initialize();
	}

	public ExpireConcurrentHashMap(long expiryInMillis, ExpireConcurrentHashMapListener<K, V> listener) {
		this.expiryInMillis = expiryInMillis;
		this.listener = listener;
		initialize();
	}

	void initialize() {
		new CleanerThread().start();
	}

	public void registerRemovalListener(ExpireConcurrentHashMapListener<K, V> listener) {
		this.listener = listener;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalStateException if trying to insert values into map after quiting
	 */
	@Override
	public V put(K key, V value) {
		if (!mapAlive) {
			throw new IllegalStateException("WeakConcurrent Hashmap is no more alive.. Try creating a new one.");	// No I18N
		}
		Date date = new Date();
		timeMap.put(key, date.getTime());
		V returnVal = super.put(key, value);
		if (listener != null) {
			listener.notifyOnAdd(key, value);
		}
		return returnVal;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalStateException if trying to insert values into map after quiting
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		if (!mapAlive) {
			throw new IllegalStateException("WeakConcurrent Hashmap is no more alive.. Try creating a new one.");	// No I18N
		}
		for (K key : m.keySet()) {
			put(key, m.get(key));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalStateException if trying to insert values into map after quiting
	 */
	@Override
	public V putIfAbsent(K key, V value) {
		if (!mapAlive) {
			throw new IllegalStateException("WeakConcurrent Hashmap is no more alive.. Try creating a new one.");	// No I18N
		}
		if (!containsKey(key)) {
			return put(key, value);
		} else {
			return get(key);
		}
	}

	/**
	 * Should call this method when it's no longer required
	 */
	public void quitMap() {
		mapAlive = false;
	}

	public boolean isAlive() {
		return mapAlive;
	}

	/**
	 * 
	 * This thread performs the cleaning operation on the concurrent hashmap once in a specified interval. This wait interval is half of the
	 * time from the expiry time.
	 * 
	 *
	 */
	class CleanerThread extends Thread {

		@Override
		public void run() {
			while (mapAlive) {
				cleanMap();
				try {
					Thread.sleep(expiryInMillis / 2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void cleanMap() {
			long currentTime = new Date().getTime();
			for (K key : timeMap.keySet()) {
				if (currentTime > (timeMap.get(key) + expiryInMillis)) {
					V value = remove(key);
					timeMap.remove(key);
					if (listener != null) {
						listener.notifyOnRemoval(key, value);
					}
				}
			}
		}
	}
}