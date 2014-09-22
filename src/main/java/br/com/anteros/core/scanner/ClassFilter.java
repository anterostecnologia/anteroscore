package br.com.anteros.core.scanner;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is intended to be used as a filter for class typed resources to be
 * used with {@link ClassPathScanner}. It contains multiple filtering methods
 * such as class types, class name and so on.
 * 
 */
public class ClassFilter extends ResourceFilter {
	private Set<Class<? extends Annotation>> annotations = new HashSet<Class<? extends Annotation>>();
	private Set<Class<?>> interfaces = new HashSet<Class<?>>();
	private Set<Class<?>> superClasses = new HashSet<Class<?>>();
	private boolean interfaceOnly = false;
	private boolean annotationOnly = false;
	private boolean enumOnly = false;
	private boolean classOnly = false;
	private boolean joinAnnotationsWithOr = true;
	private boolean joinInterfacesWithOr = true;

	public boolean accept(Object subject) {
		if (!super.accept(subject))
			return false;
		if (!filterable(subject))
			return true;
		if (subject instanceof Class) {
			Class<?> clazz = (Class<?>) subject;
			// Check Type
			if ((isInterfaceOnly() && !clazz.isInterface()) || (isAnnotationOnly() && !clazz.isAnnotation())
					|| (isEnumOnly() && !clazz.isEnum())
					|| (isClassOnly() && (clazz.isAnnotation() || clazz.isEnum() || clazz.isInterface()))) {
				return false;
			}
			// Check Annotations
			if (annotations.size() > 0) {
				boolean annotationFound = false;
				for (Class<? extends Annotation> annotation : annotations) {
					if (!clazz.isAnnotationPresent(annotation)) {
						if (!joinAnnotationsWithOr)
							return false;
					} else {
						annotationFound = true;
						if (joinAnnotationsWithOr) {
							break;
						}
					}
				}
				if (!annotationFound) {
					return false;
				}
			}
			// Check Interfaces
			if (interfaces.size() > 0) {
				boolean interfaceFound = false;
				for (Class<?> definedInterface : clazz.getInterfaces()) {
					for (Class<?> acceptableInterface : interfaces) {
						if (!acceptableInterface.isAssignableFrom(definedInterface)) {
							if (!joinInterfacesWithOr)
								return false;
						} else {
							interfaceFound = true;
							if (joinInterfacesWithOr) {
								break;
							}
						}
					}
					if (interfaceFound)
						break;
				}

				if (!interfaceFound) {
					return false;
				}
			}
			// Check Supertypes
			if (superClasses.size() > 0) {
				Class<?> definedSuperClass = clazz.getSuperclass();
				boolean superClassFound = false;
				for (Class<?> acceptableSuperClass : superClasses) {
					if (acceptableSuperClass.isAssignableFrom(definedSuperClass)) {
						superClassFound = true;
						break;
					}
				}
				if (!superClassFound) {
					return false;
				}
			}

			// Default, all filters validated
			return true;
		} else {
			return true;
		}
	}

	public boolean filterable(Object subject) {
		return (subject != null && (subject instanceof RootedURL || subject instanceof URL || subject instanceof Class));
	}

	/*
	 * Builder Methods
	 */

	public ClassFilter packageName(String name) {
		return (ClassFilter) super.packageName(name);
	}

	public ClassFilter packages(String[] pcks) {
		for (String p : pcks)
			super.packageName(p.trim());
		return this;
	}

	public ClassFilter resourceName(String name) {
		return (ClassFilter) super.resourceName(name);
	}

	public ClassFilter archiveName(String name) {
		return (ClassFilter) super.archiveName(name);
	}

	public ClassFilter directoryName(String name) {
		return (ClassFilter) super.directoryName(name);
	}

	@Override
	public ClassFilter scanArchives(boolean scan) {
		return (ClassFilter) super.scanArchives(scan);
	}

	@Override
	public ClassFilter className(String name) {
		return (ClassFilter) super.className(name);
	}

	/**
	 * Appends the given annotation to set of annotations to control scanned
	 * classes. The default behavior of this filter, joins all annotations with
	 * logical 'or' operator which means that any class that any given
	 * annotation presented will be accepted by this filter.If you need filter
	 * classes that all annotations are presented, you need to call
	 * {@link #joinAnnotationsWithAnd()}.
	 * 
	 * @param annotation
	 *            that to be added to this filter
	 * @return this
	 */
	public ClassFilter annotation(Class<? extends Annotation> annotation) {
		annotations.add(annotation);
		return this;
	}

