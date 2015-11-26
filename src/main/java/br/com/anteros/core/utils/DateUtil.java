/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.anteros.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 
 * @author Edson Martins
 */
public class DateUtil {

	public static int SUNDAY = Calendar.SUNDAY;
	public static int MONDAY = Calendar.MONDAY;
	public static int TUESDAY = Calendar.TUESDAY;
	public static int WEDNESDAY = Calendar.WEDNESDAY;
	public static int THURSDAY = Calendar.THURSDAY;
	public static int FRIDAY = Calendar.FRIDAY;
	public static int SATURDAY = Calendar.SATURDAY;

	public static final int DATE = 1;
	public static final int TIME = 2;
	public static final int DATE_TIME = 3;

	private static final int MODIFY_TRUNCATE = 0;
	private static final int MODIFY_ROUND = 1;
	private static final int MODIFY_CEILING = 2;

	public static final int SEMI_MONTH = 1001;

	private static final int[][] fields = { { Calendar.MILLISECOND },
			{ Calendar.SECOND }, { Calendar.MINUTE },
			{ Calendar.HOUR_OF_DAY, Calendar.HOUR },
			{ Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM
			/*
			 * Calendar.DAY_OF_YEAR, Calendar.DAY_OF_WEEK,
			 * Calendar.DAY_OF_WEEK_IN_MONTH
			 */
			}, { Calendar.MONTH, DateUtil.SEMI_MONTH }, { Calendar.YEAR },
			{ Calendar.ERA } };

