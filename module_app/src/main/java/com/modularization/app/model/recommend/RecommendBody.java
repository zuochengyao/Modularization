package com.modularization.app.model.recommend;

import com.modularization.app.model.monitor.Monitor;
import com.modularization.app.model.monitor.emevent.EMEvent;
import com.modularization.common.base.BaseModel;

import java.util.ArrayList;

public class RecommendBody extends BaseModel
{
    public int type;
    public String logo;
    public String title;
    public String info;
    public String price;
    public String text;
    public String site;
    public String from;
    public String zan;
    public ArrayList<String> url;

    //视频专用
    public String thumb;
    public String resource;
    public String resourceID;
    public String adid;
    public ArrayList<Monitor> startMonitor;
    public ArrayList<Monitor> middleMonitor;
    public ArrayList<Monitor> endMonitor;
    public String clickUrl;
    public ArrayList<Monitor> clickMonitor;
    public EMEvent event;
}
