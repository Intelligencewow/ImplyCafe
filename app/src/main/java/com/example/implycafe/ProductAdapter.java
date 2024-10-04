package com.example.implycafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements View.OnClickListener {
    private List<Product> productList;
    private Context context;
    private onItemClickListener onItemClickListener;

    @Override
    public void onClick(View v) {

    }

    public interface onItemClickListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
    public ProductAdapter(Context context, List<Product> productList, onItemClickListener listener) {
        this.productList = productList;
        this.context = context;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(product.getPrice());

        holder.itemView.setOnClickListener(v -> {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if(onItemClickListener != null){
                onItemClickListener.onItemLongClick(position);
                return true;
            }
            return false;
        });

    }

    public int getItemCount(){
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView productName;
        public TextView productDescription;
        public TextView productPrice;

        public ProductViewHolder(View itemView){
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);

        }
    }
}
