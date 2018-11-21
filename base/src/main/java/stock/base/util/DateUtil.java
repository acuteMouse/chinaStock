package stock.base.util;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期Util类
 *
 * @author caojun
 */
public class DateUtil {
    private static String defaultDatePattern = "yyyy-MM-dd";

    private static String DATA_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    public static String getDataTimePattern() {
        return DATA_TIME_PATTERN;
    }

    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getToday() {
        Date today = new Date();
        return format(today);
    }

    /**
     * 获取当天 0时0分0秒
     */

    public static Date getTodayZeroClock() throws ParseException {
        return new Timestamp(parse(getToday()).getTime());
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String format(Date date) {
        return date == null ? " " : format(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate) throws ParseException {
        return StringUtils.isEmpty(strDate) ? null : parse(strDate,
                getDatePattern());
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern)
            throws ParseException {
        return StringUtils.isEmpty(strDate) ? null : new SimpleDateFormat(
                pattern).parse(strDate);
    }

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加数个整天
     */
    public static Date addDate(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }
    public static Date addHoure(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, n);
        return cal.getTime();
    }
    public static Date addMinute(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }

    /**
     * 获得当月第一天
     */
    public static String getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }

    public static String getLastDayOfMonth(String year, String month) {
        Calendar cal = Calendar.getInstance();
        // 年
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        // 月，因为Calendar里的月是从0开始，所以要-1
        // cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        // 日，设为一号
        cal.set(Calendar.DATE, 1);
        // 月份加一，得到下个月的一号
        cal.add(Calendar.MONTH, 1);
        // 下一个月减一为本月最后一天
        cal.add(Calendar.DATE, -1);
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获得月末是几号
    }

    public static Date getDate(String year, String month, String day)
            throws ParseException {
        String result = year + "- "
                + (month.length() == 1 ? ("0 " + month) : month) + "- "
                + (day.length() == 1 ? ("0 " + day) : day);
        return parse(result);
    }

    /**
     * 时间转换时分秒
     *
     * @param date
     * @return
     */
    public static String getDateHms(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 由时间戳转换成时分秒
     *
     * @param applyArriveTime 时间戳   timtstamp
     * @return
     */
    public static String getDateHms(String applyArriveTime) {
        return getDateHms(new Timestamp(Long.parseLong(applyArriveTime)));

    }

    public static Timestamp getSecondDay(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.DATE, 1);
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * 把日期转成 timeStamp
     *
     * @param startDate
     * @return
     * @throws ParseException
     */

    public static Timestamp parseDateTime(String startDate) throws ParseException {
        return new Timestamp(DateUtil.parse(startDate, DATA_TIME_PATTERN).getTime());
    }

    /**
     * 把结束时间转成 timeStamp
     * 如果endDate不能为空，则直接返回该日期的timestamp
     * 如果endDate为空，则返回 startDate加一天
     *
     * @param endDate
     * @param startDate
     * @return
     * @throws ParseException
     */
    public static Timestamp getEndDay(String endDate, Timestamp startDate) throws ParseException {
        if (endDate == null) {
            return DateUtil.getSecondDay(startDate);
        }
        return new Timestamp(DateUtil.parse(endDate).getTime());
    }

    /**
     * 计算两个时间相隔多少分钟
     *
     * @param time        小的时间
     * @param greaterTime 大的时间
     * @return
     */
    public static long getTimeInterval(long time, long greaterTime) {
        return (greaterTime - time);
    }


    /**
     * 计算俩个日期想差的天数.
     *
     * @param date      Date
     * @param otherDate Date
     * @return int
     */
    public static int getIntervalDays(Date date, Date otherDate) {
        long time = Math.abs(date.getTime() - otherDate.getTime());
        return (int) time / (1000 * 3600 * 24);
    }

    public static Timestamp getNowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 判断现在是否在制定的时间段内（0-23小时）
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
