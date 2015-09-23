package com.justsdudio.easyexpress;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ExpressHttpRequest.DataRequest;
import ExpressHttpRequest.ExpressInfo;


public class MainActivity extends Activity {
    private static  Map<String,String> expressMap;
    private ImageView logoView;
    private TextView expressNameView;
    private DataRequest dataRequest;
    private TextView idView;
    private TextView idShowView;
    private TextView stateView;
    private void init()
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
        dataRequest = new DataRequest();
        idView = (TextView) findViewById(R.id.id_text);
        idShowView = (TextView) findViewById(R.id.express_num);
        stateView = (TextView) findViewById(R.id.express_status);
        expressMap = new HashMap<String,String>();
        String[] express_ids = getResources().getStringArray(R.array.express_id);
        String[] express_names = getResources().getStringArray(R.array.date);
        for (int i = 0; i < express_ids.length; ++i)
        {
            expressMap.put(express_names[i],express_ids[i]);
        }
        logoView = (ImageView) findViewById(R.id.choose_express_button);
        expressNameView = (TextView) findViewById(R.id.express_name);
        String expressName = expressNameView.getText().toString();
        setExpressIconByExpressName(expressName);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    public void setExpressInfo(String[] infos)
    {
        ListView list = (ListView)findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.array_item,infos);
        list.setAdapter(adapter);
        list.setSelection(adapter.getCount() - 1);
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
       //输入订单编号后要求查询
       else if (target.getId() == R.id.search_button)
       {
          String id = this.idView.getText().toString();
          String expressName = this.expressNameView.getText().toString();
          RequestTask task = new RequestTask(this);
           task.execute(expressName,id);
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setExpressIconByExpressName(String expressName) {
        String expressId = expressMap.get(expressName);
        Resources res = this.getResources();
        AssetManager assetManager = res.getAssets();
        String filename = null;
        boolean exists = true;
        try {
            filename = "expressImage/" + expressId + ".png";
            InputStream in = assetManager.open(filename);
            Drawable drawable = Drawable.createFromStream(in,expressId);
            logoView.setBackground(drawable);
        } catch (IOException e) {
            exists = false;
            e.printStackTrace();
        }
        if (!exists)
        {
            filename = "expressImage/unknown.png";
            try {
                InputStream in = assetManager.open(filename);
                Drawable drawable = Drawable.createFromStream(in,expressId);
                logoView.setBackground(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    class RequestTask extends AsyncTask<String,Integer,ExpressInfo>
    {

        private  Context context;
        private ProgressDialog pdialog;
        public RequestTask(Context context)
        {
            this.context = context;
        }

        //执行中
        @Override
        protected ExpressInfo doInBackground(String... params) {
            String expressName = expressMap.get(params[0]);
            String id = params[1];
            Log.v("expressName : ",expressName);
            Log.v("expressId",id);
            Log.v("sdf","sdf");
            ExpressInfo info = null;
            try {
              info = dataRequest.findExrpessInfo(expressName, id);
            }catch (Exception e)
            {
                Log.e("error:",e.toString());
            }
            return info;
        }

        //执行之后
        @Override
        protected  void onPostExecute(ExpressInfo info)
        {
            if (info == null) {
                pdialog.dismiss();
                Toast toast = Toast.makeText(context,"查询异常",Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            idShowView.setText(info.getId());
            String state = null;
            switch (info.getState())
            {
                //未知
                case UNKOWN:
                    state = "未知";
                    break;
                //已签收
                case RECEIVED:
                    state = "已签收";
                    break;
                //未签收
                case HASNOTBEENRECEIVED:
                    state = "未签收";
                    break;
                case DELIVERYING:
                    state = "正在派送";
                    break;
            }
            stateView.setText(state);
            ArrayList<String> list = new ArrayList<String>();
            Iterator<String> itr = info.getInfos();
            while(itr.hasNext())
            {
                list.add(itr.next());
            }
            if (list.size() != 0)
            {
                String[] infos = new String[list.size()];
                list.toArray(infos);
                setExpressInfo(infos);
            }
            pdialog.dismiss();
        }

        //执行之前
        @Override
        protected  void onPreExecute()
        {
           pdialog = new ProgressDialog(context);
            //进度条为环状进度条
           pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //进度条不显示进度
           pdialog.setIndeterminate(false);
           pdialog.show();
        }

        //执行中更新
        @Override
        protected  void onProgressUpdate(Integer... value)
        {
            //do nothing
        }

    }


}
