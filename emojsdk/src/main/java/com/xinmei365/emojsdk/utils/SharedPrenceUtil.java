/**
 * @project: 58bangbang
 * @file: SharedPrenceUtil.java
 * @date: 2014年9月17日 上午10:26:00
 * @copyright: 2014  58.com Inc.  All rights reserved. 
 */
package com.xinmei365.emojsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * @date: 2014年9月17日 上午10:26:00
 */
public final class SharedPrenceUtil {


    private static final String sharedPreferencesInfo = "xinmeichat.shareInfo";

    public static final String LastPubEmojTagTimestamp = "LastPubEmojTagTimestamp";
    public static final String LastPriEmojTagTimestamp = "LastPriEmojTagTimestamp";



    private static Context myContext;

    private static SharedPreferences mPreferences;

    private static Editor mEditor;

    private static SharedPrenceUtil mSharedInstance = new SharedPrenceUtil();

    private SharedPrenceUtil() {

    }

    /**
     * 单例模式获得对象实例
     */
    public static SharedPrenceUtil getInstance(Context context) {
        myContext = context;
        if (mPreferences == null && myContext != null) {
            mPreferences = myContext.getSharedPreferences(
                    sharedPreferencesInfo, Context.MODE_PRIVATE);
            mEditor = mPreferences.edit();
        }
        return mSharedInstance;
    }

    /**
     * 是否有键
     */
    public boolean isContainKey(String key) {
        return mPreferences.contains(key);
    }

    /**
     * 删除指定键的值item
     */
    public boolean clearItem(String key) {
        mEditor.remove(key);
        return mEditor.commit();
    }

    /**
     * 获得所有保存对象
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, ?> getAll() {
        if (mPreferences.getAll() instanceof HashMap) {
            return (HashMap<String, ?>) mPreferences.getAll();
        }
        return null;
    }

    /**
     * 给指定键设置String值
     */
    public boolean setString(String key, String value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    /**
     * 获得指定键的String类型值
     *
     * @param key 键
     */
    public String getString(String key) {
        return mPreferences.getString(key, "");
    }

    /**
     * 获得指定键的String类型值，带有默认值的
     *
     * @param key      键
     * @param defValue 默认值
     */
    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    /**
     * 给指定键设置int值
     */
    public boolean setInt(String key, int value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    /**
     * 获得int类型数据
     */
    public int getInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    /**
     * 获得int类型数据，带有默认值的
     */
    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    /**
     * 设置float类型数据
     */
    public boolean setFloat(String key, float value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putFloat(key, value);
        return mEditor.commit();
    }

    /**
     * 获得float类型数据
     */
    public float getFloat(String key) {
        return mPreferences.getFloat(key, 0);
    }

    /**
     * 获得float类型数据，带有默认值的
     */
    public float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    /**
     * 设置long类型数据，带有默认值的
     */
    public boolean setBoolean(String key, boolean value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    /**
     * 获得boolean类型数据
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    /**
     * 设置long类型数据
     */
    public boolean setLong(String key, long value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

    /**
     * 获得long类型数据
     */
    public long getLong(String key) {
        return mPreferences.getLong(key, 0);
    }

    /**
     * 获得long类型数据，带有默认值的
     */
    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }



}