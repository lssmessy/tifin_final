package com.tifinnearme.priteshpatel.tifinnearme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by pritesh.patel on 02-04-15.
 */
public class Customer_reg extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tifin_reg);
    }

    public void onBackClicked(View view){
        Intent i=new Intent(this,Starting_page.class);
        startActivity(i);
    }
    public void onNextclicked(View view){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
