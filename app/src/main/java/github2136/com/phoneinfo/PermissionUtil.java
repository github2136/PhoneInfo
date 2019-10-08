package github2136.com.phoneinfo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;

/**
 * Created by YB on 2019/10/8
 */
class PermissionUtil {
    private Activity activity;
    private ArrayMap<String, String> mPermissionArrayMap = new ArrayMap<>();
    private boolean setPermission = false;//有拒绝且不再提示的权限，打开了应用设置修改权限
    private boolean has = false;//有拒绝的权限
    private AlertDialog alertDialog;
    private PermissionCallback callback;

    PermissionUtil(Activity activity) {
        this.activity = activity;
        alertDialog = new AlertDialog.Builder(activity)
                .setTitle("警告")
                .setCancelable(false)
                .setPositiveButton("请求权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (has) {
                            requestPermission();
                        } else {
                            setPermission = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", PermissionUtil.this.activity.getPackageName(), null);
                            intent.setData(uri);
                            PermissionUtil.this.activity.startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("关闭应用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtil.this.activity.finish();
                    }
                })
                .create();
    }

    /**
     * 获取权限
     */
    void getPermission(ArrayMap<String, String> permissionArrayMap, PermissionCallback callback) {
        this.mPermissionArrayMap = permissionArrayMap;
        this.callback = callback;
        //请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionStatus = PackageManager.PERMISSION_GRANTED;
            //检查权限
            for (String s : permissionArrayMap.keySet()) {
                if (activity.checkSelfPermission(s) == PackageManager.PERMISSION_DENIED) {
                    //有拒绝权限
                    permissionStatus = PackageManager.PERMISSION_DENIED;
                    break;
                }
            }

            if (permissionStatus == PackageManager.PERMISSION_DENIED) {
                requestPermission();
            } else {
                callback.successful();
            }
        } else {
            callback.successful();
        }
    }

    //请求权限
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] p = new String[mPermissionArrayMap.keySet().size()];
            mPermissionArrayMap.keySet().toArray(p);
            activity.requestPermissions(p, 1);
        }
    }

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        boolean allow = true;
        has = false;
        StringBuilder permissionStr = new StringBuilder("缺少");
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                allow = false;
                permissionStr.append(" " + mPermissionArrayMap.get(permissions[i]));
                //判断是否点击不再提示
                boolean showRationale = activity.shouldShowRequestPermissionRationale(mPermissionArrayMap.get(permissions[i]));
                if (showRationale) {
                    has = true;
                }
            }
        }
        permissionStr.append(" 权限");
        if (!has) {
            permissionStr.append(" 请在应用的权限管理中允许以上权限");
        }
        if (allow) {
            this.callback.successful();
        } else {
            alertDialog.setMessage(permissionStr);
            alertDialog.show();
        }
    }

    void onRestart() {
        if (setPermission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission();
            }
        }
    }

    public interface PermissionCallback {
        void successful();
    }
}
