package com.xinmei365.emojsdk.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.xinmei365.emojsdk.domain.Constant;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xinmei on 15/11/24.
 */
public class CommUtil {

    public static void clearAllEmojImgs() {
        File file = new File(Environment.getExternalStorageDirectory() + Constant.IMAGE_CACHE_DIR);
        if (file.exists()) {
            deleteDir(file);
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    public static Context getContext() {
        try {
            Class<?> activityThreadClass =
                    Class.forName("android.app.ActivityThread");
            Method method = activityThreadClass.getMethod("currentApplication");
            return (Application) method.invoke(null, (Object[]) null);
        } catch (Exception e) {
        }

        return null;
    }

    public static String getReqImgSize() {
        String defautlSize = "064";

        int imgSize = SharedPrenceUtil.getInstance(CommUtil.getContext()).getInt(Constant.KEY_REQ_IMG_SIZE);
        if (0 < imgSize && imgSize < 100) {
            defautlSize = "0" + imgSize;
        } else if (imgSize >= 100) {
            defautlSize = String.valueOf(imgSize);
        }

        return defautlSize;
    }

    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

    public static void deleteRedundancyImgs() {
        String fileDir = Environment.getExternalStorageDirectory() + Constant.IMAGE_CACHE_DIR;
        File file = new File(fileDir);
        double dirSize = getDirSize(file);
        Logger.d("dirsize", "dirsize= " + dirSize);
        if (dirSize > getMaxBuffer()) {
            File[] files = file.listFiles();
            List<File> fileLists = Arrays.asList(files);
            Collections.sort(fileLists, new Comparator<File>() {
                @Override
                public int compare(File lhs, File rhs) {
                    if (lhs.lastModified() > rhs.lastModified()) {
                        return 1;
                    } else if (lhs.lastModified() < rhs.lastModified()) {
                        return -1;
                    }
                    return 0;
                }
            });
            for (int i = 0; i < files.length / 2; i++) {
                files[i].delete();
            }
        }
    }

    /**
     * get the max value of the emoji buffer
     *
     * @return
     */
    private static int getMaxBuffer() {
        int maxBuffer = SharedPrenceUtil.getInstance(CommUtil.getContext()).getInt(Constant.KEY_MAX_EMOJI_BUFFER_SIZE);
        maxBuffer = maxBuffer <= 0 ? 1 : maxBuffer;
        return maxBuffer;
    }
}
