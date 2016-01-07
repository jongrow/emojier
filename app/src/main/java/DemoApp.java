import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by xinmei on 16/1/5.
 */
public class DemoApp extends Application {

    public static Context applicationContext;
    private static DemoApp instance;
    private static boolean mIsLisve;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;

        //init demo helper
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static DemoApp getInstance() {
        return instance;
    }

    public static boolean isIsLive() {
        return mIsLisve;
    }

    public static void setIsLive(boolean isLive) {
        mIsLisve = isLive;
    }
}
