package com.xinmei365.emojsdk.domain;

import android.text.SpannableStringBuilder;

import com.xinmei365.emojsdk.orm.EMDBMagager;
import com.xinmei365.emojsdk.utils.StringUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by xinmei on 15/11/26.
 */
public class EMCandiateEntity {

   /* {
        "1": {
        "1": {
            "x_offset": 0,
                    "y_offset": 0,
                    "adv_width": 64,            },
        "img_url": "http://cdn1.emojier.com/cdndata/XXStarbucksXX_32.png",
                "img_type": 1,
                "link": "http://xxx/xxx/xxx.com"
        "width":64,
                "height":46
    },
        "2": {
        "img_url": "http://cdn1.emojier.com/cdndata/Steam_32.png",
                "img_type": 1,
                "width":64,
                "height":16
    },
        "id": "116d3f3bb37246ab9f0ff6d5a6b0b929",
            "data_count": "2"
        "ascent":-12
        "descent":34
    },
            "2": {
        "1": {
            "img_url": "http://cdn1.emojier.com/cdndata/Starbucks_32.png",
                    "img_type": 1,
                    "link": "http://www.starbucks.com/"
            "width":64,
                    "height":60
        }
        "id": "eb97f156274f410eb8088669cf99b80b",
                "data_count": "1"
        "ascent":-46
        "descent":18
    },
            "status_code":200,
            "cand_count": 2
}*/

    public String mEMKey;
    public  int mEMStart;


    public int mStatusCode;
    public int mCandCount;

    public ArrayList<EMCandiateProperty> mEmojEntities;

    public boolean isClickCan = false; //仅表示是否点击过toolbar 选择候选emoj来替换高亮的词
    public Vector<SpannableStringBuilder> mEMSpans;

    public EMCandiateEntity(String userinputEmojStr, int emojStartIndex) {
        mEMKey = userinputEmojStr;
        mEMStart = emojStartIndex;
        mEmojEntities = new ArrayList<EMCandiateProperty>();
    }

    public EMCandiateEntity parseRespJson(JSONObject jsonObj) {
        this.mCandCount = jsonObj.optInt("cand_count");
        this.mStatusCode = jsonObj.optInt("status_code");
        for (int i = 1;i <= mCandCount;i++) {

            JSONObject obj = jsonObj.optJSONObject(String.valueOf(i));
            EMCandiateProperty emojEntity = new EMCandiateProperty(obj.optString("id"));
            emojEntity.parseReqJson(obj);
            mEmojEntities.add(emojEntity);

            //TODO:更新EmojIdProperty中的id和 Property内容
            EMDBMagager.getInstance().cacheEmojIdProperty(emojEntity.mUniqueId,obj.toString());
        }
        return this;
    }


    public boolean haveCanEmojBitmap() {
        boolean haveEmoj = false;
        for (EMCandiateProperty tagEntity : mEmojEntities) {
            for (EMCandiateProperty.EMImgProperty descrip : tagEntity.mEmImgProperties) {
                if (!StringUtil.isNullOrEmpty(descrip.mEmojPath)){
                    haveEmoj = true;
                    return haveEmoj;
                }
            }
        }
        return haveEmoj;
    }
}
