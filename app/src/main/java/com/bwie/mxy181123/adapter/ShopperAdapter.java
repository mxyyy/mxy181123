package com.bwie.mxy181123.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bwie.mxy181123.R;
import com.bwie.mxy181123.bean.Product;
import com.bwie.mxy181123.bean.Shopper;

import java.util.List;



public class ShopperAdapter extends RecyclerView.Adapter<ShopperAdapter.ViewHolder> {
    private Context context;
    private List<Shopper<List<Product>>> list;

    public ShopperAdapter(Context context, List<Shopper<List<Product>>> list) {
        this.context = context;
        this.list = list;
    }

    public interface OnShopperClickListener {
        void onShopperClick(int position, boolean isChecked);
    }

    private OnShopperClickListener onShopperClickListener;

    public void setOnShopperClickListener(OnShopperClickListener onShopperClickListener) {
        this.onShopperClickListener = onShopperClickListener;
    }

    private CartProductAdapter.OnAddDecreaseProductListener onAddDecreaseProductListener;

    public void setOnAddDecreaseProductListener(CartProductAdapter.OnAddDecreaseProductListener onAddDecreaseProductListener) {
        this.onAddDecreaseProductListener = onAddDecreaseProductListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_shopper, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Shopper<List<Product>> shopper = list.get(position);
        holder.txtShopperName.setText(shopper.getSellerName());
        List<Product> products = shopper.getList();
        final CartProductAdapter adapter = new CartProductAdapter(context, products);
        // 设置二级监听
        adapter.setOnProductClickListener(new CartProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(int position, boolean isChecked) {
                if (!isChecked) {
                    shopper.setChecked(false);

                    if (onShopperClickListener != null) {
                        onShopperClickListener.onShopperClick(position, false);
                    }
                } else {
                    boolean isAllProductSelected = true;
                    List<Product> list = shopper.getList();
                    for (Product product : list) {
                        if (!product.isChecked()) {
                            isAllProductSelected = false;
                            break;
                        }
                    }
                    shopper.setChecked(isAllProductSelected);
                    if (onShopperClickListener != null) {
                        onShopperClickListener.onShopperClick(position, true);
                    }
                }
                notifyDataSetChanged();
            }
        });
        if (onAddDecreaseProductListener != null) {
            onAddDecreaseProductListener = null;
        }
        holder.rvCartProduct.setLayoutManager(new LinearLayoutManager(context));
        holder.rvCartProduct.setAdapter(adapter);

        // 解决Checkbox的复用问题
        holder.cbShopper.setOnCheckedChangeListener(null);
        holder.cbShopper.setChecked(shopper.isChecked());
        holder.cbShopper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shopper.setChecked(b);

                List<Product> products = shopper.getList();
                for (Product product : products) {
                    product.setChecked(b);
                }
                adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbShopper;
        private TextView txtShopperName;
        private RecyclerView rvCartProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            cbShopper = itemView.findViewById(R.id.cb_shopper);
            txtShopperName = itemView.findViewById(R.id.txt_shopper_name);
            rvCartProduct = itemView.findViewById(R.id.rv_cart_product);
        }
    }
}
