package com.bwie.mxy181123.product.view;

import com.bwie.mxy181123.bean.AddCart;
import com.bwie.mxy181123.bean.MessageBean;
import com.bwie.mxy181123.bean.Product;

import java.util.List;

public interface IViewProduct {

    void getProduct(MessageBean<List<Product>> data);

    void addCart(AddCart data);

    void onFailed(Exception e);
}
