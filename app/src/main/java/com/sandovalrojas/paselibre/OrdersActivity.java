package com.sandovalrojas.paselibre;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sandovalrojas.paselibre.adapters.OrderAdapter;
import com.sandovalrojas.paselibre.models.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersActivity extends AppCompatActivity {

    private static final String SERVICE_URL = "http://paselibre.info/api/v1/orders";
    private static final String API_KEY = "a3240ae77dcb1308e094e24c16698d96";
    private static final String JSON_STATUS_SUCCESS = "success";
    private static final String JSON_MESSAGE = "message";
    private static final String KEY_APIKEY = "api_key";

    private static final String KEY_TOKEN = "user_token";

    private SharedPreferences preferences;

    OrderAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ArrayList<Order> arrayOfOrders = new ArrayList<Order>();
        adapter = new OrderAdapter(this, arrayOfOrders);

        listView = (ListView) findViewById(R.id.lvOrders);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = adapter.getItem(position);
                Intent intent = new Intent(OrdersActivity.this, TicketsActivity.class);
                intent.putExtra("ORDER_ID", order.getId());
                startActivity(intent);
            }
        });

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        loadOrders();
    }

    private void loadOrders() {

        String url = SERVICE_URL + "?" + KEY_APIKEY + "=" + API_KEY + "&" + KEY_TOKEN + "=" + preferences.getString("TOKEN_USER", "");

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response.toString());
                    String status = jsonObject.getString("status");
                    if (status.equals(JSON_STATUS_SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject row = jsonArray.getJSONObject(i);
                            Order order = new Order(row.getInt("id"), row.getString("number"), row.getString("date_time"));
                            adapter.add(order);
                        }
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
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
