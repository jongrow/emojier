package com.xinmei365.emojsdk.notify;

/**
 * Created by xinmei on 15/11/19.
 */
public interface INotifyCallback {

    /**
     * 通知回调接口
     *
     * @param entity
     *            通知组件callback给上层的对象
     */
    void notifyCallback(NotifyEntity entity);
}
