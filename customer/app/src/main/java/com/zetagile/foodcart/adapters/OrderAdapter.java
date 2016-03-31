package com.zetagile.foodcart.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.constants.OrderStatus;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.fragments.orders.SingleInvoiceFragment;
import com.zetagile.foodcart.util.PriceAndCurrency;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Activity activity;
    List<Order> order_list;

    public OrderAdapter(Activity activity, List<Order> order_list) {
        this.activity = activity;
        this.order_list = order_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.from(activity).inflate(R.layout.invoice_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(orderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (order_list.get(position).getOrderStatus().equals(OrderStatus.PLACED.name())
                || order_list.get(position).getOrderStatus().equals(OrderStatus.PROCESSING.name()))
            holder.tv_token.setText(order_list.get(position).getToken() + "");
        else {
            holder.tv_token.setVisibility(View.GONE);
            holder.tv_token_label.setVisibility(View.GONE);
        }

        holder.tv_order_id.setText(order_list.get(position).getOrderId());
        PriceAndCurrency.setPriceWithPrecision(holder.tv_total_amount, order_list.get(position).getTotalAmount());

        holder.tv_type.setText(order_list.get(position).getOrderType());

        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mma");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        holder.tv_order_date.setText(dateFormatter.format(order_list.get(position).getOrderDate()));
        holder.tv_delivery_time.setText(timeFormatter.format(order_list.get(position).getDeliveryTime()));
        holder.tv_time.setText(timeFormatter.format(order_list.get(position).getOrderDate()));
        holder.tv_status.setText(order_list.get(position).getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return order_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_order_id;
        TextView tv_token;
        TextView tv_total_amount;
        TextView tv_delivery_time;
        TextView tv_time;
        TextView tv_type;
        TextView tv_status;
        TextView tv_order_date;
        TextView tv_token_label;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_order_id = (TextView) itemView.findViewById(R.id.invoice_order_id);
            tv_total_amount = (TextView) itemView.findViewById(R.id.invoice_amount);
            tv_delivery_time = (TextView) itemView.findViewById(R.id.delivery_time);
            tv_time = (TextView) itemView.findViewById(R.id.invoice_time);
            tv_type = (TextView) itemView.findViewById(R.id.invoice_order_type);
            tv_token = (TextView) itemView.findViewById(R.id.invoice_token);
            tv_status = (TextView) itemView.findViewById(R.id.invoice_order_status);
            tv_order_date = (TextView) itemView.findViewById(R.id.order_date);
            tv_token_label = (TextView) itemView.findViewById(R.id.token_label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SingleInvoiceFragment.createFragment(activity.getFragmentManager(), order_list.get(getAdapterPosition()).getOrderId());
                }
            });

        }
    }

}

 /*   @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OrderViewHolder holder = null;

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.invoice_list_item, parent, false);
            holder = new OrderViewHolder();
            holder.tv_token = (TextView) row.findViewById(R.id.invoice_token);

            holder.tv_order_id = (TextView) row.findViewById(R.id.invoice_order_id);
            holder.tv_total_amount = (TextView) row.findViewById(R.id.invoice_amount);
            holder.tv_delivery_time = (TextView) row.findViewById(R.id.delivery_time);
            holder.tv_time = (TextView) row.findViewById(R.id.invoice_time);
            holder.tv_type = (TextView) row.findViewById(R.id.invoice_order_type);

            row.setTag(holder);
        } else
            holder = (OrderViewHolder) row.getTag();

        if (context instanceof InvoiceActivity)
            holder.tv_token.setText(order_list.get(position).getToken() + "");
        else
            holder.tv_token.setVisibility(View.GONE);

        holder.tv_order_id.setText(order_list.get(position).getOrderId());
        holder.tv_total_amount.setText(order_list.get(position).getTotalAmount() + "");
        holder.tv_type.setText(order_list.get(position).getOrderType());

        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mma dd-MM-yy");

        holder.tv_delivery_time.setText(timeFormatter.format(order_list.get(position).getDeliveryTime()));
        holder.tv_time.setText(timeFormatter.format(order_list.get(position).getOrderDate()));

        return row;
    }

    class OrderViewHolder {
        TextView tv_order_id;
        TextView tv_token;
        TextView tv_total_amount;
        TextView tv_delivery_time;
        TextView tv_time;
        TextView tv_type;
    }
}
*/
