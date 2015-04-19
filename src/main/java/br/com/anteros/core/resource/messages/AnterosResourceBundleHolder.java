package br.com.anteros.core.resource.messages;

import java.util.Locale;

public class AnterosResourceBundleHolder {

	private String holder;
	private Locale locale;

	public AnterosResourceBundleHolder(String holder, Locale locale) {
		this.holder = holder;
		this.locale = locale;
	}

	public String getHolder() {
		return holder;
	}

	public Locale getLocale() {
		return locale;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((holder == null) ? 0 : holder.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnterosResourceBundleHolder other = (AnterosResourceBundleHolder) obj;
		if (holder == null) {
			if (other.holder != null)
				return false;
		} else if (!holder.equals(other.holder))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}

}
