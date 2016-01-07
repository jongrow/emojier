package com.xinmei365.emojsdk.domain;

/**
 * Created by xinmei on 15/12/4.
 */
public class CharEntity {

    public int start; //字符开始位置
    public int end;  //字符结束位置，包含空格

    public CharType mCharType; //字符类型，正常，本地emoj,online emoj
    public String mOriginalStr; //最初原始字符串 #|\smile_1:candf1newcar064001|

    private String mEmojKeyID;  //online emoj 的key与id的组合 James_1:candf1newcar064001:
    public String[] mEmojKeyAndID;  //存储online emoj 的key与id
    public String mEmojKey; //online emojd的key
    public String mEmojID; //online emoj的id

    public String mEmojUnicode; //local emoj的 unicode值


    public enum CharType{
        Normal,LocalEMOJ,OnlineEmoj;
    }

    public CharEntity(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public CharEntity(String originStr,int start,CharType type) {
        this.mOriginalStr = originStr;
        this.start = start;
        this.mCharType = type;
        this.end = start + originStr.length();
    }

    public String getEmojKeyID() {
        return mEmojKeyID;
    }

    public void setEmojKeyID(String emojKeyID) {
        this.mEmojKeyID = emojKeyID;
        mEmojKeyAndID = emojKeyID.split("_");
        mEmojKey = mEmojKeyAndID[0];
        mEmojID = mEmojKeyAndID[1];
    }
}
