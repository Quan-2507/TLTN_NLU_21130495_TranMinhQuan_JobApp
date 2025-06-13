package com.company.apis.Utils;

import java.util.Calendar;
import java.util.Date;

public class HandleDate {
    public int getYear(Date date) {
        Calendar get = Calendar.getInstance();
        get.setTime(date);
        int result = get.get(Calendar.YEAR);
        return result;
    }

    public int getMonth(Date date) {
        Calendar get = Calendar.getInstance();
        get.setTime(date);
        int result = get.get(Calendar.MONTH) + 1;
        return result;

    }
}
