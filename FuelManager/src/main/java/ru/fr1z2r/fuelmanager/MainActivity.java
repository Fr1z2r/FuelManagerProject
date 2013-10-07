package ru.fr1z2r.fuelmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private  ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView time=(TextView)findViewById(R.id.textView);
        //WorkTime wt=new WorkTime();
        //time.setText(Integer.toString(wt.GetWorkTime()));

        ListView cars=(ListView)findViewById(R.id.carList);
        String[] carNames=new String[]{"Solaris","Cadillac"};
        DbHelper dbHelper=new DbHelper(this);

        carNames=dbHelper.GetCarNames();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,carNames);
        cars.setAdapter(adapter);
        cars.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.carList:
                Intent intent=new Intent(this,FuelActivity.class);
                //intent.putExtra("Car",(ListView)findViewById(R.id.carList))
                startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this,FuelActivity.class);
        intent.putExtra("Car", adapter.getItem(i));
        startActivity(intent);
    }

    public void dropDBClick(View v)
    {
        DbHelper db=new DbHelper(this);
        //db.RebuildDatabase();
        db.UpdateFuelInfo();
    }
}
