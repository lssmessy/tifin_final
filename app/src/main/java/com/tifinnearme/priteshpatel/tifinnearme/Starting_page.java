package com.tifinnearme.priteshpatel.tifinnearme;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by PRITESH on 03-04-2015.
 */
public class Starting_page extends ActionBarActivity{
    EditText username,password;
    Button login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll_view=new ScrollView(this);
        scroll_view.setBackgroundColor(Color.parseColor("#FFCC99"));
        RelativeLayout rl=new RelativeLayout(this);
        rl.setBackgroundColor(Color.parseColor("#FFCC99"));
        //Edittext initialization
        username=new EditText(this);
        username.setHint("Username");
        username.setId(1);
        username.setInputType(InputType.TYPE_CLASS_TEXT);
        username.setImeOptions(EditorInfo.IME_ACTION_NEXT);//To show next button on keypad

        password=new EditText(this);
        password.setHint("Password");
        password.setId(2);
        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        password.setImeOptions(EditorInfo.IME_ACTION_NEXT);//To show next button on keypad

        //Button initialization

        login=new Button(this);
        login.setBackgroundColor(Color.parseColor("#A5ABAB"));
        login.setText("Login");
        login.setId(3);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validations()) {
                    InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(login.getWindowToken(),0);
                    new Login_background().execute();
                    /*Intent i = new Intent(Starting_page.this, MainActivity.class);
                    startActivity(i);*/
                }
             }
        });

        signup=new Button(this);
        signup.setText("Sign Up");
        signup.setBackgroundColor(Color.parseColor("#A5ABAB"));
        signup.setId(4);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Starting_page.this,SignUp_page.class);
                startActivity(i);
            }
        });
        RelativeLayout.LayoutParams signup_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //signup_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        signup_params.addRule(RelativeLayout.RIGHT_OF,login.getId());
        signup_params.addRule(RelativeLayout.BELOW, password.getId());
        signup_params.setMargins(50,30,0,0);
        //signup_params.setMargins(0,30,0,0)

        RelativeLayout.LayoutParams login_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //login_params.addRule(RelativeLayout.);
        login_params.addRule(RelativeLayout.BELOW, password.getId());
        login_params.setMargins(50,30,0,0);

        RelativeLayout.LayoutParams password_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        password_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        password_params.addRule(RelativeLayout.BELOW,username.getId());
        password_params.setMargins(0,30,0,0);
        //username
        RelativeLayout.LayoutParams username_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        username_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        username_params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        username_params.setMargins(20, 50, 0, 0);

        Resources rs=getResources();
        int pixels= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,320,rs.getDisplayMetrics());

        username.setWidth(pixels);
        password.setWidth(pixels);

        rl.addView(username,username_params);
        rl.addView(password,password_params);
        rl.addView(login,login_params);
        rl.addView(signup,signup_params);

        scroll_view.addView(rl);
        setContentView(scroll_view);

    }

    private boolean validations() {

        if(username.getText().toString().trim().length() <=0){
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Username is blank");
            ad.setMessage("Username can't be left blank");

            ad.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    username.setText("");
                    username.requestFocus();

                }
            });
            ad.show();

            return false;
        }
        /*else if(username.getText().length()<= 0)
        {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Username is blank");
            ad.setMessage("Username can't be left blank");

            ad.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    username.requestFocus();
                }
            });
            ad.show();

            return false;
        }*/
        else if(password.getText().length() <6) {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Password");
            ad.setMessage("Password length must be greater than 6");

            ad.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    password.requestFocus();
                }
            });
            ad.show();

            return  false;
        }
        else
            return true;

    }


    private class Login_background extends AsyncTask<Void,Void,Void>{
        ProgressDialog dialog;
        static final String p="MyLog";
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog=new ProgressDialog(Starting_page.this);
            dialog.setMessage("Checking credentials...");
            dialog.setTitle("Logging");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
            

            Log.i(p, "onPreExecute");
        }


        @Override
        protected Void doInBackground(Void... params) {

            Intent i = new Intent(Starting_page.this, MainActivity.class);

            try {
                Thread.sleep(2000);
                dialog.dismiss();
                startActivity(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(p,"doInBackground after 2 secs");
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(p,"onPostExecute");
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.i(p,"onProgressUpdate");
            dialog.dismiss();
        }
    }
}
