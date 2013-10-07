package ru.fr1z2r.fuelmanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static ru.fr1z2r.fuelmanager.ContextMenuItems.Delete;

/**
 * Created by Fr1z2r on 23.09.13.
 */
public class FuelActivity extends Activity {

    private int carId;
    ArrayAdapter<FuelInfo> adapter;
    private int selected=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);


        UpdateLayout();
    }

    private void UpdateLayout()
    {
        TextView textView=(TextView)findViewById(R.id.textView2);
        TextView textViewLast5=(TextView)findViewById(R.id.textViewLast5);
        TextView textViewAvg=(TextView)findViewById(R.id.textViewAvg);
        ListView fuelListView=(ListView)findViewById(R.id.fuelListView);
        registerForContextMenu(fuelListView);

        DbHelper db=new DbHelper(this);
        String car=getIntent().getExtras().getString("Car");
        Car carInfo =db.GetCarInfo(car);
        carId=carInfo.Id;
        ArrayList<FuelInfo> fiList=db.GetFuelInfoByCar(carId);
        textView.setText(Double.toString(CalcAvgRange(fiList, 2)));
        //Collections.reverse(fiList);
        textViewLast5.setText(Double.toString(CalcAvgRange(fiList,5)));
        textViewAvg.setText(Double.toString(CalcAvgRange(fiList,fiList.size())));
        adapter=new ArrayAdapter<FuelInfo>(this,android.R.layout.simple_list_item_1,fiList);
        fuelListView.setAdapter(adapter);


    }

    public void addBtnClick(View v)
    {
        Intent intent=new Intent(this,AddFuelActivity.class);
        intent.putExtra("CarId",carId);
        startActivityForResult(intent, 1);
       // UpdateLayout();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        //menu.add(Menu.NONE, ContextMenuItems.Details.ordinal(), Menu.NONE, "Details");
        menu.add(Menu.NONE, Delete.ordinal(),Menu.NONE,"Delete");
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo=(AdapterView.AdapterContextMenuInfo)menuInfo;
        selected=adapter.getItem(adapterContextMenuInfo.position).Id;

    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (ContextMenuItems.values()[item.getItemId()])
        {
            case Delete:
             DbHelper db=new DbHelper(this);
                db.DeleteFuelInfo(selected);
                UpdateLayout();
                default:
                    return super.onContextItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int req,int result,Intent intent)
    {
        UpdateLayout();
    }

   /* private double CalcLastAvg(ArrayList<FuelInfo> fiList)
    {
        if(fiList.size()<2)
            return 0;
        double odometerA=fiList.get(fiList.size()-1).Odometer;
        double odometerB=fiList.get(fiList.size()-2).Odometer;
        double fuelUsedA=fiList.get(fiList.size()-1).FuelUsed;
        double fuelUsedB=fiList.get(fiList.size()-2).FuelUsed;

        double avg=(fuelUsedA-fuelUsedB)*100.0/(odometerA-odometerB);

        return avg;
    }*/
    private double CalcAvgRange(ArrayList<FuelInfo> fiList,int count)
    {
        if(fiList.size()<count)
            return 0;
        if(fiList.size()==0)
            return 0;
        double odometerFirst=fiList.get(0).Odometer;
        double odometerLast=fiList.get(count-1).Odometer;
        double fuelUsedFirst=fiList.get(0).FuelUsed;
        double fuelUsedLast=fiList.get(count-1).FuelUsed;

        double avgRange=(fuelUsedFirst-fuelUsedLast)*100.0/(odometerFirst-odometerLast);

        return avgRange;
    }
}