	public static String getCurrentTimeHMS() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-03:00");
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String
						.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)))
				+ ":"
				+ (calendario.get(Calendar.SECOND) >= 10 ? String
						.valueOf(calendario.get(Calendar.SECOND)) : "0"
						+ String.valueOf(calendario.get(Calendar.SECOND)));
		return horas;
	}

	public static String getCurrentTimeHM() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-03:00");
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String
						.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)));
		return horas;
	}

	public static String getCurrentDateDMA() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-03:00");
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String data = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH))
				+ '/' + String.valueOf(calendario.get(Calendar.MONTH) + 1)
				+ '/' + String.valueOf(calendario.get(Calendar.YEAR));
		return data;
	}

	public static String getCurrentDateAMD() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-03:00");
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String data = String.valueOf(calendario.get(Calendar.YEAR)) + '/'
				+ String.valueOf(calendario.get(Calendar.MONTH) + 1) + '/'
				+ String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
		return data;
	}

	public static String getCurrentTimeHMS(TimeZone timeZone) {
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String
						.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)))
				+ ":"
				+ (calendario.get(Calendar.SECOND) >= 10 ? String
						.valueOf(calendario.get(Calendar.SECOND)) : "0"
						+ String.valueOf(calendario.get(Calendar.SECOND)));
		return horas;
	}

	public static String getCurrentTimeHM(TimeZone timeZone) {
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String
						.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)));
		return horas;
	}

	public static String getCurrentDateDMA(TimeZone timeZone) {
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String data = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH))
				+ '/' + String.valueOf(calendario.get(Calendar.MONTH) + 1)
				+ '/' + String.valueOf(calendario.get(Calendar.YEAR));
		return data;
	}

	public static String getCurrentDateAMD(TimeZone timeZone) {
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String data = String.valueOf(calendario.get(Calendar.YEAR)) + '/'
				+ String.valueOf(calendario.get(Calendar.MONTH) + 1) + '/'
				+ String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
		return data;
	}

	public static String toStringTimeHMS(Date date) {
		if (date == null)
			return "";
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String
						.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)))
				+ ":"
				+ (calendario.get(Calendar.SECOND) >= 10 ? String
						.valueOf(calendario.get(Calendar.SECOND)) : "0"
						+ String.valueOf(calendario.get(Calendar.SECOND)));
		return horas;
	}

	public static String toStringTimeHM(Date date) {
		if (date == null)
			return "";
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String
						.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)));
		return horas;
	}

	public static String toStringDateDMA(Date date) {
		if (date == null)
			return "";
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		String data = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH))
				+ '/' + String.valueOf(calendario.get(Calendar.MONTH) + 1)
				+ '/' + String.valueOf(calendario.get(Calendar.YEAR));
		return data;
	}

	public static String toStringDateAMD(Date date) {
		if (date == null)
			return "";
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		String data = String.valueOf(calendario.get(Calendar.YEAR)) + '/'
				+ String.valueOf(calendario.get(Calendar.MONTH) + 1) + '/'
				+ String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
		return data;
	}

	public static Date stringToDate(String dateString, int type) {

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		String stDate = dateString;
		String stTime = null;
		if (dateString.indexOf(" ") > 0) {
			stDate = new String(
					dateString.substring(0, dateString.indexOf(" ")));
			stTime = new String(
					dateString.substring(dateString.indexOf(" ") + 1));
		}

		String[] sDt = split(stDate, '/');
		String[] sTm = null;
		if (stTime != null) {
			sTm = split(stTime, ':');
		}

		if ((type & DATE) != 0) {
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sDt[0]));
			calendar.set(Calendar.MONTH, Integer.parseInt(sDt[1]) - 1
					+ Calendar.JANUARY);
			calendar.set(Calendar.YEAR, Integer.parseInt(sDt[2]));

			if (type != DATE_TIME || dateString.length() < 11) {
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				return calendar.getTime();
			}
			dateString = new String(dateString.substring(11));
		} else {
			calendar.setTime(new Date(0));
		}

		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sTm[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(sTm[1]));
		if (sTm.length > 2) {
			int seconds = Integer.parseInt(sTm[2]);
			calendar.set(Calendar.SECOND, seconds);
		}
		calendar.set(Calendar.MILLISECOND, 0);

		int pos = 8;
		if (pos < dateString.length() && dateString.charAt(pos) == '.') {
			int ms = 0;
			int f = 100;
			while (true) {
				char d = dateString.charAt(++pos);
				if (d < '0' || d > '9')
					break;
				ms += (d - '0') * f;
				f /= 10;
			}
			calendar.set(Calendar.MILLISECOND, ms);
		} else {
			calendar.set(Calendar.MILLISECOND, 0);
		}

		if (pos < dateString.length()) {

			if (dateString.charAt(pos) == '+' || dateString.charAt(pos) == '-') {
				calendar.setTimeZone(TimeZone.getTimeZone("GMT"
						+ new String(dateString.substring(pos))));
			} else if (dateString.charAt(pos) == 'Z') {
				calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
			} else {
				throw new RuntimeException("illegal time format!");
			}
		}

		return calendar.getTime();
	}

	public static Date stringToDateTime(String dateString) {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-03:00");
		Calendar calendar = Calendar.getInstance(timeZone);
		try {
			String stDate = dateString;
			String stTime = null;
			if (dateString.indexOf(" ") > 0) {
				stDate = new String(dateString.substring(0,
						dateString.indexOf(" ")));
				stTime = new String(dateString.substring(dateString
						.indexOf(" ") + 1));
			}

			String[] sDt = split(stDate, '/');
			String[] sTm = null;
			if (stTime != null) {
				sTm = split(stTime, ':');
			}
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sDt[0]));
			calendar.set(Calendar.MONTH, Integer.parseInt(sDt[1]) - 1
					+ Calendar.JANUARY);
			calendar.set(Calendar.YEAR, Integer.parseInt(sDt[2]));
			if (sTm != null) {
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sTm[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(sTm[1]));
				if (sTm.length > 2) {
					int seconds = Integer.parseInt(sTm[2]);
					calendar.set(Calendar.SECOND, seconds);
				}
				calendar.set(Calendar.MILLISECOND, 0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			calendar.set(Calendar.DAY_OF_MONTH, 01);
			calendar.set(Calendar.MONTH, 01);
			calendar.set(Calendar.YEAR, 1900);
			calendar.set(Calendar.HOUR_OF_DAY, 00);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			calendar.set(Calendar.MILLISECOND, 0);
		}

		return calendar.getTime();
	}

	public static String[] split(String str, char c) {
		String string = str + c;
		String[] result = null;
		String subStr = null;
		int last = 0;
		int search = searchOf(string, c);

		result = new String[search];

		for (int i = 0; i < search; i++) {
			subStr = new String(string.substring(last, string.length()));
			result[i] = new String(subStr.substring(0, subStr.indexOf(c)));
			last += subStr.indexOf(c) + 1;
		}

		string = null;
		subStr = null;

		return result;
	}

	public static int searchOf(String string, char c) {
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}

	public static String toStringDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date stringToDate(String string, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Calcula a diferença entre duas horas no formato HH:mm:ss
	 * 
	 * @param time
	 * @param time2
	 * @return
	 */
	public static long getIntervalInMilliseconds(String strTime, String strTime2) {
		return getIntervalInMilliseconds(stringTimeToDate(strTime),
				stringTimeToDate(strTime2));
	}

	public static long getIntervalInMilliseconds(Date time, Date time2) {
		if (time.after(time2)) {
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(time2);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			time2 = cal.getTime();
		}
		return time2.getTime() - time.getTime();
	}

	public static double getIntervalInMinutes(String strTime, String strTime2) {
		return (double) getIntervalInMilliseconds(strTime, strTime2) / 1000d / 60d;
	}

	public static double getIntervalInMinutes(Date time, Date time2) {
		return (double) getIntervalInMilliseconds(time, time2) / 1000d / 60d;
	}

	public static double getIntervalInHours(String strTime, String strTime2) {
		return getIntervalInMinutes(strTime, strTime2) / 60d;
	}

	public static double getIntervalInHours(Date time, Date time2) {
		return getIntervalInMinutes(time, time2) / 60d;
	}

	/**
	 * Faz o parse de String para Date usando o padrão HH:mm:ss
	 * 
	 * @param strTime
	 * @return
	 */
	public static Date stringTimeToDate(String strTime) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		try {
			return timeFormat.parse(strTime);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static void resetTimeStamp(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	public static void resetDate(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.YEAR, 1970);
	}

	/**
	 * Copia hora, minuto, segundo e milésimo de segundo de um calendario para
	 * outro
	 */
	public static void copyTimeStamp(Calendar from, Calendar to) {
		to.set(Calendar.HOUR_OF_DAY, from.get(Calendar.HOUR_OF_DAY));
		to.set(Calendar.MINUTE, from.get(Calendar.MINUTE));
		to.set(Calendar.SECOND, from.get(Calendar.SECOND));
		to.set(Calendar.MILLISECOND, from.get(Calendar.MILLISECOND));
	}

	/**
	 * Copia hora, minuto, segundo e milésimo de segundo de um calendario para
	 * outro
	 */
	public static void copyDate(Calendar from, Calendar to) {
		to.set(Calendar.DAY_OF_MONTH, from.get(Calendar.DAY_OF_MONTH));
		to.set(Calendar.MONTH, from.get(Calendar.MONTH));
		to.set(Calendar.YEAR, from.get(Calendar.YEAR));
	}

	/**
	 * Copia hora, minuto, segundo e milésimo de segundo de uma data para outro
	 */
	public static Date copyTimeStamp(Date from, Date to) {
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(from);
		Calendar cTo = Calendar.getInstance();
		cTo.setTime(to);
		copyTimeStamp(cFrom, cTo);
		return cTo.getTime();
	}

	/*
	 * Compara dois calendários de acordo com o tipo de field informado
	 */
	public static int truncatedCompareTo(final Calendar cal1,
			final Calendar cal2, final int field) {
		final Calendar truncatedCal1 = truncate(cal1, field);
		final Calendar truncatedCal2 = truncate(cal2, field);
		return truncatedCal1.compareTo(truncatedCal2);
	}

	/*
	 * Compara duas datas de acordo com o tipo de field informado
	 */
	public static int truncatedCompareTo(final Date date1, final Date date2,
			final int field) {
		final Date truncatedDate1 = truncate(date1, field);
		final Date truncatedDate2 = truncate(date2, field);
		return truncatedDate1.compareTo(truncatedDate2);
	}

	/*
	 * Trunca uma data, deixando o campo especificado como o campo mais
	 * significativo
	 */
	public static Date truncate(final Date date, final int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		final Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, MODIFY_TRUNCATE);
		return gval.getTime();

	}

	/*
	 * Trunca uma calendário, deixando o campo especificado como o campo mais
	 * significativa
	 */
	public static Calendar truncate(final Calendar date, final int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		final Calendar truncated = (Calendar) date.clone();
		modify(truncated, field, MODIFY_TRUNCATE);
		return truncated;
	}

	/*
	 * Trunca um objeto, deixando o campo especificado como o campo mais
	 * significativa
	 */
	public static Date truncate(final Object date, final int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (date instanceof Date) {
			return truncate((Date) date, field);
		} else if (date instanceof Calendar) {
			return truncate((Calendar) date, field).getTime();
		} else {
			throw new ClassCastException("Could not truncate " + date);
		}
	}

	/*
	 * Determina se dois calendários são iguais até não mais do que o campo mais
	 * significativo especificado.
	 */
	public static boolean truncatedEquals(final Calendar cal1,
			final Calendar cal2, final int field) {
		return truncatedCompareTo(cal1, cal2, field) == 0;
	}

	/*
	 * Determina se duas datas são iguais até não mais do que o campo mais
	 * significativo especificado.
	 */
	public static boolean truncatedEquals(final Date date1, final Date date2,
			final int field) {
		return truncatedCompareTo(date1, date2, field) == 0;
	}

	/*
	 * Determina se uma data está entre um período informado
	 */
	public static boolean dateBetween(final Date date, final Date datePeriod1,
			final Date datePeriod2) {
		if (date != null && datePeriod1 != null && datePeriod2 != null)
			return date.compareTo(datePeriod1) >= 0
					&& date.compareTo(datePeriod2) <= 0;
		return false;
	}

	public static boolean dateBetween(final Calendar date,
			final Calendar datePeriod1, final Calendar datePeriod2) {
		if (date != null && datePeriod1 != null && datePeriod2 != null)
			return dateBetween(date.getTime(), datePeriod1.getTime(),
					datePeriod2.getTime());
		return false;
	}

	/**
	 * <p>
	 * Internal calculation method.
	 * </p>
	 * 
	 * @param val
	 *            the calendar, not null
	 * @param field
	 *            the field constant
	 * @param modType
	 *            type to truncate, round or ceiling
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	private static void modify(final Calendar val, final int field,
			final int modType) {
		if (val.get(Calendar.YEAR) > 280000000) {
			throw new ArithmeticException(
					"Calendar value too large for accurate calculations");
		}

		if (field == Calendar.MILLISECOND) {
			return;
		}

		// ----------------- Fix for LANG-59 ---------------------- START
		// ---------------
		// see http://issues.apache.org/jira/browse/LANG-59
		//
		// Manually truncate milliseconds, seconds and minutes, rather than
		// using
		// Calendar methods.

		final Date date = val.getTime();
		long time = date.getTime();
		boolean done = false;

		// truncate milliseconds
		final int millisecs = val.get(Calendar.MILLISECOND);
		if (MODIFY_TRUNCATE == modType || millisecs < 500) {
			time = time - millisecs;
		}
		if (field == Calendar.SECOND) {
			done = true;
		}

		// truncate seconds
		final int seconds = val.get(Calendar.SECOND);
		if (!done && (MODIFY_TRUNCATE == modType || seconds < 30)) {
			time = time - (seconds * 1000L);
		}
		if (field == Calendar.MINUTE) {
			done = true;
		}

		// truncate minutes
		final int minutes = val.get(Calendar.MINUTE);
		if (!done && (MODIFY_TRUNCATE == modType || minutes < 30)) {
			time = time - (minutes * 60000L);
		}

		// reset time
		if (date.getTime() != time) {
			date.setTime(time);
			val.setTime(date);
		}
		// ----------------- Fix for LANG-59 ----------------------- END
		// ----------------

		boolean roundUp = false;
		for (final int[] aField : fields) {
			for (final int element : aField) {
				if (element == field) {
					// This is our field... we stop looping
					if (modType == MODIFY_CEILING
							|| (modType == MODIFY_ROUND && roundUp)) {
						if (field == DateUtil.SEMI_MONTH) {
							// This is a special case that's hard to generalize
							// If the date is 1, we round up to 16, otherwise
							// we subtract 15 days and add 1 month
							if (val.get(Calendar.DATE) == 1) {
								val.add(Calendar.DATE, 15);
							} else {
								val.add(Calendar.DATE, -15);
								val.add(Calendar.MONTH, 1);
							}
							// ----------------- Fix for LANG-440
							// ---------------------- START ---------------
						} else if (field == Calendar.AM_PM) {
							// This is a special case
							// If the time is 0, we round up to 12, otherwise
							// we subtract 12 hours and add 1 day
							if (val.get(Calendar.HOUR_OF_DAY) == 0) {
								val.add(Calendar.HOUR_OF_DAY, 12);
							} else {
								val.add(Calendar.HOUR_OF_DAY, -12);
								val.add(Calendar.DATE, 1);
							}
							// ----------------- Fix for LANG-440
							// ---------------------- END ---------------
						} else {
							// We need at add one to this field since the
							// last number causes us to round up
							val.add(aField[0], 1);
						}
					}
					return;
				}
			}
			// We have various fields that are not easy roundings
			int offset = 0;
			boolean offsetSet = false;
			// These are special types of fields that require different rounding
			// rules
			switch (field) {
			case DateUtil.SEMI_MONTH:
				if (aField[0] == Calendar.DATE) {
					// If we're going to drop the DATE field's value,
					// we want to do this our own way.
					// We need to subtrace 1 since the date has a minimum of 1
					offset = val.get(Calendar.DATE) - 1;
					// If we're above 15 days adjustment, that means we're in
					// the
					// bottom half of the month and should stay accordingly.
					if (offset >= 15) {
						offset -= 15;
					}
					// Record whether we're in the top or bottom half of that
					// range
					roundUp = offset > 7;
					offsetSet = true;
				}
				break;
			case Calendar.AM_PM:
				if (aField[0] == Calendar.HOUR_OF_DAY) {
					// If we're going to drop the HOUR field's value,
					// we want to do this our own way.
					offset = val.get(Calendar.HOUR_OF_DAY);
					if (offset >= 12) {
						offset -= 12;
					}
					roundUp = offset >= 6;
					offsetSet = true;
				}
				break;
			default:
				break;
			}
			if (!offsetSet) {
				final int min = val.getActualMinimum(aField[0]);
				final int max = val.getActualMaximum(aField[0]);
				// Calculate the offset from the minimum allowed value
				offset = val.get(aField[0]) - min;
				// Set roundUp if this is more than half way between the minimum
				// and maximum
				roundUp = offset > ((max - min) / 2);
			}
			// We need to remove this field
			if (offset != 0) {
				val.set(aField[0], val.get(aField[0]) - offset);
			}
		}
		throw new IllegalArgumentException("The field " + field
				+ " is not supported");

	}
	
	
	
	
}
