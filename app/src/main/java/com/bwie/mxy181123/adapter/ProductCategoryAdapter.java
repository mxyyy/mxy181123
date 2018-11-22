package com.bwie.mxy181123.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.mxy181123.R;
import com.bwie.mxy181123.bean.ProductCategory;

import java.util.List;

/**
 * 子商品适配器
 */


public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {
    private Context context;
    private List<ProductCategory> list;

    public ProductCategoryAdapter(Context context, List<ProductCategory> list) {
        this.context = context;
        this.list = list;
    }

    public interface OnChildProductLongClickListener {
        void onChildProductLongClick(View view, int position, ProductCategory obj);
    }

    private OnChildProductLongClickListener childProductLongClickListener;

    public void setOnChildProductLongClickListener(OnChildProductLongClickListener childProductLongClickListener) {
        this.childProductLongClickListener = childProductLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_product_category, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductCategory productCategory = list.get(position);
        holder.txtProductName.setText(productCategory.getName());

        // 配置子商品的列表
        ChildProductAdapter adapter = new ChildProductAdapter(context, productCategory.getList());
        // 设置子商品的条目点击事件
        adapter.setOnChildProductLongClickListener(new ChildProductAdapter.OnChildProductLongClickListener() {
            @Override
            public void onChildProductLongClick(View view, int position) {
                // 设置接口回调回传给activity
                if (childProductLongClickListener != null) {
                    childProductLongClickListener.onChildProductLongClick(view, position, productCategory);
                }
            }
        });
        holder.rvProductCategory.setLayoutManager(new GridLayoutManager(context, 5, LinearLayoutManager.VERTICAL, false));
        holder.rvProductCategory.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtProductName;
        private RecyclerView rvProductCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            rvProductCategory = itemView.findViewById(R.id.rv_product_category);
        }
    }
}
