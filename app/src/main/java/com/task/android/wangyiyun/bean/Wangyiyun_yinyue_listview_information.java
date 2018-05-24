package com.task.android.wangyiyun.bean;

/**
 * Created by 刘永飞 on 2018/5/15.
 */

public class Wangyiyun_yinyue_listview_information {
    private int ImageId;
    private String str1;
    private String str2;

    public Wangyiyun_yinyue_listview_information(int ImageId, String str1, String str2)
    {
        this.ImageId=ImageId;
        this.str1=str1;
        this.str2=str2;

    }
    public int getImageId(){return ImageId;}
    public String getStr1(){return str1;}
    public String getStr2(){return str2;}


}
