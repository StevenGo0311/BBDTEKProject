package com.example.stevengo.myapplication.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by StevenGo on 2017/10/19.
 * 权限检测器
 */

public class PermissionsChecker {
    /**接收系统上下文*/
    private Context mContext;
    /**构造方法初始化context*/
    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }
    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }
    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
