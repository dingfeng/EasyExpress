package com.justsdudio.easyexpress;

/**
 * Created by FD on 2015/9/21.
 */

import android.media.Image;

import java.util.Iterator;

/**
 * 快递信息类
 * 包括了时间和具体的追踪信息
 */
public class ExpressDetailInfo {
    //时间
    private String time;
    //追踪信息
    private String dsp;

    public ExpressDetailInfo(String time, String dsp)
    {
        this.time  = time;
        this.dsp = dsp;
    }

    public String getTime()
    {
        return this.time;
    }

}
