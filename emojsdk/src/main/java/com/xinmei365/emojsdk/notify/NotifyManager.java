package com.xinmei365.emojsdk.notify;

import android.util.Log;

import com.xinmei365.emojsdk.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinmei on 15/11/19.
 */
public class NotifyManager {

    private static NotifyManager mInstance = null;

    private final String TAG = NotifyManager.class.getSimpleName();
    private Map<String, ArrayList<INotifyCallback>> notifyMap;

    private NotifyManager(){
        notifyMap = new HashMap<String, ArrayList<INotifyCallback>>();

    }

    public static NotifyManager getInstance(){
        if (mInstance == null){
            synchronized (NotifyManager.class){
                if (mInstance == null) {
                    mInstance = new NotifyManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 根据参数创建通知类型
     *
     * @param cmd    cmd
     * @param subcmd subcmd
     * @param type   补充字段
     * @return 通知类型
     */
    public String createFlag(String cmd, String subcmd, String type, String otherMarks) {
        StringBuilder flag = new StringBuilder(cmd + "_" + subcmd);

        if (!StringUtil.isNullOrEmpty(type)) {
            flag.append("_").append(type);
        }

        if (!StringUtil.isNullOrEmpty(otherMarks)) {
            flag.append("_").append(otherMarks);
        }

        return flag.toString();
    }


    /**
     * 注册消息通知
     *
     * @param key      消息类型
     * @param callback 回调
     */
    public void registerNotifyCallback(String key, INotifyCallback callback) {
        if (StringUtil.isNullOrEmpty(key) || callback == null) {
            return;
        }

        if (!notifyMap.containsKey(key)) {
            notifyMap.put(key, new ArrayList<INotifyCallback>());
        }

        ArrayList<INotifyCallback> notifies = notifyMap.get(key);
        if (notifies.indexOf(callback) < 0) {
            notifies.add(callback);
            Log.d(TAG, "添加监听 name:" + callback.getClass().getSimpleName() + " type:" + key);
        }
    }



    /**
     * 注册通知消息监听
     *
     * @param cmd        cmd
     * @param subcmd     subcmd
     * @param type       type有些协议需要
     * @param otherMarks 额外标识
     * @param callback   回调
     */
    public void registerNotifyCallback(String cmd, String subcmd, String type, String otherMarks, INotifyCallback callback) {
        String notifyFlag = createFlag(cmd, subcmd, type, otherMarks);

        registerNotifyCallback(notifyFlag, callback);
    }

    /**
     * 注册通知消息监听
     *
     * @param cmd      cmd
     * @param subcmd   subcmd
     * @param type     type有些协议需要
     * @param callback 回调
     */
    public void registerNotifyCallback(String cmd, String subcmd, String type, INotifyCallback callback) {
        registerNotifyCallback(cmd, subcmd, type, "", callback);
    }

    /**
     * 注册通知消息监听
     *
     * @param cmd      cmd
     * @param subcmd   subcmd
     * @param callback 回调
     */
    public void registerNotifyCallback(String cmd, String subcmd, INotifyCallback callback) {
        registerNotifyCallback(cmd, subcmd, "", callback);
    }

    /**
     * 删除消息监听
     *
     * @param key      消息类型
     * @param callback 回调
     */
    public void removeNotifyCallback(String key, INotifyCallback callback) {
        if (StringUtil.isNullOrEmpty(key) || callback == null) {
            return;
        }

        if (notifyMap.containsKey(key)) {
            ArrayList<INotifyCallback> callbacks = notifyMap.get(key);

            int index = callbacks.indexOf(callback);

            if (index >= 0) {
                callbacks.remove(callbacks.indexOf(callback));
                Log.d(TAG, "删除监听 name:" + callback.getClass().getSimpleName() + " type:" + key);
            }

            if (callbacks.size() == 0) {
                notifyMap.remove(key);
            }
        }
    }

    /**
     * 删除消息监听
     *
     * @param cmd        cmd
     * @param subcmd     subcmd
     * @param type       额外标识
     * @param callback   回调
     * @param otherMarks 其他标识
     */
    public void removeNotifyCallback(String cmd, String subcmd, String type, String otherMarks, INotifyCallback callback) {
        String notifyFlag = createFlag(cmd, subcmd, type, otherMarks);

        removeNotifyCallback(notifyFlag, callback);
    }

    /**
     * 删除消息监听
     *
     * @param cmd      cmd
     * @param subcmd   subcmd
     * @param type     额外标识
     * @param callback 回调
     */
    public void removeNotifyCallback(String cmd, String subcmd, String type, INotifyCallback callback) {
        removeNotifyCallback(cmd, subcmd, type, "", callback);
    }

    /**
     * 注册通知消息监听
     *
     * @param cmd      cmd
     * @param subcmd   subcmd
     * @param callback type有些协议需要
     */
    public void removeNotifyCallback(String cmd, String subcmd, INotifyCallback callback) {
        removeNotifyCallback(cmd, subcmd, "", callback);
    }

    /**
     * 删除所有监听
     *
     */
    public void removeAllNotifyCallback() {
        Log.d(TAG, "清除所有监听");
        notifyMap.clear();
    }

    /**
     * 发送通知
     *
     * @param key    消息类型
     * @param entity 内容实体
     */
    public void sendNotifyCallback(String key, NotifyEntity entity) {
        if (StringUtil.isNullOrEmpty(key) || entity == null) {
            return;
        }

        entity.setKey(key);
        if (notifyMap.containsKey(key)) {
            List<INotifyCallback> callbacks = notifyMap.get(key);
            List<INotifyCallback> wellCalls = new ArrayList<INotifyCallback>();

            int size = callbacks.size();
            for (int i = 0; i < size; i++) {
                if (i >= callbacks.size()) {
                    break;
                }

                INotifyCallback notify = callbacks.get(i);
                if (notify != null) {
                    wellCalls.add(notify);
                }
            }

            for (INotifyCallback call : wellCalls) {
                call.notifyCallback(entity);
                Log.d(TAG, "分发消息 key:" + key + " " + call.getClass().getSimpleName() + "被调用");
            }
        }
    }

    /**
     * 发送通知
     *
     * @param entity 内容实体
     */
    public void sendNotifyCallback(NotifyEntity entity) {
        if (entity == null) {
            return;
        }

        String notifyFlag = createFlag(entity.getCmd(), entity.getSubcmd(), entity.getType(), entity.getOtherMarks());

        sendNotifyCallback(notifyFlag, entity);
    }



}
