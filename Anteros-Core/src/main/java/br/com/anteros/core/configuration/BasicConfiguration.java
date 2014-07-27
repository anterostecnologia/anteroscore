package br.com.anteros.core.configuration;

import java.io.InputStream;

import br.com.anteros.core.configuration.exception.AnterosConfigurationException;

public interface BasicConfiguration {

	public BasicConfiguration configure() throws AnterosConfigurationException;

	public BasicConfiguration configure(String xmlFile) throws AnterosConfigurationException;
	
	public BasicConfiguration configure(InputStream is) throws AnterosConfigurationException;
	
}
