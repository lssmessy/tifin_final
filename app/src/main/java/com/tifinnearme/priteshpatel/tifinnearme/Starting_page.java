package com.tifinnearme.priteshpatel.tifinnearme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pritesh.patel on 02-04-15.
 */


public class Starting_page extends Activity {
    ImageView iv;
    TextView tf,cs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv=(ImageView)findViewById(R.id.tifinImage);
        //iv.setImageResource(R.id.t);
        setContentView(R.layout.start_page);
        tf=(TextView)findViewById(R.id.tifinvala);
        cs=(TextView)findViewById(R.id.customer);
        tf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Starting_page.this,Tifinvala_reg.class);
                startActivity(i);
            }
        });
        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Starting_page.this,Customer_reg.class);
                startActivity(i);
            }
        });
    }
    /*public void showTifinvala(View view){
        Intent i=new Intent(this,Tifinvala_reg.class);
        startActivity(i);
    }
    public void showCustomer(View view){
        Intent i=new Intent(this,Customer_reg.class);
        startActivity(i);
    }*/
}
