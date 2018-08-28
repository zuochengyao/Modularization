package com.modularization.app.util;

import com.modularization.app.model.recommend.RecommendBody;

import java.util.ArrayList;

public class Utils
{

    //为ViewPager结构化数据
    public static ArrayList<RecommendBody> handleData(RecommendBody body)
    {
        ArrayList<RecommendBody> values = new ArrayList<>();
        String[] titles = body.title.split("@");
        String[] infos = body.info.split("@");
        String[] prices = body.price.split("@");
        String[] texts = body.text.split("@");
        ArrayList<String> urls = body.url;
        int start = 0;
        for (int i = 0; i < titles.length; i++)
        {
            RecommendBody tempValue = new RecommendBody();
            tempValue.title = titles[i];
            tempValue.info = infos[i];
            tempValue.price = prices[i];
            tempValue.text = texts[i];
            tempValue.url = extractData(urls, start, 3);
            start += 3;
            values.add(tempValue);
        }
        return values;
    }

    private static ArrayList<String> extractData(ArrayList<String> source, int start, int interval)
    {
        ArrayList<String> tempUrls = new ArrayList<>();
        for (int i = start; i < start + interval; i++)
        {
            tempUrls.add(source.get(i));
        }
        return tempUrls;
    }
}
