package tw.voco.xtools.hooks;

import android.content.pm.ApplicationInfo;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class AppDebuggable extends XC_MethodHook {

    public static final String TAG = "XTools_AppDebuggable : ";

    public static void initHooks(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (loadPackageParam.packageName.equals("android")) {
            XposedHelpers.findAndHookMethod("com.android.server.pm.PackageManagerService", loadPackageParam.classLoader,
                    "getApplicationInfo", String.class, int.class, int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            String packageName = (String) param.args[0];

                            // skip google services debuggable && system app
                            if (packageName.contains("google")) {
                                return;
                            }

                            ApplicationInfo hookApplicationInfo = (ApplicationInfo) param.getResult();
                            if (hookApplicationInfo != null && (hookApplicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                                // Make the app debuggable
                                hookApplicationInfo.flags |= ApplicationInfo.FLAG_DEBUGGABLE;

                                XposedBridge.log(String.format("%s|%s Debugging Enabled.", TAG, packageName));
                            }
                        }
                    }
            );
        }

    }
}
