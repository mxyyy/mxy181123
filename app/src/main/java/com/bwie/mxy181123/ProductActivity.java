package com.bwie.mxy181123;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bwie.mxy181123.adapter.ProductAdapter;
import com.bwie.mxy181123.bean.AddCart;
import com.bwie.mxy181123.bean.MessageBean;
import com.bwie.mxy181123.bean.Product;
import com.bwie.mxy181123.product.presenter.ProductPresenter;
import com.bwie.mxy181123.product.view.IViewProduct;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements IViewProduct {

    private XRecyclerView rvProduct;
    private ProductPresenter presenter;
    private int page = 1;
    private boolean isLoadMore = false;
    private List<Product> list;
    private ProductAdapter adapter;
    private int pscid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initView();
        initData();

        // 初始化列表
        rvProduct.setPullRefreshEnabled(true);
        rvProduct.setLoadingMoreEnabled(true);
        rvProduct.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                page = 1;
                presenter.getProduct(pscid, page);
            }

            @Override
            public void onLoadMore() {
                isLoadMore = true;
                if (page == 3) {
                    Toast.makeText(ProductActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    rvProduct.loadMoreComplete();
                } else {
                    page++;
                    presenter.getProduct(pscid, page);
                }
            }
        });
        list = new ArrayList<>();
        adapter = new ProductAdapter(this, list);
        // 商品列表的长按加购事件
        adapter.setOnProductLongClickListener(new ProductAdapter.OnProductLongClickListener() {
            @Override
            public void onProductLongClick(View view, int position) {
                Product product = list.get(position);
                int pid = product.getPid();
                presenter.addCart(pid);
            }
        });
        rvProduct.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        rvProduct.setAdapter(adapter);
    }

    private void initData() {
        presenter = new ProductPresenter();
        presenter.attach(this);

        // 获取数据
        Intent intent = getIntent();
        pscid = intent.getIntExtra("pscid", 1);
        presenter.getProduct(pscid, page);
    }

    private void initView() {
        rvProduct = findViewById(R.id.rv_product);
    }

    @Override
    public void getProduct(MessageBean<List<Product>> data) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvProduct.refreshComplete();
                rvProduct.loadMoreComplete();
            }
        }, 2000);
        if (data != null) {
            List<Product> data1 = data.getData();
            if (data1 != null) {
                if (!isLoadMore) {
                    list.clear();
                }
                list.addAll(data1);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void addCart(AddCart data) {
        if (data.getCode().equals("0")) {
            Toast.makeText(this, "加入购物车成功~", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CartActivity.class));
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
