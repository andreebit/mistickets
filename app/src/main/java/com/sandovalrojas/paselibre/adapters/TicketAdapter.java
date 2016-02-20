package com.sandovalrojas.paselibre.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandovalrojas.paselibre.R;
import com.sandovalrojas.paselibre.models.Order;
import com.sandovalrojas.paselibre.models.Ticket;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by andree on 19/02/16.
 */
public class TicketAdapter extends ArrayAdapter<Ticket> {

    Context context;

    public TicketAdapter(Context context, ArrayList<Ticket> tickets) {
        super(context, 0, tickets);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Ticket ticket = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ticket, parent, false);
        }

        ImageView imQr = (ImageView) convertView.findViewById(R.id.imQr);
        TextView txtNameEvent = (TextView) convertView.findViewById(R.id.txtNameEvent);
        TextView txtDescriptionTicket = (TextView) convertView.findViewById(R.id.txtDescriptionTicket);
        TextView txtPriceTicket = (TextView) convertView.findViewById(R.id.txtPriceTicket);
        TextView txtQuantity = (TextView) convertView.findViewById(R.id.txtQuantity);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);


        Picasso.with(this.context).load(ticket.getQr()).into(imQr);
        txtNameEvent.setText(ticket.getEvent());
        txtDescriptionTicket.setText("tipo: " + ticket.getDescription());
        txtPriceTicket.setText("$ " + String.valueOf(ticket.getPrice()));
        txtQuantity.setText(String.valueOf(ticket.getQuantity()) + " entradas");

        if (ticket.getStatus().equals("enabled")) {
            txtStatus.setText("Pendiente");
        } else {
            txtStatus.setText("Utilizado");
        }

        return convertView;

    }

}