	/**
	 * Appends the given interface to set of interfaces to control scanned
	 * classes. The default behavior of this filter, joins all annotations with
	 * logical 'or' operator which means that any class that any given interface
	 * implemented will be accepted by this filter.If you need to filter classes
	 * that all interfaces are implemented, you need to call
	 * {@link #joinInterfacesWithAnd()}.
	 * 
	 * @param interfaceClass
	 *            that to be added to this filter
	 * @return this
	 */
	public ClassFilter interfaceClass(Class<?> interfaceClass) {
		if (!interfaceClass.isInterface()) {
			throw new IllegalArgumentException(interfaceClass.getName() + " not an interface!");
		}
		interfaces.add(interfaceClass);
		return this;
	}

	/**
	 * Appends the given class to set of super classes. Scanned classes will be
	 * filtered out by this filter if any one of the given super classes
	 * extended
	 * 
	 * @param superClass
	 *            class to be added to set of super classes
	 * @return this
	 */
	public ClassFilter superClass(Class<?> superClass) {
		superClasses.add(superClass);
		return this;
	}

	/**
	 * Changes the join behavior of annotations. This is the default behavior
	 * 
	 * @return this
	 */
	public ClassFilter joinAnnotationsWithOr() {
		joinAnnotationsWithOr = true;
		return this;
	}

	/**
	 * Changes the join behavior of interfaces. This is the default behavior
	 * 
	 * @return this
	 */
	public ClassFilter joinInterfacesWithOr() {
		joinInterfacesWithOr = true;
		return this;
	}

	/**
	 * Changes the join behavior of annotations.
	 * 
	 * @return this
	 */
	public ClassFilter joinAnnotationsWithAnd() {
		joinAnnotationsWithOr = false;
		return this;
	}

	/**
	 * Changes the join behavior of interfaces.
	 * 
	 * @return this
	 */
	public ClassFilter joinInterfacesWithAnd() {
		joinInterfacesWithOr = false;
		return this;
	}

	/**
	 * Resets all type filtering options and accepts all classes
	 * 
	 * @return this
	 */
	public ClassFilter allTypes() {
		interfaceOnly = false;
		annotationOnly = false;
		enumOnly = false;
		classOnly = false;
		return this;
	}

	/**
	 * Filters interfaces only and erases the other type options
	 * 
	 * @return this
	 */
	public ClassFilter interfaceOnly() {
		allTypes();
		this.interfaceOnly = true;
		return this;
	}

	/**
	 * Filters annotations only and erases the other type options
	 * 
	 * @return this
	 */
	public ClassFilter annotationOnly() {
		allTypes();
		this.annotationOnly = true;
		return this;
	}

	/**
	 * Filters enums only and erases the other type options
	 * 
	 * @return this
	 */
	public ClassFilter enumOnly() {
		allTypes();
		this.enumOnly = true;
		return this;
	}

	/**
	 * Filters classes only and erases the other type options
	 * 
	 * @return this
	 */
	public ClassFilter classOnly() {
		allTypes();
		this.classOnly = true;
		return this;
	}

	/*
	 * Other Methods
	 */

	/**
	 * tells if the interface only option is set
	 */
	public boolean isInterfaceOnly() {
		return interfaceOnly;
	}

	/**
	 * tells if the annotation only option is set
	 */
	public boolean isAnnotationOnly() {
		return annotationOnly;
	}

	/**
	 * tells if the enum only option is set
	 */
	public boolean isEnumOnly() {
		return enumOnly;
	}

	/**
	 * tells if the class only option is set
	 */
	public boolean isClassOnly() {
		return classOnly;
	}

	@Override
	public String toString() {
		return "ClassFilter [annotations=" + annotations + ", interfaces=" + interfaces + ", superClasses="
				+ superClasses + ", interfaceOnly=" + interfaceOnly + ", annotationOnly=" + annotationOnly
				+ ", enumOnly=" + enumOnly + ", classOnly=" + classOnly + ", joinAnnotationsWithOr="
				+ joinAnnotationsWithOr + ", joinInterfacesWithOr=" + joinInterfacesWithOr + "]";
	}

}
