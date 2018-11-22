package com.bwie.mxy181123.cart.model;

import com.bwie.mxy181123.inter.INetCallBack;
import com.bwie.mxy181123.utils.HttpUtils;

import java.lang.reflect.Type;


public class CartModel {

    /**
     * model层获取数据
     * @param url
     * @param callBack
     * @param type
     */
    public void getData(String url, INetCallBack callBack, Type type) {
        HttpUtils.getInstance().get(url, callBack, type);
    }
}
