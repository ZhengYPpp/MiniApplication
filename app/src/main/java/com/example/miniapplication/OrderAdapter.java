package com.example.miniapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>
{

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;



    private List<Order> Orders;


    public  OrderAdapter (List<Order> Orders)
    {
        this.Orders = Orders;
    }



    public  class OrderViewHolder extends RecyclerView.ViewHolder
    {
        public TextView orderId, orderType,  senderName, senderPhone, senderAddress, receiverName, receiverPhone, receiverAddress;

        public OrderViewHolder(@NonNull View itemView)
        {
            super(itemView);

            orderId = (TextView) itemView.findViewById(R.id.order_id);
            orderType = (TextView) itemView.findViewById(R.id.order_type);
            senderName = (TextView) itemView.findViewById(R.id.senderName);
            senderPhone = (TextView) itemView.findViewById(R.id.senderPhone);
            senderAddress = (TextView) itemView.findViewById(R.id.senderAddress);
            receiverName = (TextView) itemView.findViewById(R.id.receiverName);
            receiverPhone = (TextView) itemView.findViewById(R.id.receiverPhone);
            receiverAddress = (TextView) itemView.findViewById(R.id.receiverAddress);


        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_show_order, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();

        return  new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int position)
    {
        String currentUserID =mAuth.getCurrentUser().getUid();
        Order Order = Orders.get(position);

        // orderId, orderType,  senderName, senderPhone, senderAddress, receiverName, receiverPhone, receiverAddress

//        String orderId = Order.getOrderId();
//        String orderType = Order.getOrderType();
//        String senderName = Order.getSenderName();

        holder.orderId.setText(Order.getOrderId());
        holder.orderType.setText(Order.getOrderType());
        holder.senderName.setText(Order.getSenderName());
        holder.senderPhone.setText(Order.getSenderPhone());
        holder.senderAddress.setText(Order.getSenderAddress());
        holder.receiverName.setText(Order.getReceiverName());
        holder.receiverPhone.setText(Order.getReceiverPhone());
        holder.receiverAddress.setText(Order.getReceiverAddress());



//        final String getTitleText = Orders.get(position).getTitle();
//        final String getDescriptionText = Orders.get(position).getDescription();
//        final String getTimeline = Orders.get(position).getTimeline();
//        final String getId = Orders.get(position).getId();
//
//        holder.itemView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//                Intent OrderDetailsIntent = new Intent(context, OrderDetailsActivity.class);
//
//                OrderDetailsIntent.putExtra("TitleText", getTitleText);
//                OrderDetailsIntent.putExtra("DescriptionText", getDescriptionText);
//                OrderDetailsIntent.putExtra("TimelineText", getTimeline);
//                OrderDetailsIntent.putExtra("Id", getId);
//
//                context.startActivity(OrderDetailsIntent);
//            }
//        });
    }

    @Override
    public int getItemCount()
    {
        return Orders.size();
    }


}
