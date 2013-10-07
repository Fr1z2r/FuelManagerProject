package ru.fr1z2r.fuelmanager;

/**
 * Created by Fr1z2r on 23.09.13.
 */
public class Car {
    public int Id;
    public String Vendor;
    public String Model;
    public String Name;
    public Car(int Id,String Vendor,String Model,String Name)
    {
        this.Id=Id;
        this.Vendor=Vendor;
        this.Model=Model;
        this.Name=Name;
    }
    public Car()
    {

    }

    @Override
    public String toString()
    {
        return Vendor+" "+Model+": "+Name;
    }

}
