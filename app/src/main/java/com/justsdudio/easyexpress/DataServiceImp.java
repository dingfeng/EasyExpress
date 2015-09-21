package com.justsdudio.easyexpress;

import android.media.Image;

/**
 * Created by FD on 2015/9/21.
 */
public class DataServiceImp implements DataService {

    private static DataService dataService = new DataServiceImp();
    private DataServiceImp()
    {
        //nothing
    }
    public static DataService getNewInstance()
    {
        return dataService;
    }

    @Override
    public ExpressInfo searchExpressInfoById(String expressName, String expressId) {
        return null;
    }

    @Override
    public Image getExpressIconByName(String expressName) {
        return null;
    }
}
