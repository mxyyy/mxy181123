package com.bwie.mxy181123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.mxy181123.adapter.ShopperAdapter;
import com.bwie.mxy181123.bean.MessageBean;
import com.bwie.mxy181123.bean.Product;
import com.bwie.mxy181123.bean.Shopper;
import com.bwie.mxy181123.cart.presenter.CartPresenter;
import com.bwie.mxy181123.cart.view.IViewCart;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements IViewCart {

    private RecyclerView rvShopper;
    private CheckBox cbCheckall;
    private TextView cbTotalPrice;
    private CartPresenter presenter;
    private List<Shopper<List<Product>>> list;
    private ShopperAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        initData();

        // 初始化数据
        list = new ArrayList<>();
        adapter = new ShopperAdapter(this, list);
        adapter.setOnShopperClickListener(new ShopperAdapter.OnShopperClickListener() {
            @Override
            public void onShopperClick(int position, boolean isChecked) {
                if (!isChecked) {
                    cbCheckall.setChecked(isChecked);
                } else {
                    boolean isAllShopperSelected = true;
                    for (Shopper<List<Product>> listShopper : list) {
                        if (!listShopper.isChecked()) {
                            isAllShopperSelected = false;
                            break;
                        }
                    }
                    cbCheckall.setChecked(isAllShopperSelected);
                }
            }
        });
        rvShopper.setLayoutManager(new LinearLayoutManager(this));
        rvShopper.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvShopper.setAdapter(adapter);

    }

    private void initData() {
        presenter = new CartPresenter();
        presenter.attach(this);
        presenter.getCart();
    }

    private void initView() {
        rvShopper = findViewById(R.id.rv_shopper);
        cbCheckall = findViewById(R.id.cb_checkall);
        cbTotalPrice = findViewById(R.id.cb_total_price);
    }

    @Override
    public void getCart(MessageBean<List<Shopper<List<Product>>>> data) {
        if (data != null) {
            List<Shopper<List<Product>>> data1 = data.getData();
            if (data1 != null) {
//                list.clear();
                list.addAll(data1);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFailed(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detach();
        }
    }
}
