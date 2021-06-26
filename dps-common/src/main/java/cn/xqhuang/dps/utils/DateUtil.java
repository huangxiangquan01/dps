package cn.xqhuang.dps.utils;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DateUtil {
    public static final String DATE_PATTERN_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_PATTERN_YYYY_MM = "yyyy-MM";
    public static final String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_HH_MM_SS = "HH:mm:ss";
    public static final String DATE_PATTERN_HH_MM = "HH:mm";
    public static final String DATE_PATTERN_MM_DD = "MM月dd日";
    public static final String DATE_PATTERN_SPECIAL_YYYY_MM_DD = "yyyy年MM月dd日";
    public static final String DATE_PATTERN_MM_DD_HH_MM = "MM月dd日HH时mm分";
    public static final long MS_OF_ONE_DAY = 86400000;

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE).format(date);
    }

    public static Date parse(String dateStr, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    /**
     * 根据默认的格式组进行解析。
     * 如果需要添加其他格式，请按精度排序。
     *
     * @param dateStr 时间字符串
     * @return Date
     */
    public static Date parse(String dateStr) {
        String[] defaultPatterns = new String[]{
                DateUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS,
                DateUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM,
                DateUtil.DATE_PATTERN_YYYY_MM_DD,
                DateUtil.DATE_PATTERN_YYYYMMDD,
                DateUtil.DATE_PATTERN_YYYY_MM,
                DateUtil.DATE_PATTERN_HH_MM_SS,
                DateUtil.DATE_PATTERN_HH_MM
        };
        return DateUtil.parse(dateStr, defaultPatterns);
    }

    /**
     * 根据一批格式对字符串进行解析。
     * 需要注意的是，该方法遍历进行解析，解析到值即刻返回。
     * 因此，请将解析精度较高的格式放在前面，否则可能会丢失精度。
     *
     * @param dateStr  时间字符串
     * @param patterns 时间格式组
     * @return Date
     */
    public static Date parse(String dateStr, String[] patterns) {
        if (dateStr != null && dateStr.length() != 0) {
            SimpleDateFormat df = new SimpleDateFormat();
            for (String pattern : patterns) {
                df.applyPattern(pattern);
                df.setLenient(false);
                ParsePosition pos = new ParsePosition(0);
                Date date = df.parse(dateStr, pos);
                if (date != null) {
                    return date;
                }
            }
        }
        return null;
    }

    /**
     * 获取指定时间范围内的天数间隔
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    public static int daysGap(Date startDate, Date endDate) {
        long gapInMillis = endDate.getTime() - startDate.getTime();
        long daysGap = gapInMillis / MS_OF_ONE_DAY;
        return Long.valueOf(daysGap).intValue();
    }

    /**
     * 获取指定日期范围内的天数间隔
     */
    public static long daysGap(final LocalDate startDate, final LocalDate endDate) {
        return endDate.toEpochDay() - startDate.toEpochDay();
    }

    /**
     * 获取指定时间范围内的分钟间隔
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    public static long minutesGap(Date startDate, Date endDate) {
        long gapInMillis = endDate.getTime() - startDate.getTime();
        long daysGap = gapInMillis / (1000 * 60);
        return Long.valueOf(daysGap).intValue();
    }

    public static Date parseDate(String dateStr, String pattern) {
        Date date = null;
        try {
            date = parse(dateStr, pattern);
        } catch (ParseException ignored) {
        }
        return date;
    }

    public static Date currentTimeOfYesterday() {
        return addDays(new Date(System.currentTimeMillis()), -1);
    }

    public static Date firstDayOfGivenDate(Date givenDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return cal.getTime();
    }

    public static Date lastDayOfGivenDate(Date givenDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public static Date specificDayOfGivenDate(Date givenDate, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return cal.getTime();
    }

    public static Date startTimeOfGivenDate(Date givenDate) {
        return setDateTime(givenDate, 0, 0, 0, 0);
    }

    public static Date endTimeOfGivenDate(Date givenDate) {
        return endTimeOfGivenDate(givenDate, 999);
    }

    public static Date endTimeOfGivenDateToLastDay(Date givenDate) {
        Date endTime = lastDayOfGivenDate(givenDate);
        return endTimeOfGivenDate(endTime, 999);
    }

    public static Date endTimeOfGivenDate(Date givenDate, int millisecond) {
        return setDateTime(givenDate, 23, 59, 59, millisecond);
    }

    public static Date addHours(Date givenDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.HOUR, amount);
        return cal.getTime();
    }

    public static Date addMinutes(Date givenDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }

    public static Date addDays(Date givenDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    public static Date addMonths(Date givenDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }

    public static Date addYears(Date givenDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        cal.add(Calendar.YEAR, amount);
        return cal.getTime();
    }

    public static int getYear(Date givenDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Date givenDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getDayOfMonth(Date givenDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取昨天的最早时间
     */
    public static Date startTimeOfYesterday() {
        return startTimeOfGivenDate(addDays(new Date(System.currentTimeMillis()), -1));
    }

    /**
     * 获取昨天的最晚时间
     */
    public static Date endTimeOfYesterday() {
        return endTimeOfGivenDate(addDays(new Date(System.currentTimeMillis()), -1));
    }

    public static Integer getBetweenDay(String dateBefore, String dateAfter) {
        Date before = null;
        Date after = null;
        try {
            before = parse(dateBefore, DATE_PATTERN_YYYY_MM_DD);
            after = parse(dateAfter, DATE_PATTERN_YYYY_MM_DD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (before == null || after == null) {
            return null;
        }
        Long date = (after.getTime() - before.getTime()) / (1000 * 60 * 60 * 24);
        return date.intValue();
    }

    public static Date startTimeOfGivenDay(Date date) {
        return setDateTime(date, 0, 0, 0, 0);
    }

    public static Date endTimeOfGivenDay(Date date) {
        return setDateTime(date, 23, 59, 59, 999);
    }

    public static Date setDateTime(Date date, int hour, int minute, int second, int millisecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, millisecond);
        return cal.getTime();
    }

    /**
     * 日期转中文
     *
     * @param dateTime 2019-05-14 12:59:00
     * @return 5月14日
     */
    public static String formatDateToZH(String dateTime) {
        Date date = parseDate(dateTime, DATE_PATTERN_YYYY_MM_DD);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH) + 1;

        int day = cal.get(Calendar.DAY_OF_MONTH);

        return month + "月" + day + "日";
    }


    public static Date getLastDayOfMonth(String time) {
        Date date = null;
        try {
            date = DateUtil.parse(time + "-01");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, last);

        return cal.getTime();

    }

    public static Date getFirstDayDateOfMonth(String time) {
        Date date = null;
        try {
            date = DateUtil.parse(time + "-01");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, last);

        return cal.getTime();

    }

    /**
     * 获取time的那个月份是起始时间
     */
    public static Date startTimeOfMonth(String time) {
        Date date = getFirstDayDateOfMonth(time);
        return setDateTime(date,0,0,0,0);
    }

    /**
     * 获取time的那个月份是结束时间
     */
    public static Date endTimeOfMonth(String time) {
        Date date = getLastDayOfMonth(time);
        return setDateTime(date,23,59,59,999);
    }

    /**
     * 获取time的周一的日期
     * @param date 传入当前日期
     */
    public static Date getStartTimeOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return setDateTime(cal.getTime(),0,0,0,0);
    }

    /**
     * 获取time的周日的日期
     * @param date 传入当前日期
     * @return
     */
    public static Date getEndTimeOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() + 6- day);
        return setDateTime(cal.getTime(),23,59,59,999);
    }

    /**
     * 获取传入日期所在年的第一天
     * @param date
     * @return
     */
    public static Date getStartTimeOfYear(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, last);
        return setDateTime(cal.getTime(),0,0,0,0);
    }

    /**
     * 获取传入日期所在年的最后一天
     * @param date
     * @return
     */
    public static Date getEndTimeOfYear(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, last);
        return setDateTime(cal.getTime(),23,59,59,999);
    }

    public static int getYearMonth(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        return year * 100 + month;//返回年份乘以100加上月份的值，因为月道份最多2位数，所以年份乘以100可以获取一个唯版一的年月数值
    }

    /**
     * 获取当前时间几个月前或几个月后时间
     *
     * @return
     */
    public static String getSeveralMonthTime(Date date, int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, month);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    /**
     * 返回星期几
     * @param date  时间
     * @return
     */
    public static String formatDateToWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        String[] arr = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        return arr[week - 1];
    }

    public static void main(String[] args) throws Exception {
//		System.out.println(getFirstDay("2019-04-03 00:00:00", 0));
//		System.out.println(getLastDay("2019-04-03 00:00:00", 0));
//		System.out.println(getBetweenDay("2019-04-01", "2019-04-30"));
//		System.out.println(getMonthFirstDay("2018-04-01", -1));
//		System.out.println(getMonthAssignDay("2019-04-01", null, 10));
//    	System.out.println(getYesterday());
        System.out.println(format(new Date(System.currentTimeMillis()), "yyyy-MM-dd-HH:mm:ss"));

        System.out.println(format(startTimeOfGivenDate(new Date(System.currentTimeMillis())), DATE_PATTERN_YYYY_MM_DD_HH_MM_SS));
        System.out.println(format(endTimeOfGivenDate(new Date(System.currentTimeMillis())), DATE_PATTERN_YYYY_MM_DD_HH_MM_SS));
        System.out.println(daysGap(startTimeOfGivenDate(new Date(System.currentTimeMillis())), addDays(endTimeOfGivenDate(new Date(System.currentTimeMillis())), 1)));

        System.out.println(startTimeOfYesterday());
        System.out.println(endTimeOfYesterday());
        System.out.println(getMonth(new Date(System.currentTimeMillis())));
        System.out.println(addDays(parseDate("2018-01-20", "yyyy-MM-dd"), -30));
        System.out.println("--------" + addMonths(parseDate("2018-07-31", "yyyy-MM-dd"), -1));

        System.out.println(specificDayOfGivenDate(new Date(System.currentTimeMillis()), 10));
//        Date date = new Date();
//        System.out.println(startTimeOfGivenDay(date));
//        System.out.println(endTimeOfGivenDay(date));
//		System.out.println(formatDateToZH("2019-05-14 12:59:00"));
//    	System.out.println(getDateAddYears(new Date(), -1));
//    	System.out.println(getDateAddMonths(new Date(), -1));
        System.out.println(getYear(new Date()));

        System.out.println(getSeveralMonthTime((new Date(System.currentTimeMillis())), -6));

        System.out.println(startTimeOfMonth(DateUtil.format(new Date(), DateUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
        System.out.println(endTimeOfMonth(DateUtil.format(new Date(), DateUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));

        System.out.println(getStartTimeOfWeek(new Date()));
        System.out.println(getEndTimeOfWeek(new Date()));

    }


    /**
     * 获取两个日期之间间隔的月份包括开始和结束时间
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<Integer>   getBetweenMonth(String beginDate, String endDate)  {
        SimpleDateFormat date = new SimpleDateFormat(DateUtil.DATE_PATTERN_YYYY_MM_DD);
        Date d1 = null;
        try {
            d1 = date.parse(beginDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = date.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int a =(c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR))*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
        String[] str = beginDate.split("-");
        String month = str[1];
        int monthnum = Integer.valueOf(month);
        List<Integer> lists = new ArrayList<Integer>();
        lists.add(monthnum);
        int t = 1;
        for(int i=1;i <a;i++){
            int num = monthnum + i;
            if(num < 13){
                lists.add(num);
            }else{
                int mnum = num-12*t;
                lists.add(mnum);
                if(mnum == 12){
                    t = t+1;
                }
            }
        }
        int start = getMonth(d1);
        int end = getMonth(d2);
        List<Integer> indexs= Lists.newArrayList();
        if (CollectionUtils.isEmpty(lists)){
            indexs.add(start);
            indexs.add(end);
        }else {
            lists.forEach(i-> indexs.add(i));
            indexs.add(end);
        }
        return indexs;
    }


}
