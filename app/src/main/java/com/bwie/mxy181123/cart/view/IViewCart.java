package com.bwie.mxy181123.cart.view;

import com.bwie.mxy181123.bean.MessageBean;
import com.bwie.mxy181123.bean.Product;
import com.bwie.mxy181123.bean.Shopper;

import java.util.List;


public interface IViewCart {

    void getCart(MessageBean<List<Shopper<List<Product>>>> data);

    void onFailed(Exception e);
}
