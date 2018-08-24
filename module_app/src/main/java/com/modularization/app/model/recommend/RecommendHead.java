package com.modularization.app.model.recommend;

import com.modularization.common.base.BaseModel;

import java.util.ArrayList;

public class RecommendHead extends BaseModel
{
    public ArrayList<String> ads;
    public ArrayList<String> middle;
    public ArrayList<RecommendFooter> footer;
}
