package ea4gkq.at.ioniqInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ea4gkq.at.ioniqInfo.greencar.NumberHolder;

public class NaviGpsChangedReceiver extends BroadcastReceiver {
  public static final String EXTRA_LAT      = "com.hkmc.telematics.gis.extra.LAT";
  public static final String EXTRA_LON      = "com.hkmc.telematics.gis.extra.LON";
  public static final String EXTRA_ALT      = "com.hkmc.telematics.gis.extra.ALT";
  public static final String EXTRA_HDOP     = "com.hkmc.telematics.gis.extra.HDOP";
  public static final String EXTRA_PDOP     = "com.hkmc.telematics.gis.extra.PDOP";
  public static final String EXTRA_HEADING  = "com.hkmc.telematics.gis.extra.HEADING";
  public static final String EXTRA_TYPE     = "com.hkmc.telematics.gis.extra.TYPE";
  public static final String EXTRA_TIME     = "com.hkmc.telematics.gis.extra.TIME";

  private static final String TAG = "at.ea4gkq.ivireceiver.NaviGpsChangedReceiver";
  private Logger mLogger = null;
  private static NumberHolder mLatLonHolder = new NumberHolder();
  private MainActivity mMainActivyty;


  public NaviGpsChangedReceiver() {
    mLogger = new Logger(TAG);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    double lat = intent.getDoubleExtra(EXTRA_LAT, 0);
    double lon = intent.getDoubleExtra(EXTRA_LON, 0);
/*
    if(mLatLonHolder.equals(lat, lon)) {
      return;
    }
    mLatLonHolder.setValues(lat, lon);
*/
    double alt = intent.getDoubleExtra(EXTRA_ALT, 0);
    int hdop = intent.getIntExtra(EXTRA_HDOP, 0);
    int pdop = intent.getIntExtra(EXTRA_PDOP, 0);
    int heading = intent.getIntExtra(EXTRA_HEADING, 0);
    int type = intent.getIntExtra(EXTRA_TYPE, 0);
    String time = intent.getStringExtra(EXTRA_TIME);

    String msg = new StringBuilder("received com.hkmc.telematics.gis.action.NAVI_GPS_CHANGED\n")
        .append("lat: ").append(lat)
        .append(" lon: ").append(lon)
        .append(" alt: ").append(alt)
        .append(" hdop: ").append(hdop)
        .append(" pdop: ").append(pdop)
        .append(" heading: ").append(heading)
        .append(" type: ").append(type)
        .append(" time: ").append(time).toString();
   // mLogger.log(msg);

    //MainActivity.updateGps(lat, lon, alt);
    try {
      if(MainActivity.ins!=null) {
        MainActivity.updateGps(lat, lon, alt, heading);
      }
    } catch (Exception e) {
      String errorText = e.toString();
      Log.e("NAVI_GPS",errorText);
    }
    }


}
