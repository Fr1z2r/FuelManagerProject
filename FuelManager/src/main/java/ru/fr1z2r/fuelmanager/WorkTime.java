package ru.fr1z2r.fuelmanager;

import android.text.format.Time;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Fr1z2r on 23.09.13.
 */
public class WorkTime {
    private Time startTime;
    public WorkTime()
    {
        startTime =new Time();
        startTime.setToNow();
    }
    public Time GetStartTime()
    {
        return startTime;
    }
    public int GetWorkTime()
    {
        Time timeNow=new Time();
        timeNow.setToNow();
        //timeNow-startTime;
        Calendar c=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        return c.get(Calendar.MINUTE);
    }
}
