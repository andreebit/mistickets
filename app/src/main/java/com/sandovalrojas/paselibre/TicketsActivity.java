package com.sandovalrojas.paselibre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sandovalrojas.paselibre.adapters.OrderAdapter;
import com.sandovalrojas.paselibre.adapters.TicketAdapter;
import com.sandovalrojas.paselibre.models.Order;
import com.sandovalrojas.paselibre.models.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TicketsActivity extends AppCompatActivity {

    private static final String SERVICE_URL = "http://paselibre.info/api/v1/tickets";
    private static final String API_KEY = "a3240ae77dcb1308e094e24c16698d96";
    private static final String JSON_STATUS_SUCCESS = "success";
    private static final String JSON_MESSAGE = "message";
    private static final String KEY_APIKEY = "api_key";

    private static final String KEY_ORDERID = "order_id";

    TicketAdapter adapter;
    ListView listView;

    private int orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orderId = getIntent().getExtras().getInt("ORDER_ID");

        ArrayList<Ticket> arrayOfTickets = new ArrayList<Ticket>();
        adapter = new TicketAdapter(this, arrayOfTickets);

        listView = (ListView) findViewById(R.id.lvTickets);
        listView.setAdapter(adapter);

        loadTickets();

    }

    private void loadTickets() {

        String url = SERVICE_URL + "?" + KEY_APIKEY + "=" + API_KEY + "&" + KEY_ORDERID + "=" + String.valueOf(this.orderId);

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
                            Ticket ticket = new Ticket(row.getInt("id"), row.getString("event"), row.getString("description"), row.getDouble("price"), row.getInt("quantity"), row.getString("qr"), row.getString("status"));
                            adapter.add(ticket);
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
