/**
 * @project: 58bangbang
 * @file: JobTalentsOptimizationAdapter.java
 * @date: 2014年10月10日 下午4:09:11
 * @copyright: 2014  58.com Inc.  All rights reserved.
 */
package com.xinmei.app.emojidemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * ListView适配器
 *
 * @author zhao yanhui
 * @date: 2014年10月10日 下午4:09:11
 */
public abstract class CommBaseAdapter<T> extends BaseAdapter {

    //上下文对象
    protected Context mContext;

    //数据源
    protected ArrayList<T> mDataArray = new ArrayList<T>();


    protected LayoutInflater mLayInflater;


    public CommBaseAdapter(Context mCtx,ArrayList<T> dataArray) {
        mContext = mCtx;
        mDataArray = dataArray;
        mLayInflater = LayoutInflater.from(mContext);
    }

    /**
     * @return
     */
    @Override
    public int getCount() {
        return mDataArray.size();
    }

    /**
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        return mDataArray.get(position);
    }

    /**
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 更新数据
     *
     * @param isClearOrigin 是否清空原有数据
     */
    public void updateData(ArrayList<T> dataArr, boolean isClearOrigin) {
        if (isClearOrigin) {
            mDataArray.clear();
        }
        mDataArray.addAll(dataArr);
        this.notifyDataSetChanged();
    }

    public void addData(T str) {
        mDataArray.add(str);
        this.notifyDataSetChanged();
    }

    /**
     * 设置ListView对应的item的view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    public abstract View getItemView(int position, View convertView, ViewGroup parent);



    public ArrayList<T> getDataArray() {
        return mDataArray;
    }

    public void setDataArray(ArrayList<T> mDataArray) {
        this.mDataArray.addAll(mDataArray);
    }
}
