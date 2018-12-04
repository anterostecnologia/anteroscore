package br.com.anteros.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;



public class ConfigurableObjectInputStream extends ObjectInputStream {

	private final ClassLoader classLoader;

	private final boolean acceptProxyClasses;


	public ConfigurableObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
		this(in, classLoader, true);
	}

	public ConfigurableObjectInputStream(
			InputStream in, ClassLoader classLoader, boolean acceptProxyClasses) throws IOException {

		super(in);
		this.classLoader = classLoader;
		this.acceptProxyClasses = acceptProxyClasses;
	}


	@Override
	protected Class<?> resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
		try {
			if (this.classLoader != null) {
				// Use the specified ClassLoader to resolve local classes.
				return ClassUtils.forName(classDesc.getName(), this.classLoader);
			}
			else {
				// Use the default ClassLoader...
				return super.resolveClass(classDesc);
			}
		}
		catch (ClassNotFoundException ex) {
			return resolveFallbackIfPossible(classDesc.getName(), ex);
		}
	}

	@Override
	protected Class<?> resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
		if (!this.acceptProxyClasses) {
			throw new NotSerializableException("Not allowed to accept serialized proxy classes");
		}
		if (this.classLoader != null) {
			// Use the specified ClassLoader to resolve local proxy classes.
			Class<?>[] resolvedInterfaces = new Class<?>[interfaces.length];
			for (int i = 0; i < interfaces.length; i++) {
				try {
					resolvedInterfaces[i] = ClassUtils.forName(interfaces[i], this.classLoader);
				}
				catch (ClassNotFoundException ex) {
					resolvedInterfaces[i] = resolveFallbackIfPossible(interfaces[i], ex);
				}
			}
			try {
				return ClassUtils.createCompositeInterface(resolvedInterfaces, this.classLoader);
			}
			catch (IllegalArgumentException ex) {
				throw new ClassNotFoundException(null, ex);
			}
		}
		else {
			// Use ObjectInputStream's default ClassLoader...
			try {
				return super.resolveProxyClass(interfaces);
			}
			catch (ClassNotFoundException ex) {
				Class<?>[] resolvedInterfaces = new Class<?>[interfaces.length];
				for (int i = 0; i < interfaces.length; i++) {
					resolvedInterfaces[i] = resolveFallbackIfPossible(interfaces[i], ex);
				}
				return ClassUtils.createCompositeInterface(resolvedInterfaces, getFallbackClassLoader());
			}
		}
	}


	protected Class<?> resolveFallbackIfPossible(String className, ClassNotFoundException ex)
			throws IOException, ClassNotFoundException{

		throw ex;
	}

	protected ClassLoader getFallbackClassLoader() throws IOException {
		return null;
	}

}