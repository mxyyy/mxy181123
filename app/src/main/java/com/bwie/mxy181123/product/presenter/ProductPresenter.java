package com.bwie.mxy181123.product.presenter;


import com.bwie.mxy181123.bean.AddCart;
import com.bwie.mxy181123.bean.MessageBean;
import com.bwie.mxy181123.bean.Product;
import com.bwie.mxy181123.inter.INetCallBack;
import com.bwie.mxy181123.product.model.ProductModel;
import com.bwie.mxy181123.product.view.IViewProduct;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ProductPresenter {

    private IViewProduct iv;
    private ProductModel model;

    /**
     * 初始化
     * @param iv
     */
    public void attach(IViewProduct iv) {
        this.iv = iv;
        model = new ProductModel();
    }

    /**
     * 获取商品列表
     */
    public void getProduct(int pscid, int page) {
        String url = "http://www.zhaoapi.cn/product/getProducts?pscid=" + pscid + "&page=" + page;
        Type type = new TypeToken<MessageBean<List<Product>>>() {
        }.getType();
        model.getData(url, new INetCallBack() {
            @Override
            public void onSuccess(Object obj) {
                iv.getProduct((MessageBean<List<Product>>) obj);
            }

            @Override
            public void onFailed(Exception e) {
                iv.onFailed(e);
            }
        }, type);
    }

    /**
     * 添加购物车
     * @param pid
     */
    public void addCart(int pid) {
        String url = "http://www.zhaoapi.cn/product/addCart?uid=71&pid=" + pid;
        Type type = new TypeToken<AddCart>() {
        }.getType();
        model.getData(url, new INetCallBack() {
            @Override
            public void onSuccess(Object obj) {
                iv.addCart((AddCart) obj);
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
