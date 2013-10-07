package ru.fr1z2r.fuelmanager;

import android.content.res.Resources;
import android.text.format.Time;

/**
 * Created by Fr1z2r on 23.09.13.
 */
public class FuelInfo {
    public int Id;
    public int CarId;
    public String Date;
    public double FuelAdded;
    public double FuelUsed;
    public int Odometer;

    @Override
    public String toString()
    {
        return Date+ ": \n\u0422\u043e\u043f\u043b\u0438\u0432\u043d\u044b\u0439 \u0441\u0447\u0435\u0442\u0447\u0438\u043a: " +FuelUsed+"\nЗаправлено: "+FuelAdded+"\nПробег: "+Odometer;
    }
}
