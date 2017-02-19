package com.example.unknownper.missingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpClientStack;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.w3c.dom.Text;

import java.io.File;
import java.util.UUID;

public class Post_mis_beg extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Uri imageCaptureUri;
    Button miss_upload_details,beg_upload_details;
    EditText aadhar,name,age,address;
    TextView beg_location_lat,beg_location_lng;
    final static int REQUEST_IMAGE_CAPTURE=1;
    final static int REQUEST_IMAGE_FILE=2;
    Double latitude;
    Double longitude;
    ImageView beggers_image,beggers_button;
    CheckBox checkBox;
    TabHost tb;
    private GestureDetectorCompat gDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_mis_beg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        aadhar = (EditText) findViewById(R.id.aa);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        address = (EditText) findViewById(R.id.add);
        beg_location_lat = (TextView) findViewById(R.id.beg_location_lat);
        beg_location_lng=(TextView)findViewById(R.id.beg_location_lng);
        miss_upload_details = (Button) findViewById(R.id.mis_upload);
        beg_upload_details = (Button) findViewById(R.id.beg_upload);
        miss_upload_details.setOnClickListener(this);
        beg_upload_details.setOnClickListener(this);
////
        tb = (TabHost) findViewById(R.id.tabhost);
        tb.setup();
        TabHost.TabSpec sp = tb.newTabSpec("Missing");
        sp.setContent(R.id.tab1);
        sp.setIndicator("Missing");
        tb.addTab(sp);
        sp = tb.newTabSpec("Beggers");
        sp.setContent(R.id.tab2);
        sp.setIndicator("Home Less");
        tb.addTab(sp);
////

        beggers_button = (ImageView) findViewById(R.id.imageView4);
        //disable camera
        if(!hasCamera())
            beggers_button.setEnabled(false);

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
    }
    //
    /*
    public void uploadMultipart() {
        //getting details
        String n,ag,addre,aadharno;
        n = name.getText().toString().trim();
        ag = age.getText().toString().trim();
        aadharno = aadhar.getText().toString().trim();
        addre = address.getText().toString().trim();


        //getting the actual path of the image
        String path = getRealPathFromUri(imageCaptureUri);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, SyncStateContract.Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/
    ///
    public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);

    }
    public void launch_camera(View v){
        beggers_image = (ImageView) findViewById(R.id.imageView3);
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),"tmp_avatar" + String.valueOf(System.currentTimeMillis())+".jpg");
        imageCaptureUri = Uri.fromFile(file);
        try{

            camera.putExtra(MediaStore.EXTRA_OUTPUT,imageCaptureUri);
            camera.putExtra("return data",true);
            startActivityForResult(camera,REQUEST_IMAGE_CAPTURE);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public void miss_camera(View v){
        beggers_image = (ImageView) findViewById(R.id.imageView);
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),"tmp_avatar" + String.valueOf(System.currentTimeMillis())+".jpg");
        imageCaptureUri = Uri.fromFile(file);
        try{

            camera.putExtra(MediaStore.EXTRA_OUTPUT,imageCaptureUri);
            camera.putExtra("return data",true);
            startActivityForResult(camera,REQUEST_IMAGE_CAPTURE);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public void miss_gallery(View v){
        beggers_image = (ImageView) findViewById(R.id.imageView);
        Intent camera = new Intent();
        camera.setType("image/*");
        camera.setAction(camera.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(camera,"complete Action Using"),REQUEST_IMAGE_FILE);
    }
    public void launch_gallery(View v){
        beggers_image = (ImageView) findViewById(R.id.imageView3);

        Intent camera = new Intent();
        camera.setType("image/*");
        camera.setAction(camera.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(camera,"complete Action Using"),REQUEST_IMAGE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode != RESULT_OK)
            return;
        String path = "";
        Bitmap bitmap = null;
        if (requestCode == REQUEST_IMAGE_FILE) {
            imageCaptureUri = data.getData();
            path = getRealPathFromUri(imageCaptureUri);

            if (path == null)
                path = imageCaptureUri.getPath();
            if(path!=null)
                bitmap = BitmapFactory.decodeFile(path);
        }else{
            path = imageCaptureUri.getPath();
            bitmap = BitmapFactory.decodeFile(path);
        }
        beggers_image.setImageBitmap(bitmap);
    }
    public String getRealPathFromUri(Uri contentUri){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,proj,null,null,null);
        if(cursor == null)
            return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //


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

            Intent i = new Intent();
            i.setComponent(new ComponentName(getApplicationContext(),MainActivity.class));
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }
    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Post_mis_beg.this);
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
///
//method to get the file path from uri

public String getPath(Uri uri) {
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    cursor.moveToFirst();
    String document_id = cursor.getString(0);
    document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
    cursor.close();

    cursor = getContentResolver().query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
    cursor.moveToFirst();
    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
    cursor.close();

    return path;
}
public void uploadMultipart() {
        //getting name for the image
    String n,ag,ad,aadhar_num;
        aadhar_num = aadhar.getText().toString().trim();
                n = name.getText().toString().trim();
                ag = age.getText().toString().trim();
                ad = address.getText().toString().trim();


        //getting the actual path of the image
        String path = getPath(imageCaptureUri);
        //Uploading code
        try {
            if (aadhar_num.isEmpty() || n.isEmpty() || ag.isEmpty() || ad.isEmpty() || ad.isEmpty() || path.isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Fill All Fields..!!", Toast.LENGTH_LONG).show();
            }
            else {

                String uploadId = UUID.randomUUID().toString();
                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, Constants.MIS_UPLOAD_URL)
                        .addFileToUpload(path, "image") //Adding file
                        .addParameter("name", n) //Adding text parameter to the request
                        .addParameter("adhar", aadhar_num)
                        .addParameter("age", ag)
                        .addParameter("address", ad)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload
            }

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void beg_uploadMultipart() {
        //getting name for the image
        String name = beg_location_lat.getText().toString().trim();
        latitude=Double.parseDouble(beg_location_lat.getText().toString());
        longitude=Double.parseDouble(beg_location_lng.getText().toString());
        //getting the actual path of the image
        String path = getPath(imageCaptureUri);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.BEG_UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    ////
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.mis_upload:
                uploadMultipart();
                break;
            case R.id.beg_upload:
                beg_uploadMultipart();
                break;
        }
    }

    public void get_location(View v) throws SecurityException{
            final LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude=location.getLatitude();
                    longitude=location.getLongitude();
                   Toast.makeText(getApplicationContext(),latitude+"\n"+longitude,Toast.LENGTH_LONG).show();
                    Bundle b=new Bundle();
                    b.putDouble("lati",latitude);
                    b.putDouble("longi",longitude);
                    BlankFragment Fmap=new BlankFragment();
                    Fmap.setArguments(b);

                    beg_location_lat.setText(String.valueOf(latitude));
                    beg_location_lng.setText(String.valueOf(longitude));
                    try {
                        lManager.removeUpdates(this);
                    }catch(SecurityException e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
                @Override
                public void onProviderEnabled(String provider) {
                }
                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        }
    public void Location_Maps(View v){
      BlankFragment frag=new BlankFragment();
      frag.show(getFragmentManager(),"Location");
    }

}

