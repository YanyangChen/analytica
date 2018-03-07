package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Update {
    public static Integer flagsecs = 1518019200; //feb 8 2018
    public static Integer todaysecs = 0;
    public static Integer startsecs = 0;
    public static void getUpdate(){
        todaysecs = countTodayMins();
    }

    public static Integer countTodayMins () {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

        Date date = null;
        try {
            date = sdf.parse("08022018");

            cal1.setTime(date);
            //get today
            cal2.setTime(new Date());
            return flagsecs + daysBetween(cal1.getTime(), cal2.getTime()) * 60 * 24 * 60;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
        public static void countStartsecs(String startdate)
        {
            Calendar cal1 = new GregorianCalendar();
            Calendar cal2 = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date date = null;

            try {
                date = sdf.parse("08022018");
                cal1.setTime(date);
                Date date2 = sdf.parse(startdate);
                cal2.setTime(date2);
                int between = daysBetween(cal2.getTime(), cal1.getTime()) ;
//                System.out.println("between " + between);
//                System.out.println("flagsecs " + flagsecs);
//                System.out.println("(between *  24 * 60) " + (between * 24 * 60 *));
//                System.out.println("flagsecs - (between * 3600 * 24 * 60) " + (flagsecs - (between * 24 * 60)));
                startsecs = flagsecs - (between *  24 * 60 * 60);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }


    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}
