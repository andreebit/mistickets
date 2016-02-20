package com.sandovalrojas.paselibre;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String SERVICE_URL = "http://paselibre.info/api/v1/usuarios/acceder";
    private static final String API_KEY = "a3240ae77dcb1308e094e24c16698d96";
    private static final String JSON_STATUS_SUCCESS = "success";
    private static final String JSON_MESSAGE = "message";
    private static final String KEY_APIKEY = "api_key";

    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";

    private EditText txtEmail;
    private EditText txtPassword;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtEmail = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.password);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    public void onLogin(View v) {

        final String email = txtEmail.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response.toString());
                    String status = jsonObject.getString("status");
                    if (status.equals(JSON_STATUS_SUCCESS)) {
                        editor.putString("TOKEN_USER", jsonObject.getJSONObject("data").getString("token").toString());
                        editor.putString("NAME_USER", jsonObject.getJSONObject("data").getString("name").toString());
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this, PanelActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: " + jsonObject.get(JSON_MESSAGE), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_EMAIL, email);
                map.put(KEY_PASSWORD, password);
                map.put(KEY_APIKEY, API_KEY);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
