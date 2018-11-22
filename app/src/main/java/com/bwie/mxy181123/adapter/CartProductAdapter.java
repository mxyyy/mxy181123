package com.bwie.mxy181123.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bwie.mxy181123.R;
import com.bwie.mxy181123.bean.Product;
import com.bwie.mxy181123.utils.Https2http;
import com.bwie.mxy181123.widget.AddDecreaseWidget;


import java.util.List;



public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> list;


    public CartProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    // 二级列表商品变化监听
    public interface OnProductClickListener {
        void onProductClick(int position, boolean isChecked);
    }

    private OnProductClickListener onProductClickListener;

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    // 加减器监听
    public interface OnAddDecreaseProductListener {
        void onChange(int position, int num);
    }

    private OnAddDecreaseProductListener onAddDecreaseProductListener;

    public void setOnAddDecreaseProductListener(OnAddDecreaseProductListener onAddDecreaseProductListener) {
        this.onAddDecreaseProductListener = onAddDecreaseProductListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cart_product, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Product product = list.get(position);
        String[] images = product.getImages().replace("|", ",").split(",");
        Glide.with(context).load(Https2http.replace(images[0])).into(holder.imgLogo);
        holder.txtTitle.setText(product.getTitle());
        holder.txtBargainPrice.setText("优惠价：￥" + product.getBargainPrice());

        holder.advWidget.setOnAddDecreaseClickListener(new AddDecreaseWidget.OnAddDecreaseClickListener() {
            @Override
            public void onAddClick(int num) {
                product.setNum(num);

                if (onAddDecreaseProductListener != null) {
                    onAddDecreaseProductListener.onChange(position, num);
                }
            }

            @Override
            public void onDecreaseClick(int num) {
                product.setNum(num);

                if (onAddDecreaseProductListener != null) {
                    onAddDecreaseProductListener.onChange(position, num);
                }
            }
        });

        holder.cbProduct.setOnCheckedChangeListener(null);
        holder.cbProduct.setChecked(product.isChecked());
        holder.cbProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                product.setChecked(b);

                if (onProductClickListener != null) {
                    onProductClickListener.onProductClick(position, b);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbProduct;
        private ImageView imgLogo;
        private TextView txtTitle;
        private TextView txtBargainPrice;
        private AddDecreaseWidget advWidget;

        public ViewHolder(View itemView) {
            super(itemView);
            cbProduct = itemView.findViewById(R.id.cb_product);
            imgLogo = itemView.findViewById(R.id.img_logo_pro);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtBargainPrice = itemView.findViewById(R.id.txt_bargain_price);
            advWidget = itemView.findViewById(R.id.adv_widget);
        }
    }
}
