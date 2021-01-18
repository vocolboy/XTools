package tw.voco.xtools.hooks;

import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WebViewDebuggable extends XC_MethodHook {
    public static final String TAG = "XTools_WebViewDebuggable : ";

    public static void initHooks(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        String[] webViewNames = new String[]{"android.webkit.WebView"};

        if (Arrays.asList(webViewNames).contains(loadPackageParam.packageName)) {
            XposedBridge.hookAllConstructors(
                    XposedHelpers.findClass(loadPackageParam.packageName, loadPackageParam.classLoader),
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);

                            XposedHelpers.callStaticMethod(param.getClass(), "setWebContentsDebuggingEnabled", "true");

                            XposedBridge.log(String.format("%s|%s Debugging Enabled.", TAG, loadPackageParam.packageName));
                        }
                    }
            );
        }
    }
}
