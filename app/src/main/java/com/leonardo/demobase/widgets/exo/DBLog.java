package com.leonardo.demobase.widgets.exo;

import android.util.Log;
import com.leonardo.demobase.BuildConfig;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/21
 */

public class DBLog {
  private static final boolean PRINT_LOG = BuildConfig.DEBUG;
  public static final String DB_TAG = "entertainment_base_";

  public static void i(String tag, String msg) {
    if (PRINT_LOG) {
      Log.i(tag, msg);
    }
  }

  public static void d(String tag, String msg) {
    if (PRINT_LOG) {
      Log.d(tag, msg);
    }
  }
  public static void d(String msg) {
    if (PRINT_LOG) {
      Log.d(DB_TAG, msg);
    }
  }

  public static void e(String tag, String msg) {
    if (PRINT_LOG) {
      Log.e(tag, msg);
    }
  }

  public static void v(String tag, String msg) {
    if (PRINT_LOG) {
      Log.v(tag, msg);
    }
  }
}
