package com.example.unknownper.missingapp;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

public class Developers extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabHost tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Developers.this);
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
