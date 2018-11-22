package com.bwie.mxy181123.category.presenter;


import com.bwie.mxy181123.bean.Category;
import com.bwie.mxy181123.bean.MessageBean;
import com.bwie.mxy181123.bean.ProductCategory;
import com.bwie.mxy181123.category.model.CategoryModel;
import com.bwie.mxy181123.category.view.IViewCategory;
import com.bwie.mxy181123.inter.INetCallBack;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class CategoryPresenter {

    private IViewCategory iv;
    private CategoryModel model;

    /**
     * 初始化
     *
     * @param iv
     */
    public void attach(IViewCategory iv) {
        this.iv = iv;
        model = new CategoryModel();
    }

    /**
     * 获取分类
     */
    public void getCategory() {
        String url = "http://www.zhaoapi.cn/product/getCatagory";
        Type type = new TypeToken<MessageBean<List<Category>>>() {
        }.getType();
        model.getData(url, new INetCallBack() {
            @Override
            public void onSuccess(Object obj) {
                iv.getCategory((MessageBean<List<Category>>) obj);
            }

            @Override
            public void onFailed(Exception e) {
                iv.onFailed(e);
            }
        }, type);
    }

    /**
     * 获取商品子分类
     *
     * @param cid
     */
    public void getProductCategory(int cid) {
        String url = "http://www.zhaoapi.cn/product/getProductCatagory?cid=" + cid;
        Type type = new TypeToken<MessageBean<List<ProductCategory>>>() {
        }.getType();
        model.getData(url, new INetCallBack() {
            @Override
            public void onSuccess(Object obj) {
                iv.getProductCategory((MessageBean<List<ProductCategory>>) obj);
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
