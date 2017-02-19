package com.example.unknownper.missingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class miss_one extends AppCompatActivity {

    String miss_position;
    String ru;
    Bitmap set,king;
    ImageView imageView;
    TextView name,age,pin,add,status;
    ArrayList<HashMap<String, String>> MyArrList;
    ListView listView;
    ProgressDialog dialog1 = null;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    String httpurl = "http://studentmitra.co.in/none/missingapp/miss_one.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_miss_one);
        miss_position = getIntent().getStringExtra("miss_position").toString();
        //
        ConnectivityManager com = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = com.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean mobile_data = com.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi || mobile_data) {
            InputMethodManager inputMethodManager1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // Initialize  AsyncLogin() class with email and password
            new miss_one.AsyncRegister().execute(miss_position);
        }
        else{
            Toast.makeText(getApplicationContext(),"No Network Access",Toast.LENGTH_LONG).show();
        }

    }
    //

    private Bitmap getimage(String path) {
        URL url = null;
        try {
            url=new URL(path);
            king = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(miss_one.this,e.toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(miss_one.this,e.toString(),Toast.LENGTH_LONG).show();
        }
        return king;
    }



    ///
    private class AsyncRegister extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(miss_one.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(true);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://studentmitra.co.in/none/missingapp/miss_one.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                try {
                    conn = (HttpURLConnection)url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("Sno", params[0]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(result != null)
            {
                ru=result.toString();
                allNotifications();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(miss_one.this, "Already Exists!!!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(miss_one.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
    public void allNotifications(){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        dialog1 = ProgressDialog.show(miss_one.this, "", "Loading....", true);


        String url = httpurl;

        try {

            JSONArray data = new JSONArray(ru);

            MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();

                String s="&amp;";
                String html=c.getString("name");
                String names;
                if(html.indexOf(s) != -1)
                {
                    names=html.replaceAll(s,"&");
                }
                else
                {
                    names=html;
                }
                //These are the column names of database so plz make any changes in db
                map.put("put_pin", c.getString("Sno"));
                map.put("put_name", names);
                map.put("put_age", c.getString("age"));

                map.put("put_image","http://studentmitra.co.in/none/missing_uploads/"+c.getString("picture"));

                map.put("put_add",c.getString("address"));
                MyArrList.add(map);
                name = (TextView) findViewById(R.id.mis_one_name);
                age = (TextView) findViewById(R.id.mis_one_age);
                add = (TextView) findViewById(R.id.mis_one_add);
                pin = (TextView) findViewById(R.id.mis_one_pin);
                status = (TextView) findViewById(R.id.mis_one_status);
                imageView = (ImageView) findViewById(R.id.miss_one_img);
                String path = MyArrList.get(0).get("put_image").toString();
                set = getimage(path);
                imageView.setImageBitmap(set);
                String miss_name,miss_age,miss_ad,miss_pin;
                miss_name = MyArrList.get(0).get("put_name").toString();
                miss_age = MyArrList.get(0).get("put_age").toString();
                miss_ad = MyArrList.get(0).get("put_add").toString();
                miss_pin = MyArrList.get(0).get("put_pin").toString();

                name.setText(miss_name);
                age.setText("Age:"+miss_age);
                add.setText(miss_ad);
                pin.setText("Pin:"+miss_pin);
                status.setText("NotFound");


            }
            dialog1.dismiss();
            /*SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(MainActivity.this, MyArrList, R.layout.missing_layout_ex,
                    new String[] {"put_pin","put_name","put_age","put_image"}, new int[] { R.id.mis_pin,R.id.mis_name, R.id.mis_age,R.id.mis_ad});
            lisView1.setAdapter(sAdap);*/


            final android.app.AlertDialog.Builder viewDetail = new android.app.AlertDialog.Builder(this);
            // OnClick Item

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void call(View v){
        Intent i=new Intent();
        i.setAction(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+100));
        startActivity(i);
    }

}
