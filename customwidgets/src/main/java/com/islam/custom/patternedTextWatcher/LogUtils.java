package com.islam.custom.patternedTextWatcher;

import android.util.Log;

final class LogUtils {
  static final String TAG = "PatternedTextWatcher";

  private LogUtils() {
  }

  public static void logd(Object methodName, Object value, boolean debug) {
    if (debug) {
      Log.d(TAG, String.format("%1$s = \"%2$s\"", methodName, value));
    }
  }

  public static void logw(Object methodName, Object value, boolean debug) {
    if (debug) {
      Log.w(TAG, String.format("%1$s:\n%2$s", methodName, value));
    }
  }
}
