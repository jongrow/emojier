package com.xinmei365.emojsdk.contoller;

import com.xinmei365.emojsdk.domain.EMCharacterEntity;
import com.xinmei365.emojsdk.notify.NotifyEntity;
import com.xinmei365.emojsdk.notify.NotifyKeys;
import com.xinmei365.emojsdk.notify.NotifyManager;
import com.xinmei365.emojsdk.utils.Logger;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by xinmei on 15/12/17.
 */
public class MessageQueueManager {


    private static final String TAG = MessageQueueManager.class.getSimpleName();
    // 集合实现的队列，需要翻译的emojkey
    private Vector<EMCharacterEntity> mNeedTransEmojKeys = new Vector<EMCharacterEntity>();
    //已经将对应的emoj下载到本地的 emojkey
    private Vector<EMCharacterEntity> mHasTransEmojKeys = new Vector<EMCharacterEntity>();

    private int mAllNeedTranCount = -1;

    private static MessageQueueManager mInstance;
    private ArrayList<EMCharacterEntity> mAllAssembleArr;

    private MessageQueueManager() {

    }

    public static MessageQueueManager getInstance() {
        if (mInstance == null) {
            synchronized (MessageQueueManager.class) {
                if (mInstance == null) {
                    mInstance = new MessageQueueManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 消息加入队列头的位置<br>
     * 只有主线程使用
     */
    public void putNeedTransEmojKey(EMCharacterEntity transEntry) {
        if (transEntry != null) {
            //加入队尾
            mNeedTransEmojKeys.add(0, transEntry);

            //启动发送线程处理
            EMContolManager.getInstance().startSendMsg();
        }
    }


    /**
     * 只有主线程使用
     */
    public void putAllNeedTransEmojKey(ArrayList<EMCharacterEntity> transEntrys) {
        if (transEntrys != null && transEntrys.size() > 0) {
            mNeedTransEmojKeys.addAll(transEntrys);
            mAllNeedTranCount = transEntrys.size();
            EMContolManager.getInstance().startSendMsg();
        }
    }

    public EMCharacterEntity getNextSendMessage() {
        if (mNeedTransEmojKeys.size() <= 0) {
            return null;
        }

        return mNeedTransEmojKeys.remove(0);
    }

    /**
     * 只有主线程使用
     */
    public void putTransedEmojKey(EMCharacterEntity transEntry) {
        if (transEntry == null || mAllNeedTranCount < 0) return;

        Logger.d(TAG, "one emoj has download start=" + transEntry.mWordStart + " word=" + transEntry.mWord);

        for (EMCharacterEntity entry : mHasTransEmojKeys) {
            if (entry.mWordStart == transEntry.mWordStart && entry.mWord.equals(transEntry.mWord)) {
                return;
            }
        }
        mHasTransEmojKeys.add(transEntry);
        if (mNeedTransEmojKeys.size() == 0 && mHasTransEmojKeys.size() == mAllNeedTranCount) {
            mHasTransEmojKeys.clear();
            mAllNeedTranCount = -1;
            NotifyManager.getInstance().sendNotifyCallback(NotifyKeys.ALL_EMOJ_HAS_DOWNLOAD, new NotifyEntity(mAllAssembleArr));
        }
    }

    public Vector<EMCharacterEntity> getNeedTransEmojKeys() {
        return mNeedTransEmojKeys;
    }

    public void clear() {
        mHasTransEmojKeys.clear();
        mNeedTransEmojKeys.clear();
    }

    public void putNeedAssebleKeys(ArrayList<EMCharacterEntity> joinArr) {
        this.mAllAssembleArr = joinArr;
    }
}
