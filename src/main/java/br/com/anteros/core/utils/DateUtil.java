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

	public static int DOMINGO = Calendar.SUNDAY;
	public static int SEGUNDA = Calendar.MONDAY;
	public static int TERCA = Calendar.TUESDAY;
	public static int QUARTA = Calendar.WEDNESDAY;
	public static int QUINTA = Calendar.THURSDAY;
	public static int SEXTA = Calendar.FRIDAY;
	public static int SABADO = Calendar.SATURDAY;
	public static final int DATE = 1;
	public static final int TIME = 2;
	public static final int DATE_TIME = 3;
	public static final String PATTERN_DATA_HORA_DMAH = "dd/MM/yyyy hh:mm:ss";

	public static String getCurrentTimeHMS() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-03:00");
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)))
				+ ":"
				+ (calendario.get(Calendar.SECOND) >= 10 ? String.valueOf(calendario.get(Calendar.SECOND)) : "0"
						+ String.valueOf(calendario.get(Calendar.SECOND)));
		return horas;
	}

	public static String getCurrentTimeHM() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-03:00");
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)));
		return horas;
	}

	public static String getCurrentDateDMA() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-03:00");
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String data = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH)) + '/'
				+ String.valueOf(calendario.get(Calendar.MONTH) + 1) + '/' + String.valueOf(calendario.get(Calendar.YEAR));
		return data;
	}

	public static String getCurrentDateAMD() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-03:00");
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String data = String.valueOf(calendario.get(Calendar.YEAR)) + '/' + String.valueOf(calendario.get(Calendar.MONTH) + 1)
				+ '/' + String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
		return data;
	}

	public static String getCurrentTimeHMS(TimeZone timeZone) {
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)))
				+ ":"
				+ (calendario.get(Calendar.SECOND) >= 10 ? String.valueOf(calendario.get(Calendar.SECOND)) : "0"
						+ String.valueOf(calendario.get(Calendar.SECOND)));
		return horas;
	}

	public static String getCurrentTimeHM(TimeZone timeZone) {
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)));
		return horas;
	}

	public static String getCurrentDateDMA(TimeZone timeZone) {
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String data = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH)) + '/'
				+ String.valueOf(calendario.get(Calendar.MONTH) + 1) + '/' + String.valueOf(calendario.get(Calendar.YEAR));
		return data;
	}

	public static String getCurrentDateAMD(TimeZone timeZone) {
		Calendar calendario = Calendar.getInstance(timeZone);
		calendario.setTime(new Date());
		String data = String.valueOf(calendario.get(Calendar.YEAR)) + '/' + String.valueOf(calendario.get(Calendar.MONTH) + 1)
				+ '/' + String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
		return data;
	}

	public static String toStringTimeHMS(Date date) {
		if (date == null)
			return "";
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY))
				+ ':'
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)))
				+ ":"
				+ (calendario.get(Calendar.SECOND) >= 10 ? String.valueOf(calendario.get(Calendar.SECOND)) : "0"
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
				+ (calendario.get(Calendar.MINUTE) >= 10 ? String.valueOf(calendario.get(Calendar.MINUTE)) : "0"
						+ String.valueOf(calendario.get(Calendar.MINUTE)));
		return horas;
	}

	public static String toStringDateDMA(Date date) {
		if (date == null)
			return "";
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		String data = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH)) + '/'
				+ String.valueOf(calendario.get(Calendar.MONTH) + 1) + '/' + String.valueOf(calendario.get(Calendar.YEAR));
		return data;
	}

	public static String toStringDateAMD(Date date) {
		if (date == null)
			return "";
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		String data = String.valueOf(calendario.get(Calendar.YEAR)) + '/' + String.valueOf(calendario.get(Calendar.MONTH) + 1)
				+ '/' + String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
		return data;
	}

	public static Date stringToDate(String dateString, int type) {

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		String stDate = dateString;
		String stTime = null;
		if (dateString.indexOf(" ") > 0) {
			stDate = new String(dateString.substring(0, dateString.indexOf(" ")));
			stTime = new String(dateString.substring(dateString.indexOf(" ") + 1));
		}

		String[] sDt = split(stDate, '/');
		String[] sTm = null;
		if (stTime != null) {
			sTm = split(stTime, ':');
		}

		if ((type & DATE) != 0) {
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sDt[0]));
			calendar.set(Calendar.MONTH, Integer.parseInt(sDt[1]) - 1 + Calendar.JANUARY);
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
				calendar.setTimeZone(TimeZone.getTimeZone("GMT" + new String(dateString.substring(pos))));
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
				stDate = new String(dateString.substring(0, dateString.indexOf(" ")));
				stTime = new String(dateString.substring(dateString.indexOf(" ") + 1));
			}

			String[] sDt = split(stDate, '/');
			String[] sTm = null;
			if (stTime != null) {
				sTm = split(stTime, ':');
			}
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sDt[0]));
			calendar.set(Calendar.MONTH, Integer.parseInt(sDt[1]) - 1 + Calendar.JANUARY);
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
		return getIntervalInMilliseconds(stringTimeToDate(strTime), stringTimeToDate(strTime2));
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

}
