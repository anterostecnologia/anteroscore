package br.com.anteros.core.configuration;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "package-scan-entity")
public class PackageScanEntity {

	@Attribute(name = "package-name", required = true)
	protected String packageName;
	
	public String getPackageName() {
		return packageName;
	}

	public PackageScanEntity setPackageName(String packageName) {
		this.packageName = packageName;
		return this;
	}

}
