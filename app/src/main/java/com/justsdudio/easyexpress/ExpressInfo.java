package com.justsdudio.easyexpress;

import android.media.Image;

import java.util.Iterator;
import java.util.List;

/**
 * Created by FD on 2015/9/21.
 */
public class ExpressInfo {
    //详细信息
    private List<ExpressDetailInfo> expressDataList;
    //状态
    private String state;
    //快递单据编号
    private String id;

    public ExpressInfo(List<ExpressDetailInfo> expressDataList, String state, String id) {
        this.expressDataList = expressDataList;
        this.state = state;
        this.id = id;
    }

    public String getId()
    {
        return this.id;
    }

    public Iterator<ExpressDetailInfo> getInfos()
    {
      return this.expressDataList.iterator();
    }
    public String getState()
    {
      return this.state;
    }
}
