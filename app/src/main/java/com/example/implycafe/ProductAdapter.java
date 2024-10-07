package com.example.implycafe;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements View.OnClickListener {
    private final List<Product> productList;
    private final Context context;
    private final onItemClickListener onItemClickListener;

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
        String imageUrl = product.getImageUrl();
        if(imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.productImage);
            holder.productImage.setVisibility(View.VISIBLE);
        } else {
            holder.productImage.setVisibility(View.GONE);
        }

        double price = Double.parseDouble(product.getPrice());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formattedPrice = currencyFormat.format(price);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(formattedPrice);


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
        public ImageView productImage;


        public ProductViewHolder(View itemView){
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);

        }
    }
}
