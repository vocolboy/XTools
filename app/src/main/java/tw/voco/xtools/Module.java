package tw.voco.xtools;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import tw.voco.xtools.hooks.AppDebuggable;
import tw.voco.xtools.hooks.WebViewDebuggable;

public class Module extends XC_MethodHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        WebViewDebuggable.initHooks(loadPackageParam);

        AppDebuggable.initHooks(loadPackageParam);
    }
}
