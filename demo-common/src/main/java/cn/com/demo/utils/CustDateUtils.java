package cn.com.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: code4fun
 * @date: 2018/8/13:上午11:59
 */
public class CustDateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat();

    /**
     *
     * @param partten
     * @param date
     * @return
     */
    public static String getFormat(String partten, Date date){
        sdf.applyPattern(partten);
        return sdf.format(date);
    }
}
