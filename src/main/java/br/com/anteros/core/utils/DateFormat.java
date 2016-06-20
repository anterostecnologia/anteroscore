package br.com.anteros.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * Formatadores de data thread-safe. Para uma mesma categoria, os formatadores
 * atualmente são diferenciados pelos seus separadores ("formato_separador")
 * exemplos:
 * <ul>
 * <li>DDMMYY <code>default:ddMMyy</code></li>
 * <li>DDMMYY_B <code>barr:dd/MM/yy</code></li>
 * <li>DDMMYY_H <code>hyphen:dd-MM-yy</code></li>
 * <li>DDMMYY_U <code>underline:dd_MM_yy</code></li>
 * <li>HHMMSS_C <code>colon:"hh:mm:ss"</code></li>
 * <li>etc.</li>
 * </ul>
 * </p>
 * 
 * @author <a href=http://gilmatryx.googlepages.com/>Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public enum DateFormat implements Format<Date, SimpleDateFormat>{

	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"ddMMyy"</tt>.
	 * </p>
	 */
	DDMMYY("ddMMyy"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"dd/MM/yy"</tt>.
	 * </p>
	 */
	DDMMYY_B("dd/MM/yy"), 
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"dd-MM-yy"</tt>.
	 * </p>
	 */
	DDMMYY_H("dd-MM-yy"), 
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"dd_MM_yy"</tt>.
	 * </p>
	 */
	DDMMYY_U("dd_MM_yy"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"ddMMyyyy"</tt>.
	 * </p>
	 */
	DDMMYYYY("ddMMyyyy"), 
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"dd/MM/yyyy"</tt>.
	 * </p>
	 */
	DDMMYYYY_B("dd/MM/yyyy"), 
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"dd-MM-yyyy"</tt>.
	 * </p>
	 */
	DDMMYYYY_H("dd-MM-yyyy"), 
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"dd_MM_yyyy"</tt>.
	 * </p>
	 */
	DDMMYYYY_U("dd_MM_yyyy"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"yyMMdd"</tt>.
	 * </p>
	 */
	YYMMDD("yyMMdd"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"yy/MM/dd"</tt>.
	 * </p>
	 */
	YYMMDD_B("yy/MM/dd"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"yy/MM/dd"</tt>.
	 * </p>
	 */
	YYMMDD_H("yy-MM-dd"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"yy_MM_dd"</tt>.
	 * </p>
	 */
	YYMMDD_U("yy_MM_dd"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"yyyyMMdd"</tt>.
	 * </p>
	 */
	YYYYMMDD("yyyyMMdd"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"yyyy/MM/dd"</tt>.
	 * </p>
	 */
	YYYYMMDD_B("yyyy/MM/dd"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"yyyy-MM-dd"</tt>.
	 * </p>
	 */
	YYYYMMDD_H("yyyy-MM-dd"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"yyyy_MM_dd"</tt>.
	 * </p>
	 */
	YYYYMMDD_U("yyyy_MM_dd"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"hhmmss"</tt>.
	 * </p>
	 */
	HHMMSS("hhmmss"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"HHmmss"</tt>.
	 * </p>
	 */
	HHMMSS_24("HHmmss"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"hh:mm:ss"</tt>.
	 * </p>
	 */
	HHMMSS_C("hh:mm:ss"),
	
	/**
	 * <p>
	 * Formatador de datas no padrão <tt>"HH:mm:ss"</tt>.
	 * </p>
	 */
	HHMMSS_24C("HH:mm:ss"),
	;
	
	private final ThreadLocalFormat<SimpleDateFormat> DATE_FORMAT;

	private DateFormat(String format) {
	
		DATE_FORMAT = new ThreadLocalFormat<SimpleDateFormat>(format){

			@Override
			protected SimpleDateFormat initialValue() {				
		       return new SimpleDateFormat(format);
			}
	        
	    };
	}

	public String format(Date obj) {	
		return DATE_FORMAT.get().format(obj);
	}
	
	public Date parse(String text) {
		try {
			return DATE_FORMAT.get().parse(text);
		} catch (ParseException e) {
			throw new IllegalArgumentException("DateFormat Exception!", e);
		}
	}
	
	public SimpleDateFormat copy(){
			
		return (SimpleDateFormat) DATE_FORMAT.get().clone();
	}
}
