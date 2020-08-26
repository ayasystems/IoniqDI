package ea4gkq.at.ioniqInfo;

import android.support.v4.util.Pair;
import android.util.Log;

public class Logger {
  String mTag;
  private MainActivity mMainActivyty;
  public Logger(String tag) {
    mTag = tag;
  }

  public void log(String msg) {
    Log.d(mTag, msg);


  }

  public void log(String msg, Throwable ex) {
    Log.e(mTag, msg, ex);
  }

  public void log(String event, float oldValue, float newValue) {
    String msg = event + " old: " + oldValue + " new: " + newValue;
    Log.v(mTag, msg);

  }

  public void log(String event, int oldValue, int newValue) {
    String msg = event + " old: " + oldValue + " new: " + newValue;
    Log.v(mTag, msg);

  }

  public void log(Pair<String, Object>... values) {
    StringBuilder sb = new StringBuilder();
    for(Pair<String, Object> value : values) {
      if(sb.length() == 0) {
        sb.append("{");
      } else {
        sb.append(",");
      }
      sb.append("\"").append(value.first).append("\":").append(value.second);
    }
    sb.append("}");
    Log.v(mTag, sb.toString());



  }
}
