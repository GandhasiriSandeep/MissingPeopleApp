package com.example.unknownper.missingapp;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBar actionBar;
     ArrayList<HashMap<String, String>> MyArrList,MyArrList_beg;
    TabHost tb;
    Bitmap set;
    Double la=16.98,lo=80.76;
    //
    ImageView imageView,imageView_beg;
    TextView name,age,pin,add,location;
    Bitmap king;
    ListView lisView1,listView2;
    ProgressDialog dialog1 = null;
    String httpurl = "http://studentmitra.co.in/none/missingapp/msg.php";
    String httpurl_beg = "http://studentmitra.co.in/none/missingapp/msg_beg.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
////
        tb = (TabHost) findViewById(R.id.tabhost);
        tb.setup();

        TabHost.TabSpec sp = tb.newTabSpec("Missing");
        sp.setContent(R.id.tab1);
        sp.setIndicator("Missing");
        tb.addTab(sp);
        sp = tb.newTabSpec("Home Less");
        sp.setContent(R.id.tab2);
        sp.setIndicator("Home Less");
        tb.addTab(sp);
        ////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setComponent(new ComponentName(getApplicationContext(),Notifications.class));
                startActivity(i);
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //retrieve
//for wifi status
        ConnectivityManager com = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = com.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean mobile_data = com.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi || mobile_data) {
            lisView1 = (ListView) findViewById(R.id.lv);
            listView2 = (ListView) findViewById(R.id.lv_beg);

            lisView1.setAdapter(new imge());
            listView2.setAdapter(new imge_beg());
        }
        else {
                Toast.makeText(getApplicationContext(),"No Network Access",Toast.LENGTH_LONG).show();
        }
        TextView navigation;
        LayoutInflater  lis = LayoutInflater.from(getApplicationContext());
        View vis = lis.inflate(R.layout.nav_header_main,null);
        navigation = (TextView)vis. findViewById(R.id.navigation_username);
        navigation.setText("ss");
      //  allNotifications_beg();
    }
    class imge_beg extends BaseAdapter{

        @Override
        public int getCount() {
            allNotifications_beg();
            return MyArrList_beg.size();

        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater  li = LayoutInflater.from(getApplicationContext());
            View vi = li.inflate(R.layout.beg_layout_ex,null);
            imageView_beg = (ImageView)vi. findViewById(R.id.beg_photo);
            location = (TextView)vi. findViewById(R.id.beg_location);
            String path = MyArrList_beg.get(position).get("put_beg_image").toString();
            set = getimage(path);
            imageView_beg.setImageBitmap(set);
            String location_beg;
            location_beg = MyArrList_beg.get(position).get("put_beg_location").toString();


            location.setText(location_beg);

            //Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();

            return vi;
        }
    }
    class imge extends BaseAdapter{

        @Override
        public int getCount() {
            allNotifications();
            return MyArrList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater  li = LayoutInflater.from(getApplicationContext());
            View v = li.inflate(R.layout.missing_layout_ex,null);
            imageView = (ImageView)v. findViewById(R.id.mis_photo);
            name = (TextView)v. findViewById(R.id.mis_name);
            age = (TextView)v. findViewById(R.id.mis_age);
            add = (TextView)v. findViewById(R.id.mis_ad);
            pin = (TextView)v. findViewById(R.id.mis_pin);
            String path = MyArrList.get(position).get("put_image").toString();
            set = getimage(path);
            imageView.setImageBitmap(set);
            String miss_name,miss_age,miss_ad,miss_pin;
            miss_name = MyArrList.get(position).get("put_name").toString();
            miss_age = MyArrList.get(position).get("put_age").toString();
            miss_ad = MyArrList.get(position).get("put_add").toString();
            miss_pin = MyArrList.get(position).get("put_pin").toString();

            name.setText(miss_name);
            age.setText("Age:"+miss_age);
            add.setText(miss_ad);
            pin.setText("Pin:"+miss_pin);

            //Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();

            return v;
        }
    }

    private Bitmap getimage(String path) {
        URL url = null;
        try {
            url=new URL(path);
             king = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }
        return king;
    }


    public void allNotifications(){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        dialog1 = ProgressDialog.show(MainActivity.this, "", "Loading....", true);


        String url = httpurl;

        try {

            JSONArray data = new JSONArray(getJSONUrl(url));

             MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();

                String s="&amp;";
                String html=c.getString("name");
                String name;
                if(html.indexOf(s) != -1)
                {
                    name=html.replaceAll(s,"&");
                }
                else
                {
                    name=html;
                }
                //These are the column names of database so plz make any changes in db
                map.put("put_pin", c.getString("Sno"));
                map.put("put_name", name);
                map.put("put_age", c.getString("age"));

                map.put("put_image","http://studentmitra.co.in/none/missingapp/missing_uploads/"+c.getString("picture"));

                map.put("put_add",c.getString("address"));
                MyArrList.add(map);

            }
            dialog1.dismiss();
            /*SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(MainActivity.this, MyArrList, R.layout.missing_layout_ex,
                    new String[] {"put_pin","put_name","put_age","put_image"}, new int[] { R.id.mis_pin,R.id.mis_name, R.id.mis_age,R.id.mis_ad});
            lisView1.setAdapter(sAdap);*/
            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String pos = MyArrList.get(position).get("put_pin").toString();

                    Intent i = new Intent(MainActivity.this,miss_one.class);
                    i.putExtra("miss_position",pos);
                    startActivity(i);

                }
            });

            final android.app.AlertDialog.Builder viewDetail = new android.app.AlertDialog.Builder(this);
            // OnClick Item

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void allNotifications_beg(){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        dialog1 = ProgressDialog.show(MainActivity.this, "", "Loading....", true);


        String url = httpurl_beg;

        try {

            JSONArray data = new JSONArray(getJSONUrl(url));

            MyArrList_beg = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();

                //These are the column names of database so plz make any changes in db
                map.put("put_beg_pin", c.getString("Sno"));
                map.put("put_beg_image","http://studentmitra.co.in/none/missingapp/beg_uploads/"+c.getString("photo"));
                map.put("put_beg_location",c.getString("location"));
                MyArrList_beg.add(map);

            }
            dialog1.dismiss();
            /*SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(MainActivity.this, MyArrList, R.layout.missing_layout_ex,
                    new String[] {"put_pin","put_name","put_age","put_image"}, new int[] { R.id.mis_pin,R.id.mis_name, R.id.mis_age,R.id.mis_ad});
            lisView1.setAdapter(sAdap);*/
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String pos = MyArrList_beg.get(position).get("put_beg_pin").toString();
                    Bundle b=new Bundle();
                    b.putDouble("lati",la);
                    b.putDouble("longi",lo);
                    BlankFragment Fmap=new BlankFragment();
                    Fmap.setArguments(b);
/*
                    Intent i = new Intent(MainActivity.this,miss_one.class);
                    i.putExtra("miss_position",pos);
                    startActivity(i);*/
                    BlankFragment frag=new BlankFragment();
                    frag.show(getFragmentManager(),"Location");

                }
            });

            final android.app.AlertDialog.Builder viewDetail = new android.app.AlertDialog.Builder(this);
            // OnClick Item

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }


//////


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.login_register) {
            Intent i = new Intent();
            i.setComponent(new ComponentName(getApplicationContext(),Login.class));
            startActivity(i);
            finish();
            // Handle the camera action
        } else if (id == R.id.post) {

            Intent i = new Intent();
            i.setComponent(new ComponentName(getApplicationContext(),Post_mis_beg.class));
            startActivity(i);
            finish();

        } else if (id == R.id.orginfo) {
            Intent i = new Intent();
            i.setComponent(new ComponentName(getApplicationContext(),Organizations.class));
            startActivity(i);
            finish();

        } else if (id == R.id.dev) {
            Intent i = new Intent();
            i.setComponent(new ComponentName(getApplicationContext(),Developers.class));
            startActivity(i);
            finish();

        } else if (id == R.id.about) {

            Intent i = new Intent();
            i.setComponent(new ComponentName(getApplicationContext(),About.class));
            startActivity(i);
            finish();
        }else if (id == R.id.home) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/


    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }
    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);
        alertDialog.setMessage("Are you sure to leave?");

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

}
