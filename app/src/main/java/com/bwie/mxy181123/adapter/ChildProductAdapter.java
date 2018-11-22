package com.bwie.mxy181123.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.mxy181123.R;
import com.bwie.mxy181123.bean.ProductCategory;


import java.util.List;

/**
 * 子商品类型的适配器
 *
 */


public class ChildProductAdapter extends RecyclerView.Adapter<ChildProductAdapter.ViewHolder> {
    private Context context;
    private List<ProductCategory.ListBean> list;

    public ChildProductAdapter(Context context, List<ProductCategory.ListBean> list) {
        this.context = context;
        this.list = list;
    }


    public interface OnChildProductLongClickListener {
        void onChildProductLongClick(View view, int position);
    }

    private OnChildProductLongClickListener childProductLongClickListener;

    public void setOnChildProductLongClickListener(OnChildProductLongClickListener childProductLongClickListener) {
        this.childProductLongClickListener = childProductLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_child_product, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtShow.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(holder.imgLogo);

        // 设置子商品列表的item点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (childProductLongClickListener != null) {
                    childProductLongClickListener.onChildProductLongClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView txtShow;

        public ViewHolder(View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.img_logo);
            txtShow = itemView.findViewById(R.id.txt_show);
        }
    }
}
