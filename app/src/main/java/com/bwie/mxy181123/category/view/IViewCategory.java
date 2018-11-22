package com.bwie.mxy181123.category.view;

import com.bwie.mxy181123.bean.Category;
import com.bwie.mxy181123.bean.MessageBean;
import com.bwie.mxy181123.bean.ProductCategory;

import java.util.List;

public interface IViewCategory {

    void getCategory(MessageBean<List<Category>> data);

    void getProductCategory(MessageBean<List<ProductCategory>> data);

    void onFailed(Exception e);
}
