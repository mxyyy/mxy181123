package com.bwie.mxy181123.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bwie.mxy181123.R;
import com.bwie.mxy181123.bean.Product;
import com.bwie.mxy181123.utils.Https2http;


import java.util.List;



public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> list;

    public ProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }


    public interface OnProductLongClickListener {
        void onProductLongClick(View view, int position);
    }

    private OnProductLongClickListener productLongClickListener;

    public void setOnProductLongClickListener(OnProductLongClickListener productLongClickListener) {
        this.productLongClickListener = productLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_product, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String[] images = list.get(position).getImages().replace("|", ",").split(",");
        Glide.with(context).load(Https2http.replace(images[0])).into(holder.imgLogo);
        holder.txtProductName.setText(list.get(position).getTitle());
        holder.txtPrice.setText("原价：￥" + list.get(position).getPrice());
        // 设置文字删除线
        holder.txtPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtBargainPrice.setText("优惠价：￥" + list.get(position).getBargainPrice());

        // 给条目设置长按回调
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (productLongClickListener != null) {
                    productLongClickListener.onProductLongClick(view, position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView txtProductName;
        private TextView txtPrice;
        private TextView txtBargainPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.img_logo);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            txtPrice = itemView.findViewById(R.id.txt_price);
            txtBargainPrice = itemView.findViewById(R.id.txt_bargain_price);
        }
    }
}
