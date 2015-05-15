package com.tifinnearme.priteshpatel.tifinnearme;

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
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.tifinnearme.priteshpatel.tifinnearme.json.JSONParser;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PRITESH on 03-04-2015.
 */
public class Starting_page extends ActionBarActivity{
    EditText username,password;
    Button login,signup,webcall;
    JSONParser jsonParser = new JSONParser();
    private static String register_user = "http://whtsnext.cuccfree.com/apis/validate_user.php";
    private ProgressDialog dialog;
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

                    List<NameValuePair> params=new ArrayList<NameValuePair>();
                    params.add( new BasicNameValuePair("username",username.getText().toString()));
                    params.add( new BasicNameValuePair("password",password.getText().toString()));
                    Login_background background=new Login_background(params);
                    background.execute();
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

        webcall=new Button(this);
        webcall.setText("Webcall");
        webcall.setId(5);
        webcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Starting_page.this,Webcall.class);
                startActivity(i);
            }
        });

        RelativeLayout.LayoutParams webcall_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //signup_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        webcall_params.addRule(RelativeLayout.RIGHT_OF,signup.getId());
        webcall_params.addRule(RelativeLayout.BELOW, password.getId());
        webcall_params.setMargins(50,30,0,0);


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
        rl.addView(webcall,webcall_params);
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


    class Login_background extends  AsyncTask<String,String,Void>{
        List<NameValuePair> params;
        String message=null;

        public Login_background(List<NameValuePair> params){
            this.params=params;
        }
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(Starting_page.this);
            dialog.setMessage("We are Logging in. Please wait . . .");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }



        @Override
        protected Void doInBackground(String... paras) {
            DefaultHttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(register_user);
            JSONObject jsonObject=new JSONObject();
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            try {

                jsonObject.put("username",username.getText().toString());
                jsonObject.put("password",password.getText().toString());
                StringEntity se=new StringEntity("json="+jsonObject.toString());
                //httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
                httpPost.setEntity(se);
               String req=se.toString();

                HttpResponse response=httpClient.execute(httpPost);


                if(response!=null){
                    InputStream is=response.getEntity().getContent();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb=new StringBuilder();
                    String line=null;
                    while((line=bufferedReader.readLine())!=null){
                        sb.append(line+"\n");
                    }
                    this.message=sb.toString();
                    //Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            //JSONObject jsonObject=jsonParser.makeHttpRequest(register_user,"POST",this.params);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            //this assumes that the response looks like this:
            //{"success" : true }
            /*String message = null;
            try {
                message = result.getString("message");
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            boolean success = false;
            try {
                success = result.getBoolean("success");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
                    Toast.LENGTH_SHORT).show();*/
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    /*private class Login_background extends AsyncTask<Void,Void,Void>{
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
            dialog.dismiss();
            String data = null;
            try {
                data = URLEncoder.encode("Username", "UTF-8")
                        + "=" + URLEncoder.encode(username.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "="
                        + URLEncoder.encode(password.getText().toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            String text = "";
            BufferedReader reader=null;

            // Send data
            try
            {

                // Defined URL  where to send data
                URL url = new URL("http://whtsnext.cuccfree.com/apis/login_check.php");

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                text = sb.toString();
                final String finalText = text;
                Starting_page.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Starting_page.this, finalText, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch(Exception ex)
            {

            }
            finally
            {
                try
                {

                    reader.close();
                }

                catch(Exception ex) {}
            }

            // Show response on activity

            *//*Intent i = new Intent(Starting_page.this, Main_Map.class);

            try {
                Thread.sleep(2000);
                dialog.dismiss();
                startActivity(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(p,"doInBackground after 2 secs");*//*

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(p,"onPostExecute");

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.i(p,"onProgressUpdate");
            dialog.dismiss();
        }
    }*/
}
