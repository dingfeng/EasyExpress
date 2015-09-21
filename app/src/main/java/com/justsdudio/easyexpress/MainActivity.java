package com.justsdudio.easyexpress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends Activity {
    private static  Map<String,String> expressMap;
    private void initExpressName()
    {
       /* try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result=null;
            expressMap = new HashMap<String,String>();
            while((line = bufReader.readLine()) != null)
            {
                String[] expressIdAndName = line.split(":");
              expressMap.put(expressIdAndName[0],expressIdAndName[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        expressMap = new HashMap<String,String>();
        String[] express_ids = getResources().getStringArray(R.array.express_id);
        String[] express_names = getResources().getStringArray(R.array.date);
        for (int i = 0; i < express_ids.length; ++i)
        {
            expressMap.put(express_names[i],express_ids[i]);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initExpressName();

    }

    public void setExpressInfo(String[] infos)
    {
        ListView list = (ListView)findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.array_item,infos);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void clickHandler(View target)
    {
        /*
         *选择快递公司
         */
       if (target.getId() == R.id.express_choose)
       {
           Intent intent = new Intent(this, ChooseExpressActivity.class);
           startActivityForResult(intent, 0);
       }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent)
    {

        if (requestCode == 0 && resultCode == 0)
        {
            Bundle data = intent.getExtras();
            String express = data.getString("express");
            TextView express_name_view = (TextView)findViewById(R.id.express_name);
            express_name_view.setText(express);
            this.setExpressIconByExpressName(express);
        }
    }

    private void setExpressIconByExpressName(String expressName)
    {

    }


}
