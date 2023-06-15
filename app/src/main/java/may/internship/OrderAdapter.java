package may.internship;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {

    Context context;
    ArrayList<OrderList> productArrayList;
    SharedPreferences sp;

    public OrderAdapter(Context context, ArrayList<OrderList> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
        sp = context.getSharedPreferences(ConstantData.PREF, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public OrderAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order,parent,false);
        return new OrderAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView orderId,name,email,contact,address,city,paymentMethod;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.custom_order_id);
            name = itemView.findViewById(R.id.custom_order_name);
            email = itemView.findViewById(R.id.custom_order_email);
            contact = itemView.findViewById(R.id.custom_order_contact);
            address = itemView.findViewById(R.id.custom_order_address);
            city = itemView.findViewById(R.id.custom_order_city);
            paymentMethod = itemView.findViewById(R.id.custom_order_payment_method);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyHolder holder, int position) {
        holder.orderId.setText("Order Id : "+productArrayList.get(position).getId());
        holder.name.setText(productArrayList.get(position).getName());
        holder.email.setText(productArrayList.get(position).getEmail());
        holder.contact.setText(productArrayList.get(position).getContact());
        holder.address.setText(productArrayList.get(position).getAddress());
        holder.city.setText(productArrayList.get(position).getCity());
        if(productArrayList.get(position).getPaymentMethod().equalsIgnoreCase(context.getResources().getString(R.string.online))) {
            holder.paymentMethod.setText(productArrayList.get(position).getPaymentMethod()+" ("+productArrayList.get(position).getTransactionId()+")");
        }
        else{
            holder.paymentMethod.setText(productArrayList.get(position).getPaymentMethod());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantData.ORDER_POSITION, String.valueOf(position)).commit();
                new CommonMethod(context, OrderDetailActivity.class);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
