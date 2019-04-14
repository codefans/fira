package com.codefans.util;

import com.codefans.domain.DateInterval;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: codefans
 * @Date: 2019-04-14 0:51
 */

public class CalculateEngineUtil {

    /**
     * @Title: generateDateIntervalByLast
     * @Description: 一次性付清（后收）
     * @param grantDate 放款日期
     * @param expiredDate 到期日
     */
    public static List<DateInterval> generateDateIntervalByLast(Date grantDate, Date expiredDate ){
        List<DateInterval> list = new ArrayList<DateInterval>();
        DateInterval dateInterval = new DateInterval();
        dateInterval.setPeriod(1);
        dateInterval.setStartDate(grantDate);
        dateInterval.setEndDate(DateUtils.getDateAfter(expiredDate,-1));
        dateInterval.setRepaymentDate(expiredDate);
        list.add(dateInterval);
        return list;

    }
    /**
     * @Title: generateDateIntervalByMonth
     * @Description: 按月（后收）
     * @param repaymentDay 还款日
     * @param grantDate  放款日期
     * @param expiredDate 到期日
     */
    public static List<DateInterval> generateDateIntervalByMonth(int repaymentDay,Date grantDate,Date expiredDate) throws ParseException {
        List<DateInterval> list = new ArrayList<DateInterval>();
//        Date lenderTime = grantDate;
//        Date maturiyTime = expiredDate;
        Date startTime =  (Date) grantDate.clone();
        //定义起始日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(grantDate);
        //放款日
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        //放款年
        int startYear = calendar.get(Calendar.YEAR);
        //放款月
        int startMonth = calendar.get(Calendar.MONTH)+1;
        //放款月最大天数
        int amountDay = DateUtils.maxDateOfMonth(calendar);
        //放款日期在还款日之后，且还款日是小于当月最大日期
        if((startDay<repaymentDay)&&(repaymentDay<amountDay)){
            //定义第一个还款日
            String repayTimeStr = startYear+"-"+startMonth+"-"+repaymentDay;
            //初始化还款日（在当月）
            Date repayTime = DateUtils.parseYYYYMMDD(repayTimeStr);
            int i = 1;
            //初始化起始月份
            int month = calendar.get(Calendar.MONTH)+1;
            int year = calendar.get(Calendar.YEAR);
            Date startTimeNew = startTime;
            while(true){
                if(repayTime.before(expiredDate)){
                    String repayDateStr = DateUtils.formatYYYYMMDD(repayTime);
                    System.out.println("----------还款日为："+repayDateStr);
                    Date RepaymentDate = (Date) repayTime.clone();
                    DateInterval dateInterval = new DateInterval();
                    dateInterval.setPeriod(1);
                    dateInterval.setStartDate(startTimeNew);
                    dateInterval.setEndDate(DateUtils.getDateAfter(RepaymentDate,-1));
                    dateInterval.setRepaymentDate(RepaymentDate);
                    list.add(dateInterval);
                    //下一步是定义下一个还款日（此时就需要考虑是否跨年是否大于下个月数的最大天数）
                    //发现当前月份已经是12月份了
                    startTimeNew = (Date) repayTime.clone();
                    if(month==12){
                        //初始化月份为1
                        month = 1;
                        //年份加1
                        year = year+1;
                        Date newDate = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+"01");
                        //定义起始日期
                        Calendar calendarNew = Calendar.getInstance();
                        calendarNew.setTime(newDate);
                        int maxDay = DateUtils.maxDateOfMonth(calendarNew);
                        if(repaymentDay<=maxDay){
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+repaymentDay);
                        }else {
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+maxDay);
                        }
                    }else {
                        //初始化月份为1
                        month = month+1;
                        Date newDate = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+"01");
                        //定义起始日期
                        Calendar calendarNew = Calendar.getInstance();
                        calendarNew.setTime(newDate);
                        int maxDay = DateUtils.maxDateOfMonth(calendarNew);
                        if(repaymentDay<=maxDay){
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+repaymentDay);
                        }else {
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+maxDay);
                        }
                    }

                }else {
                    DateInterval dateIntervalBeanOther = new DateInterval();
                    dateIntervalBeanOther.setPeriod(i);
                    dateIntervalBeanOther.setStartDate(startTimeNew);
                    dateIntervalBeanOther.setEndDate(DateUtils.getDateAfter(expiredDate,-1));
                    dateIntervalBeanOther.setRepaymentDate(expiredDate);
                    list.add(dateIntervalBeanOther);
                    break;
                }
                i=i+1;
            }
        }else if((repaymentDay>=amountDay)&&(repaymentDay>startDay)){
            //定义第一个还款日
            String repayTimeStr = startYear+"-"+startMonth+"-"+amountDay;
            //初始化还款日（在当月）
            Date repayTime = DateUtils.parseYYYYMMDD(repayTimeStr);
            //初始化起始月份
            int month = calendar.get(Calendar.MONTH)+1;
            int year = calendar.get(Calendar.YEAR);
            int i = 1;
            Date startTimeNew = startTime;
            while(true){
                if(repayTime.before(expiredDate)){
                    Date RepaymentDate = (Date) repayTime.clone();
                    String repayDateStr = DateUtils.formatYYYYMMDD(repayTime);
                    System.out.println("----------还款日为："+repayDateStr);
                    DateInterval dateInterval = new DateInterval();
                    dateInterval.setPeriod(1);
                    dateInterval.setStartDate(startTimeNew);
                    dateInterval.setEndDate(DateUtils.getDateAfter(RepaymentDate,-1));
                    dateInterval.setRepaymentDate(RepaymentDate);
                    list.add(dateInterval);
                    startTimeNew = (Date) repayTime.clone();
                    //下一步是定义下一个还款日（此时就需要考虑是否跨年是否大于下个月数的最大天数）
                    //发现当前月份已经是12月份了
                    if(month==12){
                        //初始化月份为1
                        month = 1;
                        //年份加1
                        year = year+1;
                        Date newDate = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+"01");
                        //定义起始日期
                        Calendar calendarNew = Calendar.getInstance();
                        calendarNew.setTime(newDate);
                        int maxDay = DateUtils.maxDateOfMonth(calendarNew);
                        if(repaymentDay<=maxDay){
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+repaymentDay);
                        }else {
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+maxDay);
                        }
                    }else {
                        //初始化月份为1
                        month = month+1;
                        Date newDate = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+"01");
                        //定义起始日期
                        Calendar calendarNew = Calendar.getInstance();
                        calendarNew.setTime(newDate);
                        int maxDay = DateUtils.maxDateOfMonth(calendarNew);
                        if(repaymentDay<=maxDay){
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+repaymentDay);
                        }else {
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+maxDay);
                        }
                    }

                }else {
                    DateInterval dateIntervalBeanOther = new DateInterval();
                    dateIntervalBeanOther.setPeriod(i);
                    dateIntervalBeanOther.setStartDate(startTimeNew);
                    dateIntervalBeanOther.setEndDate(DateUtils.getDateAfter(expiredDate,-1));
                    dateIntervalBeanOther.setRepaymentDate(expiredDate);
                    list.add(dateIntervalBeanOther);
                    break;
                }
                i=i+1;
            }
        }else if((startDay>=repaymentDay)){
            //初始化起始月份
            int month = calendar.get(Calendar.MONTH)+1;
            int year = calendar.get(Calendar.YEAR);
            int i = 1;
            Date startTimeNew = startTime;
            //定义第一个还款日
            Date repayTime = null;
            if(month==12){
                //初始化月份为1
                month = 1;
                //年份加1
                year = year+1;
                Date newDate = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+"01");
                //定义起始日期
                Calendar calendarNew = Calendar.getInstance();
                calendarNew.setTime(newDate);
                int maxDay = DateUtils.maxDateOfMonth(calendarNew);
                if(repaymentDay<=maxDay){
                    repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+repaymentDay);
                }else {
                    repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+maxDay);
                }
            }else {
                //初始化月份为1
                month = month+1;
                Date newDate = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+"01");
                //定义起始日期
                Calendar calendarNew = Calendar.getInstance();
                calendarNew.setTime(newDate);
                int maxDay = DateUtils.maxDateOfMonth(calendarNew);
                if(repaymentDay<=maxDay){
                    repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+repaymentDay);
                }else {
                    repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+maxDay);
                }
            }
            while(true){
                if(repayTime.before(expiredDate)){
                    String repayDateStr = DateUtils.formatYYYYMMDD(repayTime);
                    System.out.println("----------还款日为："+repayDateStr);
                    Date RepaymentDate = (Date) repayTime.clone();
                    DateInterval dateInterval = new DateInterval();
                    dateInterval.setPeriod(1);
                    dateInterval.setStartDate(startTimeNew);
                    dateInterval.setEndDate(DateUtils.getDateAfter(RepaymentDate,-1));
                    dateInterval.setRepaymentDate(RepaymentDate);
                    list.add(dateInterval);
                    startTimeNew = (Date) repayTime.clone();
                    //下一步是定义下一个还款日（此时就需要考虑是否跨年是否大于下个月数的最大天数）
                    //发现当前月份已经是12月份了
                    if(month==12){
                        //初始化月份为1
                        month = 1;
                        //年份加1
                        year = year+1;
                        Date newDate = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+"01");
                        //定义起始日期
                        Calendar calendarNew = Calendar.getInstance();
                        calendarNew.setTime(newDate);
                        int maxDay = DateUtils.maxDateOfMonth(calendarNew);
                        if(repaymentDay<=maxDay){
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+repaymentDay);
                        }else {
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+maxDay);
                        }
                    }else {
                        //初始化月份为1
                        month = month+1;
                        Date newDate = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+"01");
                        //定义起始日期
                        Calendar calendarNew = Calendar.getInstance();
                        calendarNew.setTime(newDate);
                        int maxDay = DateUtils.maxDateOfMonth(calendarNew);
                        if(repaymentDay<=maxDay){
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+repaymentDay);
                        }else {
                            repayTime = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+maxDay);
                        }
                    }

                }else {
                    DateInterval dateIntervalBeanOther = new DateInterval();
                    dateIntervalBeanOther.setPeriod(i);
                    dateIntervalBeanOther.setStartDate(startTimeNew);
                    dateIntervalBeanOther.setEndDate(DateUtils.getDateAfter(expiredDate,-1));
                    dateIntervalBeanOther.setRepaymentDate(expiredDate);
                    list.add(dateIntervalBeanOther);
                    break;
                }
                i=i+1;
            }
        }

        return list;
    }

    //按季收（后收）
    /**
     * 每个季度最后一个月还款日还款(3、6、9、12月）
     * @param repaymentDay
     * @param lenderDate
     * @param maturityDate
     * @return
     */
    public static List<DateInterval> generateDateIntervalBySeason(int repaymentDay,Date lenderDate,Date maturityDate) throws ParseException {
        List<DateInterval> list = new ArrayList<DateInterval>();
        Date lenderTime = lenderDate;
        Date maturiyTime = maturityDate;
        //定义起始日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lenderTime);
        //放款日
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        //放款年
        int startYear = calendar.get(Calendar.YEAR);
        //放款月
        int startMonth = calendar.get(Calendar.MONTH)+1;
        //放款月最大天数
        int amountDay = DateUtils.maxDateOfMonth(calendar);

        //初始化起始月份
        int month = startMonth;
        int year = startYear;

        //还款日在放款日之前
        if (startDay >= repaymentDay) {
            month++;
        } else {
            if (startDay < getMaxRepaymentDay(amountDay, repaymentDay)) {
                //
            } else {
                month++;
            }
        }

        if (month > 12) {
            year++;
            month = 3;
        }
        month = getLastMonthOfSeason(month);
        //定义第一个还款日
        Date repayTime = DateUtils.parseYYYYMMDD(
                year+"-"+month+"-"+getMaxRepaymentDay(year, month, repaymentDay));

        int i = 1;
        Date startTime =  (Date) lenderTime.clone();
        while(true){
            if (repayTime.before(maturiyTime)) {
                Date repaymentDate = (Date) repayTime.clone();
                list.add(generateDateInterval(i, startTime, repaymentDate));

                startTime = (Date) repayTime.clone();
                //下一步是定义下一个还款日（此时就需要考虑是否跨年是否大于下个月数的最大天数）
                //发现当前月份已经是12月份了
                month += 3;
                if (month > 12) {
                    year++;
                    month = 3;
                }
                repayTime = DateUtils.parseYYYYMMDD(
                        year+"-"+month+"-"+getMaxRepaymentDay(year, month, repaymentDay));

                i++;
            }else {
                list.add(generateDateInterval(i, startTime, maturiyTime));
                break;
            }
        }
        return list;
    }

    private static DateInterval generateDateInterval(int period, Date startTime, Date repaymentDate) {
        DateInterval dateInterval = new DateInterval();
        dateInterval.setPeriod(period);
        dateInterval.setStartDate(startTime);
        dateInterval.setEndDate(DateUtils.getDateAfter(repaymentDate,-1));
        dateInterval.setRepaymentDate(repaymentDate);
        return dateInterval;
    }

    private static int getMaxRepaymentDay(int year, int month, int repaymentDay) throws ParseException {
        Date newDate = DateUtils.parseYYYYMMDD(year+"-"+month+"-"+"01");
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.setTime(newDate);
        int maxDay = DateUtils.maxDateOfMonth(calendarNew);
        return getMaxRepaymentDay(maxDay, repaymentDay);
    }

    private static int getMaxRepaymentDay(int maxDay, int repaymentDay) {
        return repaymentDay <= maxDay ? repaymentDay : maxDay;
    }

    private static int getLastMonthOfSeason(int month) {
        switch(month) {
            case 1:
            case 2:
            case 3: return 3;
            case 4:
            case 5:
            case 6: return 6;
            case 7:
            case 8:
            case 9: return 9;
            case 10:
            case 11:
            case 12: return 12;
            default: return 3;
        }
    }

    //按月（先收）
    //按季（先收）


    /**
     * 按月分期，还款日等于放款日
     * @param lenderDate
     * @param periods
     * @return
     */
    public static List<DateInterval> generateDateIntervalByMonth(Date lenderDate, int periods) throws ParseException {
        List<DateInterval> list = new ArrayList<DateInterval>();
        Date lenderTime = lenderDate;

        //每期开始日期
        Date startTime =  (Date) lenderTime.clone();

        //定义起始日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lenderTime);
        //放款日
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);

        //初始化起始月份
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        for (int i=0; i<periods; i++) {
            //生成还款月
            if (month == 12) {
                year += 1;
                month = 1;
            } else {
                month += 1;
            }
            String repayMonthStr = year+"-"+month+"-01";
            Date repayMonth = DateUtils.parseYYYYMMDD(repayMonthStr);
            calendar.setTime(repayMonth);
            /**
             * 每月最大天数
             * 例如
             * 2019-02月最大天数为28天
             * 2019-03月最大天数为31天
             * 2019-04月最大天数为30天
             */
            int maxDay = DateUtils.maxDateOfMonth(calendar);

            String repayDateStr = year+"-"+month+"-"+ (maxDay<startDay ? maxDay : startDay);
            Date repayDate = DateUtils.parseYYYYMMDD(repayDateStr);

            DateInterval dateInterval = new DateInterval();
            dateInterval.setPeriod(i+1);
            dateInterval.setStartDate(startTime);
            dateInterval.setEndDate(DateUtils.getDateAfter(repayDate,-1));
            dateInterval.setRepaymentDate(repayDate);
            list.add(dateInterval);

            startTime = repayDate;
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            System.out.println(generateDateIntervalBySeason(22,
                    DateUtils.parseYYYYMMDD("2019-03-21"), DateUtils.parseYYYYMMDD("2019-10-21")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }




}
