package com.leonardo.demobase.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import com.leonardo.demobase.widgets.exo.DBLog;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/22
 */

public class PermissionsChecker {
  private final Context context;
  public PermissionsChecker(Context context){
    this.context = context.getApplicationContext();
  }

  public boolean lacksPermissions(String... permissions) {
    for (String permission : permissions) {
      if (lacksPermission(permission)) {
        return true;
      }
    }
    return false;
  }

  public boolean lacksPermissionsResult(int... permissions) {
    for (int permission : permissions) {
      DBLog.e("lacksPermissionsResult permission",""+permission);
      if (lacksPermission(permission)) {
        DBLog.e("lacksPermissionsResult","true");
        return true;
      }
    }
    return false;
  }
  private boolean lacksPermission(int resultCode) {
    return resultCode ==
        PackageManager.PERMISSION_GRANTED;
  }

  private boolean lacksPermission(String permission) {
    return ContextCompat.checkSelfPermission(context, permission) ==
        PackageManager.PERMISSION_DENIED;
  }
}
