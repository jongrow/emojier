package com.xinmei365.emojsdk.domain;

import android.text.SpannableStringBuilder;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by xinmei on 15/12/4.
 */
public class EMReceiveTxtEntity {

    public String originReceiveTxt;

    public SpannableStringBuilder mSpanStrBuild;

    public SpannableStringBuilder mFinalSpanSB;

    public String[] mSplitTxts;

    public Map<Integer,CharEntity> allContent;
    public ArrayList<EMCandiateProperty> mNoBitMapEmojTagEntities;//该集合中的EmojTagEntity是online emoj在本地缓存中又property 的json内容，却没有图片
    public ArrayList<CharEntity> mEmojIds; //该集合中的CharEntity是online emoj中的内容，只有id,没有对应的property和本地图片
    public ArrayList<String> mHasDownBitMap;

    public EMReceiveTxtEntity(String originReceiveTxt) {
        this.originReceiveTxt = originReceiveTxt;
        mSpanStrBuild = new SpannableStringBuilder(originReceiveTxt);
        mFinalSpanSB = new SpannableStringBuilder();
        mNoBitMapEmojTagEntities = new ArrayList<EMCandiateProperty>();
        mEmojIds = new ArrayList<CharEntity>();
        mHasDownBitMap = new ArrayList<String>();
    }
}
