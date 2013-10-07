package ru.fr1z2r.fuelmanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddFuelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fuel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_fuel, menu);
        return true;
    }

    public void saveBtnClick(View v)
    {
        try
        {
        Intent intent=getIntent();
        FuelInfo fuelInfo=new FuelInfo();
        fuelInfo.CarId=intent.getExtras().getInt("CarId");
            //double a=Double.parseDouble("");
        fuelInfo.FuelAdded=Double.parseDouble(((EditText)findViewById(R.id.editTextAdded)).getText().toString());
        fuelInfo.FuelUsed=Double.parseDouble(((EditText)findViewById(R.id.editTextUsed)).getText().toString());
        fuelInfo.Odometer=Integer.parseInt(((EditText)findViewById(R.id.editTextOdo)).getText().toString());
        DbHelper dbHelper=new DbHelper(this);
        dbHelper.SaveFuelInfo(fuelInfo);
        //dbHelper.close();
        setResult(RESULT_OK,getIntent());
        finish();
        }
        catch (Exception e)
        {
            TextView err=(TextView)findViewById(R.id.textViewErr);
            err.setVisibility(View.VISIBLE);
        }

    }
    
}
