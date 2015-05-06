package com.tifinnearme.priteshpatel.tifinnearme.postdata;

import com.tifinnearme.priteshpatel.tifinnearme.cutomer.Customer_reg;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pritesh.patel on 06-05-15.
 */
public class Post_Data
{
    public void postData(){
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://whtsnext.cuccfree.com/api");

            Customer_reg customer_details = new Customer_reg();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("Username", customer_details.username.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("Password", customer_details.password.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("Email", customer_details.email.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("Address", customer_details.address.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("Mobile", customer_details.mobile.getText().toString()));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response=httpClient.execute(httpPost);

            System.out.print(response.getEntity().getContent().read());

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
