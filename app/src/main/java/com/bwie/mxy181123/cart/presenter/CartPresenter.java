package com.bwie.mxy181123.cart.presenter;


import com.bwie.mxy181123.bean.MessageBean;
import com.bwie.mxy181123.bean.Product;
import com.bwie.mxy181123.bean.Shopper;
import com.bwie.mxy181123.cart.model.CartModel;
import com.bwie.mxy181123.cart.view.IViewCart;
import com.bwie.mxy181123.inter.INetCallBack;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class CartPresenter {

    private IViewCart iv;
    private CartModel model;

    /**
     * 初始化
     * @param iv
     */
    public void attach(IViewCart iv) {
        this.iv = iv;
        model = new CartModel();
    }

    /**
     * 获取商品列表
     */
    public void getCart() {
        String url = "http://www.zhaoapi.cn/product/getCarts?uid=71";
        Type type = new TypeToken<MessageBean<List<Shopper<List<Product>>>>>() {
        }.getType();
        model.getData(url, new INetCallBack() {
            @Override
            public void onSuccess(Object obj) {
                iv.getCart((MessageBean<List<Shopper<List<Product>>>>) obj);
            }

            @Override
            public void onFailed(Exception e) {
                iv.onFailed(e);
            }
        }, type);
    }

    /**
     * 置空解耦
     */
    public void detach() {
        if (iv != null) {
            iv = null;
        }
    }
}
