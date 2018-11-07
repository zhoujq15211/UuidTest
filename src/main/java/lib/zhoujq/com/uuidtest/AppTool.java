package lib.zhoujq.com.uuidtest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.UUID;


public class AppTool {
    public static String getDeviceUUID(Context context) {
        String uuid = loadDeviceUUID(context);
        if (TextUtils.isEmpty(uuid)) {
            uuid = createDeviceUUID(context);
            saveDeviceUUID(context, uuid);
        }
        return uuid;
    }

    /**
     * 生成uuid
     *
     * @param context
     * @return
     */
    public static String createDeviceUUID(Context context) {
        String androidId = getAndroidId(context);
//        androidId = "9774d56d682e549c";
        //有些手机系统存在bug，androidId被写死为9774d56d682e549c
//        if ("9774d56d682e549c".equals(androidId)) {
//            Random random = new Random();
//            androidId = Integer.toHexString(random.nextInt()) + Integer.toHexString(random.nextInt()) + Integer.toHexString(random.nextInt());
//        }
        return new UUID(androidId.hashCode(), getPhoneInfo().hashCode()).toString();
    }

    public static String getPhoneInfo() {
        //这里选用了几个不会随系统更新而改变的值
        StringBuffer buildSB = new StringBuffer();
        buildSB.append(Build.BRAND).append("/");
        buildSB.append(Build.PRODUCT).append("/");
        buildSB.append(Build.DEVICE).append("/");
        buildSB.append(Build.ID).append("/");
        buildSB.append(Build.VERSION.INCREMENTAL);
        return buildSB.toString();
    }

    private static void saveDeviceUUID(Context context, String uuid) {
        context.getSharedPreferences("device_uuid", Context.MODE_PRIVATE).edit().putString("uuid", uuid).apply();
    }

    private static String loadDeviceUUID(Context context) {
        return context.getSharedPreferences("device_uuid", Context.MODE_PRIVATE).getString("uuid", null);
    }

    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        String applicationName = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (Exception e) {
            applicationName = "android";
        }
        return applicationName;
    }

    /**
     * 获取手机厂商
     *
     * @return
     */
    public static String getPhoneCompany() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    public static String getPhoneName() {
        return Build.MANUFACTURER;
    }

    public static String getPhoneSystemVersion() {
        return "Android" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT;
    }
}