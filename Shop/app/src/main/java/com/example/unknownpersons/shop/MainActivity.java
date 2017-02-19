package com.example.unknownpersons.shop;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout br,c;
    TextView bt,ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (TextView) findViewById(R.id.b_item);
        ct = (TextView) findViewById(R.id.cd_item);

        br = (LinearLayout) findViewById(R.id.b);
        c =  (LinearLayout) findViewById(R.id.cd);
        br.setOnClickListener(this);
        c.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.b:
                Intent i = new Intent();
                i.setComponent(new ComponentName(getApplicationContext(),oneItem.class));
                startActivity(i);
                Toast.makeText(getApplicationContext(),""+bt.getText().toString(),Toast.LENGTH_LONG).show();
                break;
            case R.id.cd:
                break;
        }

    }
}
