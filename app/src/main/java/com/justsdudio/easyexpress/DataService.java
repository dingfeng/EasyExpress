package com.justsdudio.easyexpress;

import android.media.Image;

import java.util.Iterator;

/**
 * Created by FD on 2015/9/21.
 */
public interface DataService {
    /*
     *通过快递名称和编号查找具体的跟踪信息
     */
    public ExpressInfo searchExpressInfoById(String expressName, String expressId);
    /*
     *根据快递公司的名称获得图标
     */
    public Image  getExpressIconByName(String expressName);
}
