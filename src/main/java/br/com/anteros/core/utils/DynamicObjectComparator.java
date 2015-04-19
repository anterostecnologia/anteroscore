package br.com.anteros.core.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

public class DynamicObjectComparator {
	
	private DynamicObjectComparator(){
		
	}

	public static int compareTo(Object source, Object target){
		if ((source == null) && (target == null))
			return 0;
		
		if ((source == null) && (target != null))
			return -1;
		
		if ((source != null) && (target == null))
			return 1;
		
		if (source instanceof String) {
			return ((String) source).compareTo((String) target);
		} else if (source instanceof Integer) {
			return ((Integer) source).compareTo((Integer) target);
		} else if (source instanceof Double) {
			return ((Double) source).compareTo((Double) target);
		} else if (source instanceof Long) {
			return ((Long) source).compareTo((Long) target);
		} else if (source instanceof Short) {
			return ((Short) source).compareTo((Short) target);
		} else if (source instanceof Boolean) {
			return ((Boolean) source).compareTo((Boolean) target);
		} else if (source instanceof Character) {
			return ((Character) source).compareTo((Character) target);
		} else if (source instanceof Float) {
			return ((Float) source).compareTo((Float) target);
		} else if (source instanceof Byte) {
			return ((Byte) source).compareTo((Byte) target);
		} else if (source instanceof BigInteger) {
			return ((BigInteger) source).compareTo((BigInteger) target);
		} else if (source instanceof BigDecimal) {
			return ((BigDecimal) source).compareTo((BigDecimal) target);	
		} else if (source instanceof Date) {
			return ((Date) source).compareTo((Date) target);		
		} else if (source instanceof Enum) {
			return ((Enum) source).compareTo((Enum) target);
		} else if (source instanceof byte[]) {
			return (Arrays.equals((byte[]) source, (byte[]) target) == true ? 0 : -1);
		} else {
			if (source.getClass() != target.getClass())
				return -1;
			Field[] allDeclaredFields = ReflectionUtils.getAllDeclaredFields(source.getClass());
			for (Field field : allDeclaredFields){
				try {
					Object sourceValue = field.get(source);
					Object targetValue = field.get(target);
					int result = DynamicObjectComparator.compareTo(sourceValue, targetValue);
					if (result!=0)
						return result;
				} catch (Exception e) {
					return -1;
				}				
			}
			return 0;
		}
	}

}
