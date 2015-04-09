package com.tifinnearme.priteshpatel.tifinnearme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

/**
 * Created by pritesh.patel on 03-04-15.
 */
public class Webcall extends Activity {
    TextView tv;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().detectAll().build());
        setContentView(R.layout.load_data);
        tv=(TextView)findViewById(R.id.loadData);

        new LoadinData().execute();



    }


    private class LoadinData extends AsyncTask<Void,Void,Void> {

        static final String p="MyLog";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(Webcall.this);
            dialog.setMessage("Loading...");
            dialog.setTitle("Getting Data");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();

        }
        @Override
        protected Void doInBackground(Void... params) {
            Actual_webcall aw=new Actual_webcall();
            try {
                String data=aw.getData();
                tv.setText(data);
                Thread.sleep(3000);
                dialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }
}
