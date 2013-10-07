package ru.fr1z2r.fuelmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Fr1z2r on 23.09.13.
 */
public class DbHelper extends SQLiteOpenHelper {
    static final String dbName="FuelManager";

    static final String CarTable="Car";
    static final String CarId="Id";
    static final String CarVendor="Vendor";
    static final String CarModel="Model";
    static final String CarName="Name";

    static final String FuelTable="FuelInfo";
    static final String FuelId="Id";
    static final String FuelCarId="CarId";
    static final String FuelDate="Date";
    static final String FuelAdded="FuelAdded";
    static final String FuelUsed="FuelUsed";
    static final String FuelOdo="Odometer";

    static final String FuelToCarTable="FuelToCar";
    static final String FuelToCarId="Id";
    static final String FuelToCarFuelId="FuelId";
    static final String FuelToCarCarId="CarId";

    public DbHelper(Context context) {
        super(context, dbName, null, 35);
        //onUpgrade(getWritableDatabase(),35,35);
    }

    public void RebuildDatabase()
    {
        onUpgrade(getWritableDatabase(),35,35);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+FuelTable+" ("+FuelId+" integer primary key autoincrement, "
                +FuelCarId+" integer not null, "+FuelDate+" text, "
        +FuelAdded+" real, "+ FuelUsed+" real, "+FuelOdo+" integer not null);");
        db.execSQL("create table "+CarTable+" ("+CarId+" integer primary key autoincrement, "+CarVendor+" text not null, "+
        CarModel+" text not null, "+CarName+" text not null);");
        InsertCars(db);
        InsertFuels(db);
        //db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("drop table if exists "+CarTable);
        sqLiteDatabase.execSQL("drop table if exists "+FuelTable);
        onCreate(sqLiteDatabase);

    }

    public void InsertCars(SQLiteDatabase db)
    {
        ContentValues cv=new ContentValues();
        cv.put(CarVendor,"Hyundai");
        cv.put(CarModel,"Solaris");
        cv.put(CarName,"Solaris");
        db.insert(CarTable,CarId,cv);
        cv.put(CarVendor,"Cadillac");
        cv.put(CarModel,"SRX");
        cv.put(CarName,"SRX");
        db.insert(CarTable,CarId,cv);

    }

    public void InsertFuels(SQLiteDatabase db)
    {
        ContentValues cv=new ContentValues();

        cv.put(FuelCarId,1);
        cv.put(FuelDate,"22.10.12");
        cv.put(FuelUsed,0);
        cv.put(FuelOdo,0);
        db.insert(FuelTable,FuelId,cv);

        cv.put(FuelCarId,1);
        cv.put(FuelDate,"22.10.12");
        cv.put(FuelUsed,1.65);
        cv.put(FuelOdo,10);
        db.insert(FuelTable,FuelId,cv);
    }
    public String[] GetCarNames()
    {
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cur=db.query(CarTable,new String[]{CarId,CarName},null,null,null,null,null,null);
        cur.moveToFirst();

        ArrayList<String> carNames=new ArrayList<String>();

        carNames.add(cur.getString(cur.getColumnIndex(CarName)));
        while (cur.moveToNext())
        {
            carNames.add(cur.getString(cur.getColumnIndex(CarName)));
        }


        cur.close();
        db.close();

        return carNames.toArray(new String[carNames.size()] );

    }

    public Car GetCarInfo(String carName)
    {
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(CarTable,new String[]{CarId,CarVendor,CarModel,CarName},CarName+"=?",new String[]{carName},null,null,null);
        cursor.moveToFirst();

        Car car=new Car();

        car.Id=cursor.getInt(cursor.getColumnIndex(CarId));
        car.Vendor=cursor.getString(cursor.getColumnIndex(CarVendor));
        car.Model=cursor.getString(cursor.getColumnIndex(CarModel));
        car.Name=cursor.getString(cursor.getColumnIndex(CarName));

        cursor.close();
        db.close();

        return car;
    }
    
    public ArrayList<FuelInfo> GetFuelInfoByCar(int carId)
    {

        ArrayList<FuelInfo> fiList=new ArrayList<FuelInfo>();
        SQLiteDatabase db=this.getReadableDatabase();




            Cursor c= null;
            if (db != null) {
                c = db.query(FuelTable,new String[]{FuelId,FuelCarId,FuelDate,FuelAdded,FuelUsed,FuelOdo},FuelCarId+"=?",new String[]{Integer.toString(carId)},null,null,FuelId+" desc");
            }


            if (c != null) {
                c.moveToFirst();



            //FuelInfo fi=new FuelInfo();
        if(c.getCount()>0)
        {
            fiList.add(FillFuelInfoFromCursor(c));
        }

        while (c.moveToNext())
        {
            fiList.add(FillFuelInfoFromCursor(c));
        }

        c.close();
            }

        if (db != null) {
            db.close();
        }

        return fiList;

    }

    public void SaveFuelInfo(FuelInfo fuelInfo)
    {
        SQLiteDatabase db=getWritableDatabase();
        //Calendar c=Calendar.getInstance();
        //c.
        ContentValues cv=new ContentValues();
        cv.put(FuelCarId,fuelInfo.CarId);
        cv.put(FuelDate,new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()));
        cv.put(FuelUsed,fuelInfo.FuelUsed);
        cv.put(FuelAdded,fuelInfo.FuelAdded);
        cv.put(FuelOdo,fuelInfo.Odometer);
        db.insert(FuelTable,null,cv);

        db.close();

    }

    private FuelInfo FillFuelInfoFromCursor(Cursor c)
    {
        FuelInfo fuelInfo=new FuelInfo();

        fuelInfo.Id=c.getInt(c.getColumnIndex(FuelId));
        fuelInfo.CarId=c.getInt(c.getColumnIndex(FuelCarId));
        fuelInfo.Date=c.getString(c.getColumnIndex(FuelDate));
        fuelInfo.FuelUsed=c.getDouble(c.getColumnIndex(FuelUsed));
        fuelInfo.FuelAdded=c.getDouble(c.getColumnIndex(FuelAdded));
        fuelInfo.Odometer=c.getInt(c.getColumnIndex(FuelOdo));

        return fuelInfo;
    }
    public void UpdateFuelInfo()
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update "+FuelTable+" set CarId=2");
        db.close();
    }
}
