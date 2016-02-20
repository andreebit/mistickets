package com.sandovalrojas.paselibre;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class PanelActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private TextView txtNameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNameUser = (TextView) findViewById(R.id.txtNameUser);
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        txtNameUser.setText(preferences.getString("NAME_USER", "..."));
    }

    public void showOrders(View v) {
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
    }

}
