package com.jinan.utils;

import java.sql.Timestamp;
import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public final class DateUtils {

  /**
   * ISO8601-like pattern for date-time. It does not support timezone.
   * <tt>yyyy-MM-ddTHH:mm:ss</tt>
   */
  public static final String ISO8601_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

  /**
   * ISO8601-like pattern for time. <tt>HH:mm:ss</tt>
   */
  public static final String ISO8601_TIME_PATTERN = "HH:mm:ss";

  public static final DateFormat DATE_HEADER_FORMAT = new SimpleDateFormat(
      "EEE, dd MMM yyyy HH:mm:ss ", Locale.US);

  private static final MessageFormat MINUTE_SECONDS = new MessageFormat(
      "{0}{1}");

  private static final double[] LIMITS = { 0, 1, 2 };

  private static final String[] MINUTES_PART = { "", "1 minute ",
      "{0,number} minutes " };

  private static final String[] SECONDS_PART = { "0 seconds", "1 second",
      "{1,number} seconds" };

  private static final ChoiceFormat MINUTES_FORMAT = new ChoiceFormat(LIMITS,
      MINUTES_PART);

  private static final ChoiceFormat SECONDS_FORMAT = new ChoiceFormat(LIMITS,
      SECONDS_PART);

  public static final String YEAR = "YY";

  public static final String MONTH = "MM";

  public static final String DATE = "DD";

  public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";

  public static final String CONVERSE_DATETIME_PATTERN = "dd-MM-yy";

  public static final String ISO_DATE_FORMAT = "yyyyMMdd";

  /**
   * 24小时制 yyyy-MM-dd HH:mm:ss
   */
  public static String DATETIME_PATTERN24H = "yyyy-MM-dd HH:mm:ss";

  /**
   * 24小时制 yyyyMMddHHmmss
   */
  public static String DATETIMECONTACTPATTER = "yyyyMMddHHmmss";

  /**
   * 12小时制 yyyy-MM-dd hh:mm:ss
   */
  public static String DATETIME_PATTERN12H = "yyyy-MM-dd hh:mm:ss";

  /**
   * Default lenient setting for getDate.
   */
  private static boolean LENIENT_DATE = false;

  static {
    MINUTE_SECONDS.setFormat(0, MINUTES_FORMAT);
    MINUTE_SECONDS.setFormat(1, SECONDS_FORMAT);
  }

  private DateUtils() {
  }

  /**
   * 把yyyy-mm-dd的时间字符串转换成dd-mm-yyyy格式的时间字符串
   */
  public static String getConverseDateString(String dateString) {

    return stringToString(dateString, ISO_EXPANDED_DATE_FORMAT,
        CONVERSE_DATETIME_PATTERN);
  }

  /**
   * Returns the days between two dates. Positive values indicate that the
   * second date is after the first, and negative values indicate, well, the
   * opposite. Relying on specific times is problematic.
   * 
   * @param early
   *          the "first date"
   * @param late
   *          the "second date"
   * @return the days between the two dates
   */
  public static final int daysBetween(Date early, Date late) {

    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();
    c1.setTime(early);
    c2.setTime(late);

    return daysBetween(c1, c2);
  }

  /**
   * Returns the days between two dates. Positive values indicate that the
   * second date is after the first, and negative values indicate, well, the
   * opposite.
   * 
   * @param early
   * @param late
   * @return the days between two dates.
   */
  public static final int daysBetween(Calendar early, Calendar late) {

    return (int) (toJulian(late) - toJulian(early));
  }

  /**
   * Return a Julian date based on the input parameter. This is based from
   * calculations found at <a
   * href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day
   * Calculations (Gregorian Calendar) </a>, provided by Bill Jeffrys.
   * 
   * @param c
   *          a calendar instance
   * @return the julian day number
   */
  public static final float toJulian(Calendar c) {

    int Y = c.get(Calendar.YEAR);
    int M = c.get(Calendar.MONTH);
    int D = c.get(Calendar.DATE);
    int A = Y / 100;
    int B = A / 4;
    int C = 2 - A + B;
    float E = (int) (365.25f * (Y + 4716));
    float F = (int) (30.6001f * (M + 1));
    float JD = C + D + E + F - 1524.5f;

    return JD;
  }

  /**
   * 日期增加
   * 
   * @param isoString
   *          日期字符串
   * @param fmt
   *          格式
   * @param field
   *          年/月/日 Calendar.YEAR/Calendar.MONTH/Calendar.DATE
   * @param amount
   *          增加数量
   * @return
   * @throws ParseException
   */
  public static final String dateIncrease(String isoString, String fmt,
      int field, int amount) {

    try {
      Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
      cal.setTime(stringToDate(isoString, fmt, true));
      cal.add(field, amount);

      return dateToString(cal.getTime(), fmt);

    } catch (Exception ex) {
      return null;
    }
  }

  /**
   * Time Field Rolling function. Rolls (up/down) a single unit of time on the
   * given time field.
   * 
   * @param isoString
   * @param field
   *          the time field.
   * @param up
   *          Indicates if rolling up or rolling down the field value.
   * @param expanded
   *          use formating char's
   * @exception ParseException
   *              if an unknown field value is given.
   */
  public static final String roll(String isoString, String fmt, int field,
      boolean up) throws ParseException {

    Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
    cal.setTime(stringToDate(isoString, fmt));
    cal.roll(field, up);

    return dateToString(cal.getTime(), fmt);
  }

  /**
   * Time Field Rolling function. Rolls (up/down) a single unit of time on the
   * given time field.
   * 
   * @param isoString
   * @param field
   *          the time field.
   * @param up
   *          Indicates if rolling up or rolling down the field value.
   * @exception ParseException
   *              if an unknown field value is given.
   */
  public static final String roll(String isoString, int field, boolean up)
      throws ParseException {

    return roll(isoString, DATETIME_PATTERN24H, field, up);
  }

  /**
   * 字符串转换为日期java.util.Date
   * 
   * @param dateText
   *          字符串
   * @param format
   *          日期格式
   * @param lenient
   *          日期越界标志
   * @return
   */
  public static Date stringToDate(String dateText, String format,
      boolean lenient) {

    if (dateText == null || dateText.equals("")) {
      return null;
    }

    DateFormat df = null;

    try {

      if (format == null) {
        df = new SimpleDateFormat();
      } else {
        df = new SimpleDateFormat(format);
      }

      // setLenient avoids allowing dates like 9/32/2001
      // which would otherwise parse to 10/2/2001
      df.setLenient(false);

      return df.parse(dateText);
    } catch (ParseException e) {

      return null;
    }
  }

  /**
   * 字符串转换为日期java.util.Date
   * 
   * @param dateText
   *          字符串
   * @param format
   *          日期格式
   * @return
   */
  public static Date stringToDate(String dateString, String format) {

    return stringToDate(dateString, format, LENIENT_DATE);
  }

  /**
   * 字符串转换为日期java.util.Date
   * 
   * @param dateText
   *          字符串
   */
  public static Date stringToDate(String dateString) {

    return stringToDate(dateString, ISO_EXPANDED_DATE_FORMAT, LENIENT_DATE);
  }

  /**
   * 根据时间变量返回时间字符串
   * 
   * @return 返回时间字符串
   * @param pattern
   *          时间字符串样式
   * @param date
   *          时间变量
   */
  public static String dateToString(Date date, String pattern) {

    if (date == null) {

      return null;
    }

    try {

      SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
      sfDate.setLenient(false);

      return sfDate.format(date);
    } catch (Exception e) {

      return null;
    }
  }

  /**
   * 根据时间变量返回时间字符串 yyyy-MM-dd
   * 
   * @param date
   * @return
   */
  public static String dateToString(Date date) {
    return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
  }

  /**
   * 返回当前时间
   * 
   * @return 返回当前时间
   */
  public static Date getCurrentDateTime() {
    java.util.Calendar calNow = java.util.Calendar.getInstance();
    java.util.Date dtNow = calNow.getTime();

    return dtNow;
  }

  /**
   * 返回当前日期字符串
   * 
   * @param pattern
   *          日期字符串样式
   * @return
   */
  public static String getCurrentDateString(String pattern) {
    return dateToString(getCurrentDateTime(), pattern);
  }

  /**
   * 返回当前日期字符串 yyyy-MM-dd
   * 
   * @return
   */
  public static String getCurrentDateString() {
    return dateToString(getCurrentDateTime(), ISO_EXPANDED_DATE_FORMAT);
  }

  /**
   * 返回当前日期+时间字符串 yyyy-MM-dd hh:mm:ss
   * 
   * @param date
   * @return
   */
  public static String dateToStringWithTime(Date date) {

    return dateToString(date, DATETIME_PATTERN24H);
  }

  /**
   * 日期增加-按日增加
   * 
   * @param date
   * @param days
   * @return java.util.Date
   */
  public static Date dateIncreaseByDay(Date date, int days) {

    Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
    cal.setTime(date);
    cal.add(Calendar.DATE, days);
    return cal.getTime();
  }

  /**
   * 日期增加-按分钟增加
   * 
   * @param date
   * @param minutes
   * @return
   */
  public static Date dateIncreaseByMinute(Date date, int minutes) {

    Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
    cal.setTime(date);
    cal.add(Calendar.MINUTE, minutes);
    return cal.getTime();
  }

  /**
   * 日期增加-按月增加
   * 
   * @param date
   * @param days
   * @return java.util.Date
   */
  public static Date dateIncreaseByMonth(Date date, int mnt) {

    Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
    cal.setTime(date);
    cal.add(Calendar.MONTH, mnt);

    return cal.getTime();
  }

  /**
   * 日期增加-按年增加
   * 
   * @param date
   * @param mnt
   * @return java.util.Date
   */
  public static Date dateIncreaseByYear(Date date, int mnt) {

    Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
    cal.setTime(date);
    cal.add(Calendar.YEAR, mnt);

    return cal.getTime();
  }

  /**
   * 日期增加
   * 
   * @param date
   *          日期字符串 yyyy-MM-dd
   * @param days
   * @return 日期字符串 yyyy-MM-dd
   */
  public static String dateIncreaseByDay(String date, int days) {
    return dateIncreaseByDay(date, ISO_DATE_FORMAT, days);
  }

  /**
   * 日期增加
   * 
   * @param date
   *          日期字符串
   * @param fmt
   *          日期格式
   * @param days
   * @return
   */
  public static String dateIncreaseByDay(String date, String fmt, int days) {
    return dateIncrease(date, fmt, Calendar.DATE, days);
  }

  /**
   * 日期字符串格式转换
   * 
   * @param src
   *          日期字符串
   * @param srcfmt
   *          源日期格式
   * @param desfmt
   *          目标日期格式
   * @return
   */
  public static String stringToString(String src, String srcfmt, String desfmt) {
    return dateToString(stringToDate(src, srcfmt), desfmt);
  }

  /**
   * Format a date/time into a specific pattern.
   * 
   * @param date
   *          the date to format expressed in milliseconds.
   * @param pattern
   *          the pattern to use to format the date.
   * @return the formatted date.
   */
  public static String format(long date, String pattern) {
    return format(new Date(date), pattern);
  }

  /**
   * Format a date/time into a specific pattern.
   * 
   * @param date
   *          the date to format expressed in milliseconds.
   * @param pattern
   *          the pattern to use to format the date.
   * @return the formatted date.
   */
  public static String format(Date date, String pattern) {
    if (date == null)
      return null;
    DateFormat df = createDateFormat(pattern);
    return df.format(date);
  }

  /**
   * return a lenient date format set to GMT time zone.
   * 
   * @param pattern
   *          the pattern used for date/time formatting.
   * @return the configured format for this pattern.
   */
  private static DateFormat createDateFormat(String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf;
  }

  /**
   * Parse a string as a datetime using the ISO8601_DATETIME format which is
   * <code>yyyy-MM-dd'T'HH:mm:ss</code>
   * 
   * @param datestr
   *          string to be parsed
   * 
   * @return a java.util.Date object as parsed by the format.
   * @exception ParseException
   *              if the supplied string cannot be parsed by this pattern.
   * @since Ant 1.6
   */
  public static Date parseIso8601DateTime(String datestr) throws ParseException {
    return new SimpleDateFormat(ISO8601_DATETIME_PATTERN).parse(datestr);
  }

  /**
   * Parse a string as a date using the ISO8601_DATE format which is
   * <code>yyyy-MM-dd</code>
   * 
   * @param datestr
   *          string to be parsed
   * 
   * @return a java.util.Date object as parsed by the format.
   * @exception ParseException
   *              if the supplied string cannot be parsed by this pattern.
   * @since Ant 1.6
   */
  public static Date parseIso8601Date(String datestr) throws ParseException {
    return new SimpleDateFormat(ISO_EXPANDED_DATE_FORMAT).parse(datestr);
  }

  /**
   * Parse a string as a date using the either the ISO8601_DATETIME or
   * ISO8601_DATE formats.
   * 
   * @param datestr
   *          string to be parsed
   * 
   * @return a java.util.Date object as parsed by the formats.
   * @exception ParseException
   *              if the supplied string cannot be parsed by either of these
   *              patterns.
   * @since Ant 1.6
   */
  public static Date parseIso8601DateTimeOrDate(String datestr)
      throws ParseException {
    try {
      return parseIso8601DateTime(datestr);
    } catch (ParseException px) {
      return parseIso8601Date(datestr);
    }
  }

  /**
   * 将一个日期格式字符串按照指定格式转换为日期对象。比如：YYYY.MM.DD HH-MI-SS
   * 不推荐使用，如果使用必须保证日期格式和format各个字符位置一样
   * 
   * @param dateStr
   * @param format
   * @deprecated replace with {@link DateUtils#format(String, String, Class)}}
   */
  public static Date format(String dateStr, String format) {
    Calendar cld = Calendar.getInstance();
    int year = 0, month = 0, day = 0, hour = 0, minute = 0, second = 0;
    format = format.toUpperCase();
    int offset;
    String substr;
    if (format.matches(".*YYYY.*")) {
      offset = format.indexOf("YYYY");
      substr = dateStr.substring(offset, offset + 4).trim();
      if (substr.matches("^[0-9]+$"))
        year = Integer.parseInt(substr);
    }
    if (format.matches(".*MM.*")) {
      offset = format.indexOf("MM");
      substr = dateStr.substring(offset, offset + 2).trim();
      if (substr.matches("^[0-9]+$"))
        month = Integer.parseInt(substr);
    }
    if (format.matches(".*DD.*")) {
      offset = format.indexOf("DD");
      substr = dateStr.substring(offset, offset + 2).trim();
      if (substr.matches("^[0-9]+$"))
        day = Integer.parseInt(substr);
    }
    if (format.matches(".*HH.*")) {
      offset = format.indexOf("HH");
      substr = dateStr.substring(offset, offset + 2).trim();
      if (substr.matches("^[0-9]+$"))
        hour = Integer.parseInt(substr);
    }
    if (format.matches(".*MI.*")) {
      offset = format.indexOf("MI");
      substr = dateStr.substring(offset, offset + 2).trim();
      if (substr.matches("^[0-9]+$"))
        minute = Integer.parseInt(substr);
    }
    if (format.matches(".*SS.*")) {
      offset = format.indexOf("SS");
      substr = dateStr.substring(offset, offset + 2).trim();
      if (substr.matches("^[0-9]+$"))
        second = Integer.parseInt(substr);
    }
    cld.set(year, month > 1 ? (month - 1) : 0, day, hour, minute, second);
    return cld.getTime();
  }

  /**
   * 将一个日期格式字符串按照指定格式转换为日期对象。比如：{@link DateUtils#LOCAL_DATE_FORMAT}
   * 
   * @param dateStr
   * @param format
   * @param dateClass
   * @return
   */
  public static Date format(String dateStr, String format, Class<?> dateClass) {
    Date date = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      date = dateFormat.parse(dateStr);
      if (dateClass == java.sql.Date.class)
        date = new java.sql.Date(date.getTime());
      if (dateClass == java.sql.Timestamp.class)
        date = new Timestamp(date.getTime());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return date;
  }

  /**
   * 将Date转化为sqlDate
   * 
   * @param date
   * @return
   */
  public static java.sql.Date tranSqlDate(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    return new java.sql.Date(cal.getTimeInMillis());
  }

  /**
   * 获取当前日期
   * 
   * @return
   */
  public static Date getCurrentDate() {
    Calendar cld = Calendar.getInstance();
    return cld.getTime();
  }

  /**
   * 日期计算
   * 
   * @param date
   * @param field
   * @param i
   * @return
   */
  public static Date calcDate(Date date, int field, int i) {
    Calendar cal = Calendar.getInstance();
    if (date != null) {
      cal.setTime(date);
    }
    cal.set(field, cal.get(field) + i);
    return cal.getTime();
  }

  public static String secToTime(long time) {
    String timeStr = null;
    long hour = 0;
    long minute = 0;
    long second = 0;
    if (time <= 0)
      return "00:00";
    else {
      minute = time / 60;
      if (minute < 60) {
        second = time % 60;
        timeStr = unitFormat(minute) + "分钟" + unitFormat(second) + "秒";
      } else {
        hour = minute / 60;
        if (hour > 99)
          return "99:59:59";
        minute = minute % 60;
        second = time - hour * 3600 - minute * 60;
        timeStr = unitFormat(hour) + "小时" + unitFormat(minute) + "分钟"
            + unitFormat(second) + "秒";
      }
    }
    return timeStr;
  }

  public static String unitFormat(long i) {
    String retStr = null;
    if (i >= 0 && i < 10)
      retStr = "0" + Long.toString(i);
    else
      retStr = "" + i;
    return retStr;
  }
  
}
