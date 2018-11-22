package com.bwie.mxy181123;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bwie.mxy181123.adapter.CategoryAdapter;
import com.bwie.mxy181123.adapter.ProductCategoryAdapter;
import com.bwie.mxy181123.bean.Category;
import com.bwie.mxy181123.bean.MessageBean;
import com.bwie.mxy181123.bean.ProductCategory;
import com.bwie.mxy181123.category.presenter.CategoryPresenter;
import com.bwie.mxy181123.category.view.IViewCategory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IViewCategory {

    private RecyclerView rvCategory;
    private RecyclerView rvProductCategory;
    private CategoryPresenter presenter;
    private List<Category> categories;
    private CategoryAdapter categoryAdapter;
    private List<ProductCategory> productCategories;
    private ProductCategoryAdapter productCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        // 配置分类列表
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categories);
        // 分类条目的点击事件，点击右边切换到对应数据
        categoryAdapter.setOnCategoryClickListener(new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(View view, int position) {
                int cid = categories.get(position).getCid();
                presenter.getProductCategory(cid);
            }
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        rvCategory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvCategory.setAdapter(categoryAdapter);

        // 配置商品分类列表
        productCategories = new ArrayList<>();
        productCategoryAdapter = new ProductCategoryAdapter(this, productCategories);
        productCategoryAdapter.setOnChildProductLongClickListener(new ProductCategoryAdapter.OnChildProductLongClickListener() {
            @Override
            public void onChildProductLongClick(View view, int position, ProductCategory obj) {
                ProductCategory.ListBean listBean = obj.getList().get(position);
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("pscid", listBean.getPscid());
                startActivity(intent);
//                Toast.makeText(MainActivity.this, listBean.getPscid() + "", Toast.LENGTH_SHORT).show();
            }
        });
        rvProductCategory.setLayoutManager(new LinearLayoutManager(this));
        rvProductCategory.setAdapter(productCategoryAdapter);
    }

    private void initData() {
        presenter = new CategoryPresenter();
        presenter.attach(this);
        presenter.getCategory();
        presenter.getProductCategory(1);
    }

    private void initView() {
        rvCategory = findViewById(R.id.rv_category);
        rvProductCategory = findViewById(R.id.rv_product_category);
    }

    @Override
    public void getCategory(MessageBean<List<Category>> data) {
        if (data != null) {
            List<Category> data1 = data.getData();
            if (data1 != null) {
                categories.clear();
                categories.addAll(data1);
                categoryAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getProductCategory(MessageBean<List<ProductCategory>> data) {
        if (data != null) {
            List<ProductCategory> data1 = data.getData();
            if (data1 != null) {
                productCategories.clear();
                productCategories.addAll(data1);
                productCategoryAdapter.notifyDataSetChanged();
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
