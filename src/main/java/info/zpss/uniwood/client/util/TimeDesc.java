package info.zpss.uniwood.client.util;

public class TimeDesc {
    public static String getTimeDesc(long time) {
        long second = (System.currentTimeMillis() - time) / 1000;
        long minute = second / 60;
        long hour = minute / 60;
        long day = hour / 24;
        long month = day / 30;
        long year = month / 12;
        if (year > 0)
            return year + "年前";
        else if (month > 0)
            return month + "月前";
        else if (day > 0)
            return day + "天前";
        else if (hour > 0)
            return hour + "小时前";
        else if (minute > 0)
            return minute + "分钟前";
        else if (second > 0)
            return second + "秒前";
        else
            return "刚刚";
    }
}
