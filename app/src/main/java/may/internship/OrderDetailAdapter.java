package may.internship;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyHolder> {

    Context context;
    ArrayList<CartList> productArrayList;
    SharedPreferences sp;
    public OrderDetailAdapter(Context context, ArrayList<CartList> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
        sp = context.getSharedPreferences(ConstantData.PREF,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public OrderDetailAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order_detail,parent,false);
        return new OrderDetailAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,price,qty;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_order_detail_image);
            name = itemView.findViewById(R.id.custom_order_detail_name);
            qty = itemView.findViewById(R.id.custom_order_detail_qty);
            price = itemView.findViewById(R.id.custom_order_detail_price);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.MyHolder holder, int position) {
        holder.name.setText(productArrayList.get(position).getName());

        holder.qty.setText("Qty : "+productArrayList.get(position).getQty());

        holder.price.setText(ConstantData.PRICE_SYMBOL+productArrayList.get(position).getPrice()+"/"+productArrayList.get(position).getUnit());
        holder.imageView.setImageResource(productArrayList.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantData.PRODUCT_NAME,productArrayList.get(position).getName()).commit();
                sp.edit().putString(ConstantData.PRODUCT_PRICE,productArrayList.get(position).getPrice()).commit();
                sp.edit().putString(ConstantData.PRODUCT_UNIT,productArrayList.get(position).getUnit()).commit();
                sp.edit().putString(ConstantData.PRODUCT_IMAGE, String.valueOf(productArrayList.get(position).getImage())).commit();
                sp.edit().putString(ConstantData.PRODUCT_DESCRIPTION,productArrayList.get(position).getDescription()).commit();
                new CommonMethod(context,ProductDetailActivity.class);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
