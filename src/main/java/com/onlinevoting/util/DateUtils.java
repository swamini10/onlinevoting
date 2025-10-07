package com.onlinevoting.util;

import java.sql.Date;

public class DateUtils {

    public static boolean isDObValid(Date dob) {

        if (dob == null) {
            return false;
        }

        Date currentDate = new Date(System.currentTimeMillis());
        long age = (currentDate.getTime() - dob.getTime()) / (1000L * 60 * 60 * 24 * 365);

     if (age >= 18) {
            return true;
        } else {
            return false;
        }
    }    

}
