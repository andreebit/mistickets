package com.sandovalrojas.paselibre.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sandovalrojas.paselibre.R;
import com.sandovalrojas.paselibre.models.Order;

import java.util.ArrayList;

/**
 * Created by andree on 19/02/16.
 */
public class OrderAdapter  extends ArrayAdapter<Order> {

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Order order = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order, parent, false);
        }

        TextView tvNumberOrder = (TextView) convertView.findViewById(R.id.txtNumOrder);
        TextView tvDateOrder = (TextView) convertView.findViewById(R.id.txtDateOrder);


        tvNumberOrder.setText(order.getNumber());
        tvDateOrder.setText(order.getDateTime());

        return convertView;
    }

}
