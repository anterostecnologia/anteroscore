package br.com.anteros.core.utils;

import java.text.ParseException;
import java.util.Formattable;
/**
 * <p>
 * Formatadores thread-safe relacionados a moeda e números decimais.
 * </p>
 * 
 */
public enum DecimalFormat implements Format<Number, java.text.DecimalFormat>{

	/**
	 * <p>
	 * Formatador de decimais pt-BR para <code>Float,Double,BigDecimal</code> no padrão
	 * <tt>"#,##0.0"</tt>. Exemplo: <tt>"1.500,9"</tt>.
	 * </p>
	 */
	NUMBER_D_BR("#,##0.0", DecimalFormatSymbols.BRASIL.copy()),
	
	/**
	 * <p>
	 * Formatador de decimais pt-BR para <code>Float,Double,BigDecimal</code> no padrão
	 * <tt>"#,##0.00"</tt>. Exemplo: <tt>"1.500,99"</tt>.
	 * </p>
	 */
	NUMBER_DD_BR("#,##0.00", DecimalFormatSymbols.BRASIL.copy()),
	
	/**
	 * <p>
	 * Formatador de decimais <code>Float,Double,BigDecimal</code> no padrão de
	 * moeda (Real pt-BR): <tt>"R$ #,##0.0"</tt>. Usado para formatar valores
	 * monetários em real com o prefixo R$, exemplo: R$ 10,0.
	 * </p>
	 */
	MONEY_D_BR("\u00A4 #,##0.0", DecimalFormatSymbols.BRASIL.copy()),
	
	/**
	 * <p>
	 * Formatador de decimais <code>Float,Double,BigDecimal</code> no padrão de
	 * moeda (Real pt-BR): <tt>"R$ #,##0.00"</tt>. Usado para formatar valores
	 * monetários em real com o prefixo R$, exemplo: R$ 10,00.
	 * </p>
	 */
	MONEY_DD_BR("\u00A4 #,##0.00", DecimalFormatSymbols.BRASIL.copy()),
	
	;
	
	private final ThreadLocalLocalizedFormat<java.text.DecimalFormat, java.text.DecimalFormatSymbols> DECIMAL_FORMAT;

	private DecimalFormat(String format, java.text.DecimalFormatSymbols formatSymbols) {
	
		DECIMAL_FORMAT = new ThreadLocalLocalizedFormat<java.text.DecimalFormat, java.text.DecimalFormatSymbols>(format, formatSymbols){

			@Override
			protected java.text.DecimalFormat initialValue() {
				
				return new java.text.DecimalFormat(format, formatSymbols);
			}
	    };
	}

	public String format(Number obj) {
	
		return DECIMAL_FORMAT.get().format(obj);
	}
	
	public Number parse(String text) {
		try {
			return DECIMAL_FORMAT.get().parse(text);
		} catch (ParseException e) {
			throw new IllegalArgumentException("DecimalFormat Exception!", e);
		}
	}
	
	public java.text.DecimalFormat copy(){
		
		return (java.text.DecimalFormat) DECIMAL_FORMAT.get().clone();
	}
}