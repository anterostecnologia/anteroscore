package br.com.anteros.core.resource.messages;

import java.util.Enumeration;

public interface AnterosBundle {

	public String getMessage(String key);
	
	public String getMessage(String key, Object... parameters);
	
	public abstract Enumeration<String> getKeys();


}
