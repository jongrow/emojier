package com.xinmei365.emojsdk.domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.xinmei365.emojsdk.utils.MD5Util;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by xinmei on 15/11/20.
 */
public class EMCandiateProperty extends BaseEntity {


    public ArrayList<EMImgProperty> mEmImgProperties;


    public String mUniqueId;
    public int mDataCount;
    public int mAscent;
    public int mDescent;
    public String mLinkUrl;

    public int mSpanStarIndex;
    public int mSpanEndIndex;


    public int mHotEmojSpanStartIndex = -1;

    public EMCandiateProperty(String uniqueId) {
        mUniqueId = uniqueId;
        if (mEmImgProperties == null) {
            mEmImgProperties = new ArrayList<EMImgProperty>();
        }
    }


    public static class EMImgProperty {
        public String mEmojUrl;
        public long mEmojXOffset;
        public long mEmojYOffset;
        public long mEmojAdvWidth;

        public long mEmojWidth;
        public long mEmojHeight;
        public int mEmojImgType;

        public Bitmap mEmojBmap;
        public String mEmojPath;


    }

    //
//    {
//        "1": {
//        "1": {
//            "x_offset": 0,
//                    "y_offset": 0,
//                    "adv_width": 64,
//                    "img_url": "http://xxx/xxx/chicago_bulls_top.png",
//                    "img_type": 1,
//                    "link": "http://xxx/xxx/xxx.com",
//                    "width":64,
//                    "height":53
//        },
//        "2": {
//            "x_offset": -64,
//                    "y_offset": 53,
//                    "adv_width": 0,
//                    "img_url": "http://xxx/xxx/000.png",
//                    "img_type": 1,
//                    "width":64,
//                    "height":27
//        },
//        "id": "#:id_xxx",
//                "data_count": "2",
//                "ascent":-53,
//                "descent":27
//    },
//        "cand_count": 1
//    }
    public EMCandiateProperty parseReqJson(JSONObject emojJson) {

        this.mAscent = emojJson.optInt("ascent");
        this.mDescent = emojJson.optInt("descent");
        this.mDataCount = emojJson.optInt("data_count");
        this.mLinkUrl = emojJson.optString("link");

        for (int i = 1; i <= this.mDataCount; i++) {
            JSONObject obj = emojJson.optJSONObject(String.valueOf(i));
            EMImgProperty emImgProperty = new EMImgProperty();
            emImgProperty.mEmojUrl = obj.optString("img_id");
            emImgProperty.mEmojXOffset = obj.optLong("x_offset");
            emImgProperty.mEmojYOffset = obj.optLong("y_offset");
            emImgProperty.mEmojAdvWidth = obj.optLong("adv_width");
            emImgProperty.mEmojImgType = obj.optInt("type");
            emImgProperty.mEmojWidth = obj.optLong("width");
            emImgProperty.mEmojHeight = obj.optLong("height");
            String filName = MD5Util.getMD5String(emImgProperty.mEmojUrl);
            File file = new File(Environment.getExternalStorageDirectory() + Constant.IMAGE_CACHE_DIR + "/" + filName + ".png");
            if (file.exists()) {
                emImgProperty.mEmojPath = file.getPath();
                emImgProperty.mEmojBmap = BitmapFactory.decodeFile(file.getPath());
            }
            mEmImgProperties.add(emImgProperty);
        }
        return this;
    }

}
