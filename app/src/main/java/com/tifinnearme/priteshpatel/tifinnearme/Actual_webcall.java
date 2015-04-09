package com.tifinnearme.priteshpatel.tifinnearme;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by pritesh.patel on 03-04-15.
 */
public class Actual_webcall {

    public String getData() throws Exception{
        BufferedReader br=null;
        String data=null;
        try{
            HttpClient client=new DefaultHttpClient();//client that will request
            URI website=new URI("http://www.twitter.com");
            Log.i("fb","Connected");
            HttpGet request=new HttpGet();//to GET the data from site
            request.setURI(website);
            HttpResponse response=client.execute(request);//Get reesponse by exceuting the request
            br=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));//convert response into readable strings

            StringBuffer sb=new StringBuffer("");
            String line="";
            String newLine=System.getProperty("line.separator");
            while((line=br.readLine())!=null){
                sb.append(line+newLine);
            }
            data=sb.toString();
            br.close();

            return data;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            br.close();
            return data;

        }


    }

}
